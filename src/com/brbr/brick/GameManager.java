package com.brbr.brick;

import com.brbr.brick.UI.UIManager;
import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.level.LevelManager;
import com.brbr.brick.object.*;
import com.brbr.brick.physics.*;
import com.brbr.brick.render.Renderer;

import javax.swing.*;

public class GameManager {
    private final Thread gameThread = createGameThread();

    private Scene scene;
    private Renderer renderer;
    private PhysicManager physicManager;
    private UIManager uiManager;
    private LevelManager levelManager;
    private InputManager inputManager;
    private AnimationManager animationManager;

    private BallShooter shooter;

    public GameManager() {
        init();
    }

    private void init() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        scene = new Scene();
        uiManager = UIManager.getInstance(scene);
        renderer = new Renderer(scene);
        physicManager = new PhysicManager(scene);
        levelManager = new LevelManager(scene);
        animationManager = new AnimationManager(scene);
        inputManager = InputManager.getInstance();
        uiManager = UIManager.getInstance();
        shooter = new BallShooter(scene);
        inputManager.setTarget(renderer);

        // init scene frame
        scene.frameMarginTop = Coordinates.GAME_FRAME_Y;
        scene.frameWidth = GAME_WIDTH;
        scene.frameHeight = Coordinates.BRICK_HEIGHT * Coordinates.BRICK_GRID_HEIGHT +
                (Coordinates.BRICK_MARGIN + 1) * Coordinates.BRICK_GRID_HEIGHT;

        uiManager.init();

        createDummyData();

        frame.getContentPane().add(renderer);
        frame.setSize(GAME_WIDTH, GAME_HEIGHT);
        frame.setVisible(true);
    }

    // dummy data TODO : remove
    private void createDummyData() {
        Wall wall1 = new Wall(scene.frameWidth / 2, scene.frameMarginTop);
        ((BoxCollider) wall1.addComponent(new BoxCollider(scene.frameWidth, 10, ColliderType.STATIC))).setTag("wall");

        Wall wall2 = new Wall(0, scene.frameHeight / 2 + scene.frameMarginTop);
        ((BoxCollider) wall2.addComponent(new BoxCollider(10, scene.frameHeight, ColliderType.STATIC))).setTag("wall");

        Wall wall3 = new Wall(scene.frameWidth / 2, scene.frameHeight + scene.frameMarginTop);
        ((BoxCollider) wall3.addComponent(new BoxCollider(scene.frameWidth, 10, ColliderType.STATIC))).setTag("wall");

        Wall wall4 = new Wall(scene.frameWidth, scene.frameHeight / 2 + scene.frameMarginTop);
        ((BoxCollider) wall4.addComponent(new BoxCollider(10, scene.frameHeight, ColliderType.STATIC))).setTag("wall");

        scene.gameObjectList.add(wall1);
        scene.gameObjectList.add(wall2);
        scene.gameObjectList.add(wall3);
        scene.gameObjectList.add(wall4);

        RayPath rayPath = new RayPath(scene.frameWidth / 2, scene.frameHeight - 50, 500);
        scene.rayPath = rayPath;
        // TODO : 공이 전부 소진 된 후 다음 레벨로 바뀌도록 변경 필요
        for (int i = 0; i < 100; i++) {
            scene.scheduler.postDelayed(i * 5000, () -> {
                scene.needLevelUpdate = true;
            });
        }
    }

    public void start() {
        gameThread.start();
    }

    private Thread createGameThread() {
        return new Thread(() -> {
            long lastLoopTime = System.currentTimeMillis();
            long lastFpsTime = System.currentTimeMillis();
            int frameCount = 0;

            while (true) {
                loop(System.currentTimeMillis() - lastLoopTime);

                lastLoopTime = System.currentTimeMillis();
                frameCount++;
                if (System.currentTimeMillis() - lastFpsTime >= 1000) {
                    lastFpsTime = System.currentTimeMillis();
                    scene.framePerSecond = frameCount;
                    frameCount = 0;
                }

                try {
                    //noinspection BusyWait
                    Thread.sleep(1000L / 60L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loop(long dt) {
        // scheduler
        scene.scheduler.update();

        // physic
        physicManager.collisionCheck(dt);

        // input
        handleInput(inputManager.poll());

        // level
        levelManager.update(dt);

        // animation
        animationManager.update(dt);

        // render
        renderer.repaint();

        // score
        if (scene.gameStatus == Scene.BEFORE_GAME || scene.gameStatus == Scene.END_GAME) {
            scene.scoreManager.saveRecordScore();
        }
    }

    private void handleInput(InputData inputData) {
        if (inputData == null) return;
        uiManager.buttonClickCheck(inputData);
        physicManager.handleInput(inputData);
        shooter.handleInput(inputData);
    }

    public final static int GAME_WIDTH = 605;
    public final static int GAME_HEIGHT = 800;
}

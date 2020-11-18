package com.brbr.brick;

import com.brbr.brick.UI.ButtonClickCallback;
import com.brbr.brick.UI.ButtonUI;
import com.brbr.brick.UI.TextUI;
import com.brbr.brick.UI.UIManager;
import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.debug.Debugger;
import com.brbr.brick.object.Brick;
import com.brbr.brick.object.GameObject;
import com.brbr.brick.object.Wall;
import com.brbr.brick.physics.*;
import com.brbr.brick.render.Renderer;
import com.brbr.brick.math.Transform;
import com.brbr.brick.math.Vector2;

import javax.swing.*;
import java.util.Random;

public class GameManager {

    private final Thread gameThread = createGameThread();

    private Scene scene;
    private Renderer renderer;
    private PhysicManager physicManager;
    private UIManager uiManager;
    private InputManager inputManager;

    public GameManager() {
        init();
    }

    private void init() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // TODO : init managers
        scene = new Scene();
        renderer = new Renderer(scene);
        physicManager = PhysicManager.getInstance();
        uiManager = UIManager.getInstance();


        // init scene frame
        scene.frameMarginTop = Coordinates.GAME_FRAME_Y;
        scene.frameWidth = GAME_WIDTH;
        scene.frameHeight = Coordinates.BRICK_HEIGHT * Coordinates.BRICK_GRID_HEIGHT +
                (Coordinates.BRICK_MARGIN + 1) * Coordinates.BRICK_GRID_HEIGHT;

        createDummyData();
        TextUI recordUI = new TextUI("Record: 0", new Vector2(scene.frameWidth / 2 - 48, 30), 20);
        TextUI scoreUI = new TextUI("Score: 0", new Vector2(scene.frameWidth / 2 - 48, 60), 20);

        //Todo: Delete
        ButtonUI tmpButton = new ButtonUI("임시버튼", new Vector2(50, 15), 20, 100, 40,
                new ButtonClickCallback() {
                    @Override
                    public void clicked() {
                        Debugger.Print("button clicked");
                    }
                });
        uiManager.addTextUI(recordUI);
        uiManager.addTextUI(scoreUI);
        uiManager.addButtonUI(tmpButton);

        frame.getContentPane().add(renderer);
        frame.setSize(GAME_WIDTH, GAME_HEIGHT);
        frame.setVisible(true);
    }

    // dummy data TODO : remove
    private void createDummyData() {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            Vector2 vector2 = new Vector2();
            vector2.x = (float) (random.nextInt(6)) * Coordinates.BRICK_WIDTH;
            vector2.y = (float) (random.nextInt(8)) * Coordinates.BRICK_HEIGHT + scene.frameMarginTop;
            Brick brick = new Brick();
            Transform transform = new Transform();
            transform.translate(vector2);
            brick.transform = transform;
            brick.health = random.nextInt(50) + 1;
            ((BoxCollider) brick.addComponent(new BoxCollider(Coordinates.BRICK_WIDTH, Coordinates.BRICK_HEIGHT, ColliderType.STATIC))).setTag("brick0");
            scene.gameObjectList.add(brick);
            physicManager.addEntity((BoxCollider)(brick.getComponent("BoxCollider")));
        }

        int[] ballXList = {100, 200, 300};
        int ballY = 350;
        for (int ballX : ballXList) {
            Ball ball = new Ball(ballX, ballY);
            scene.gameObjectList.add(ball);
            physicManager.addEntity((CircleCollider) (ball.getComponent("CircleCollider")));
            ball.throwBall(-45);
        }

        Wall wall1 = new Wall(scene.frameWidth / 2, scene.frameMarginTop);
        ((BoxCollider) wall1.addComponent(new BoxCollider(scene.frameWidth, 10, ColliderType.STATIC))).setTag("wall0");

        Wall wall2 = new Wall(0, scene.frameHeight / 2 + scene.frameMarginTop);
        ((BoxCollider) wall2.addComponent(new BoxCollider(10, scene.frameHeight, ColliderType.STATIC))).setTag("wall1");

        Wall wall3 = new Wall(scene.frameWidth / 2, scene.frameHeight + scene.frameMarginTop);
        ((BoxCollider) wall3.addComponent(new BoxCollider(scene.frameWidth, 10, ColliderType.STATIC))).setTag("wall2");

        Wall wall4 = new Wall(scene.frameWidth, scene.frameHeight / 2 + scene.frameMarginTop);
        ((BoxCollider) wall4.addComponent(new BoxCollider(10, scene.frameHeight, ColliderType.STATIC))).setTag("wall3");

        scene.gameObjectList.add(wall1);
        physicManager.addEntity((BoxCollider) (wall1.getComponent("BoxCollider")));
        scene.gameObjectList.add(wall2);
        physicManager.addEntity((BoxCollider) (wall2.getComponent("BoxCollider")));
        scene.gameObjectList.add(wall3);
        physicManager.addEntity((BoxCollider) (wall3.getComponent("BoxCollider")));
        scene.gameObjectList.add(wall4);
        physicManager.addEntity((BoxCollider) (wall4.getComponent("BoxCollider")));
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
        // TODO : physic
        physicManager.collisionCheck();

        // TODO : input

        // TODO : logic

        // TODO : level

        // render
        renderer.repaint();

        // TODO : 이동 논의
        for (GameObject gameObject : scene.gameObjectList) {
            if (gameObject instanceof Ball) {
                ((Ball) gameObject).update(dt / 1000.0);
            }
        }
    }

    private final static int GAME_WIDTH = 605;
    private final static int GAME_HEIGHT = 800;
}

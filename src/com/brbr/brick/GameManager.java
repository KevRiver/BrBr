package com.brbr.brick;

import com.brbr.brick.UI.*;
import com.brbr.brick.UI.UIManager;
import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.debug.Debugger;
import com.brbr.brick.level.LevelManager;
import com.brbr.brick.object.BallItem;
import com.brbr.brick.object.Brick;
import com.brbr.brick.object.GameObject;
import com.brbr.brick.object.Wall;
import com.brbr.brick.physics.*;
import com.brbr.brick.render.Renderer;
import com.brbr.brick.math.Transform;
import com.brbr.brick.math.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameManager {
    private final Thread gameThread = createGameThread();

    private Scene scene;
    private Renderer renderer;
    private PhysicManager physicManager;
    private UIManager uiManager;
    private LevelManager levelManager;
    private InputManager inputManager;
    private AnimationManager animationManager;

    private UILayer beforeLayer;
    private UILayer pauseLayer;
    private UILayer proceedingLayer;

    public GameManager() {
        init();
    }

    private void init() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // TODO : init managers
        scene = new Scene();
        renderer = new Renderer(scene);
        physicManager = new PhysicManager(scene);
        levelManager = new LevelManager(scene);
        animationManager = new AnimationManager(scene);
        inputManager = InputManager.getInstance();
        uiManager = UIManager.getInstance();

        inputManager.setTarget(renderer);
        // init scene frame
        scene.frameMarginTop = Coordinates.GAME_FRAME_Y;
        scene.frameWidth = GAME_WIDTH;
        scene.frameHeight = Coordinates.BRICK_HEIGHT * Coordinates.BRICK_GRID_HEIGHT +
                (Coordinates.BRICK_MARGIN + 1) * Coordinates.BRICK_GRID_HEIGHT;

        createDummyData();

        setProceedingUI();
        setBeforeUI();
        setPauseUI();

        beforeGame();

        frame.getContentPane().add(renderer);
        frame.setSize(GAME_WIDTH, GAME_HEIGHT);
        frame.setVisible(true);
    }

    private void setBeforeUI(){
        beforeLayer = new UILayer();

        Color backgroundColor = new Color(251, 136, 54);
        Color textColor = Color.WHITE;

        ButtonUI startButton = new ButtonUI("Game Start", new Vector2(GAME_WIDTH / 2 - 100, GAME_HEIGHT / 2 - 55),
                20, 200, 50);
        startButton.setBackgroundColor(backgroundColor);
        startButton.setTextColor(textColor);

        ButtonUI quitButton = new ButtonUI("Quit", new Vector2(GAME_WIDTH / 2 - 100, GAME_HEIGHT / 2),
                20, 200, 50);
        quitButton.setBackgroundColor(backgroundColor);
        quitButton.setTextColor(textColor);

        startButton.setButtonClickCallback(() -> {
            startGame();
        });

        quitButton.setButtonClickCallback(() -> { });

        beforeLayer.addButtonUI(startButton);
        beforeLayer.addButtonUI(quitButton);

        beforeLayer.background = true;

        uiManager.addLayer(beforeLayer);
    }

    private void setProceedingUI(){
        proceedingLayer = new UILayer();
        TextUI recordUI = new TextUI("RECORD: 0", new Vector2(scene.frameWidth / 2 - 48, 30), 20);
        TextUI scoreUI = new TextUI("SCORE: 0", new Vector2(scene.frameWidth / 2 - 48, 60), 20);

        ButtonUI pauseButton = new ButtonUI("Ⅱ", new Vector2(scene.frameWidth - 50, 15),
                20, 40, 40);
        pauseButton.setButtonClickCallback(() -> {
            pauseGame();
        });

        proceedingLayer.addTextUI(recordUI);
        proceedingLayer.addTextUI(scoreUI);
        proceedingLayer.addButtonUI(pauseButton);

        uiManager.addLayer(proceedingLayer);
    }

    private void setPauseUI(){
        pauseLayer = new UILayer();

        Color backgroundColor = new Color(251, 136, 54);
        Color textColor = Color.WHITE;

        ButtonUI resumeButton = new ButtonUI("resume", new Vector2(GAME_WIDTH / 2 - 90, GAME_HEIGHT / 2 - 40),
                20, 180, 50);

        resumeButton.setButtonClickCallback(() -> {
            startGame();
        });
        resumeButton.setBackgroundColor(backgroundColor);
        resumeButton.setTextColor(textColor);

        pauseLayer.addButtonUI(resumeButton);

        pauseLayer.background = true;

        uiManager.addLayer(pauseLayer);
    }

    private void beforeGame(){
        scene.gameStatus = Scene.BEFORE_GAME;
        beforeLayer.setVisible(true);
        pauseLayer.setVisible(false);
        proceedingLayer.setClickUnable(true);
    }

    private void startGame(){
        scene.gameStatus = Scene.PROCEEDING_GAME;
        beforeLayer.setVisible(false);
        pauseLayer.setVisible(false);
        proceedingLayer.setClickUnable(false);
    }

    private void pauseGame(){
        scene.gameStatus = Scene.PAUSE_GAME;
        beforeLayer.setVisible(false);
        pauseLayer.setVisible(true);
        proceedingLayer.setClickUnable(true);
    }

    private void endGame(){
        scene.gameStatus = Scene.END_GAME;
    }

    // dummy data TODO : remove
    private void createDummyData() {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            BallItem item = new BallItem();
            Transform transform = new Transform(
                    (float) (random.nextInt(6)) * Coordinates.BRICK_WIDTH,
                    (float) (random.nextInt(8)) * Coordinates.BRICK_HEIGHT
            );
            item.transform = transform;
            scene.gameObjectList.add(item);

        }

        int[] ballXList = {100, 200, 300};
        int ballY = 350;
        for (int ballX : ballXList) {
            Ball ball = new Ball(ballX, ballY);
            scene.gameObjectList.add(ball);
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
        scene.gameObjectList.add(wall2);
        scene.gameObjectList.add(wall3);
        scene.gameObjectList.add(wall4);

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
        /*switch(scene.gameStatus){
            case Scene.BEFORE_GAME:
            case Scene.END_GAME:
            case Scene.PAUSE_GAME:
                renderer.repaint();
                return ;
        }*/

        // scheduler
        scene.scheduler.update();

        // TODO : physic
        physicManager.collisionCheck();

        // TODO : input
        handleInput(inputManager.poll());

        // TODO : logic

        // level
        levelManager.update(dt);

        // animation
        animationManager.update(dt);

        // render
        renderer.repaint();

        // TODO : 이동 논의
        for (GameObject gameObject : scene.gameObjectList) {
            if (gameObject instanceof Ball) {
                ((Ball) gameObject).update(dt / 1000.0);
            }
        }
    }

    private void handleInput(InputData inputData){
        if(inputData == null) return;
        uiManager.buttonClickCheck(inputData);
    }
    private final static int GAME_WIDTH = 605;
    private final static int GAME_HEIGHT = 800;
}

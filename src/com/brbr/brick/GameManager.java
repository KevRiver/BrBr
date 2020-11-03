package com.brbr.brick;

import com.brbr.brick.object.Brick;
import com.brbr.brick.object.GameObject;
import com.brbr.brick.render.Renderer;
import com.brbr.math.Transform;
import com.brbr.math.Vector2;

import javax.swing.*;
import java.util.Random;

public class GameManager {

    private final Thread gameThread = createGameThread();

    private Scene scene;
    private Renderer renderer;

    public GameManager() {
        init();
    }

    private void init() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // TODO : init managers
        scene = new Scene();
        renderer = new Renderer(scene);

        // dummy data TODO : remove
        Random random = new Random();
        for (int i = 0; i < 80; i++) {
            Vector2 vector2 = new Vector2();
            vector2.x = (float) (random.nextInt(6));
            vector2.y = (float) (random.nextInt(8));
            Brick brick = new Brick();
            Transform transform = new Transform();
            transform.Translate(vector2);
            brick.transform = transform;
            brick.health = random.nextInt(50) + 1;
            scene.gameObjectList.add(brick);
        }
        frame.getContentPane().add(renderer);
        frame.setSize(GAME_WIDTH, GAME_HEIGHT);
        frame.setVisible(true);
    }

    public void start() {
        gameThread.start();
    }

    private Thread createGameThread() {
        return new Thread(() -> {
            long lastLoopTime = System.currentTimeMillis();
            while (true) {
                loop(System.currentTimeMillis() - lastLoopTime);
                lastLoopTime = System.currentTimeMillis();
            }
        });
    }

    private void loop(long dt) {
        // TODO : physic

        // TODO : input

        // TODO : logic

        // TODO : level

        // render
        renderer.repaint();
    }

    private final static int GAME_WIDTH = 605;
    private final static int GAME_HEIGHT = 800;
}

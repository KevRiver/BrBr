package com.brbr.brick;

import com.brbr.brick.render.Renderer;

import javax.swing.*;

public class GameManager {

    private final Thread gameThread = createGameThread();

    private Renderer renderer;

    public GameManager() {
        init();
    }

    private void init() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // TODO : init managers
        renderer = new Renderer();

        frame.getContentPane().add(renderer);
        frame.setSize(GAME_WIDTH,GAME_HEIGHT);
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

    private final static int GAME_WIDTH = 540;
    private final static int GAME_HEIGHT = 800;
}

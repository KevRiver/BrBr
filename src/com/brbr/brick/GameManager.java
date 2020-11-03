package com.brbr.brick;

public class GameManager {

    private final Thread gameThread = createGameThread();

    public GameManager() {
        // TODO : init
    }

    public void start() {
        // TODO : init jframe
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

        // TODO : render

    }
}

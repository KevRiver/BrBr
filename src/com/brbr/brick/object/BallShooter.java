package com.brbr.brick.object;

import com.brbr.brick.InputData;
import com.brbr.brick.Scene;
import com.brbr.brick.math.Vector2;
import com.brbr.brick.physics.Ball;

public class BallShooter extends GameObject {
    private Vector2 shootDirection;
    private double shootInterval;
    private Scene scene;

    public BallShooter(Scene scene) {
        this.scene = scene;
        init();
    }

    private void init() {
        setPosition(340, 650);
        setShootInterval(330);
    }

    public void setPosition(int x, int y) {
        transform.position.x = x;
        transform.position.y = y;
    }

    public void setShootDirection(Vector2 shootDirection) {
        this.shootDirection = shootDirection;
    }

    public void setShootInterval(double shootInterval) {
        this.shootInterval = shootInterval;
    }

    public void shoot() {
        for (int i = 0; i < scene.level; i++) {
            Ball newBall = new Ball(((int) transform.position.x), ((int) transform.position.x));
            addBallToScene(scene, newBall);
            newBall.setDirection(shootDirection);
            scene.scheduler.postDelayed((long) shootInterval * i, () -> {
                newBall.throwBall();
            });
        }
        scene.needToShoot = false;
    }

    public void handleInput(InputData inputData) {
        if (!scene.needToShoot) return;

        if (inputData.type == InputData.InputType.Release) {
            Vector2 src = transform.position;
            Vector2 dest = new Vector2(inputData.x, inputData.y);
            Vector2 dir = Vector2.subtract(dest, src);
            Vector2.normalize(dir);
            setShootDirection(dir);
            shoot();
        }
    }

    private void addBallToScene(Scene scene, Ball ball) {
        scene.gameObjectList.add(ball);
    }
}

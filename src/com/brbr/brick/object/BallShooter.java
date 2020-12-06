package com.brbr.brick.object;

import com.brbr.brick.InputData;
import com.brbr.brick.Scene;
import com.brbr.brick.math.Vector2;
import com.brbr.brick.physics.Ball;

import java.util.ArrayList;
import java.util.List;

public class BallShooter extends GameObject {
    private Vector2 shootDirection;
    private double shootInterval;
    private List<Ball> balls;
    private Scene scene;
    private boolean isBallMoving;

    public BallShooter(Scene scene) {
        this.scene = scene;
        balls = new ArrayList<>();
        init();
    }

    private void init() {
        isBallMoving = false;
        setPosition(340, 650);
        addBall(3);
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

    public void addBall(int count) {
        for (int i = 0; i < count; i++) {
            Ball newBall = new Ball(((int) transform.position.x), ((int) transform.position.x));
            addBallToScene(scene, newBall);
            balls.add(newBall);
        }
    }

    public void shoot() {
        isBallMoving = true;
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            ball.setDirection(shootDirection);
            scene.scheduler.postDelayed((long) shootInterval * i, () -> {
                ball.throwBall();
            });
        }
        scene.needToShoot = false;
    }

    public void handleInput(InputData inputData) {
        if (inputData.type == InputData.InputType.Release) {
            if (isBallMoving) return;
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

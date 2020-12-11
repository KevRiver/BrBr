package com.brbr.brick.object;

import com.brbr.brick.InputData;
import com.brbr.brick.Scene;
import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Vector2;
import com.brbr.brick.physics.Ball;

// 공을 발사하는 게임 오브젝트
public class BallShooter extends GameObject {
    private Vector2 shootDirection;
    private double shootInterval;
    private Scene scene;

    public BallShooter(Scene scene) {
        this.scene = scene;
        init();
    }

    private void init() {
        setShootInterval(330);
    } // 0.33 초

    public void setPosition(int x, int y){
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
        for (int i = 0; i < scene.ballCount; i++) {
            Ball newBall = new Ball(((int) transform.position.x), ((int) transform.position.y));
            addBallToScene(scene, newBall);
            newBall.setDirection(shootDirection);
            scene.scheduler.postDelayed((long) shootInterval * i, () -> {
                newBall.throwBall();
            });
        }
        scene.needToShoot = false;
    } // 발사해야할 공 개수만큼 새로운 공을 생성, 등록, 방향 설정 스케줄러에 등록

    public void handleInput(InputData inputData) {
        if (!scene.needToShoot) return;
        Debugger.Print("x: " + inputData.x + " y: " + inputData.y);

        if (inputData.type == InputData.InputType.Release) {
            Vector2 src = transform.position;
            Vector2 dest = new Vector2(inputData.x, inputData.y);
            Vector2 dir = Vector2.subtract(dest, src);
            Vector2.normalize(dir);
            setShootDirection(dir);
            Debugger.Print("dir: " + dir.x + ", " + dir.y);
            shoot();
        }
    } // 입력 데이터 처리

    private void addBallToScene(Scene scene, Ball ball) {
        scene.gameObjectList.add(ball);
    } // 생성된 Ball 객체 등록
}

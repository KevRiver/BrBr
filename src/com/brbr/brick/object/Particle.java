package com.brbr.brick.object;

import com.brbr.brick.math.Vector2;

import java.awt.*;

public class Particle extends AnimationObject { // 벽돌이 부서지거나 아이템을 먹었을 때 발생하는 파티클 클래스
    public Vector2 pos;

    public float gravity;
    public Vector2 animatedVector;
    public float xSpeed;
    public float opacity = 1;
    public float time = 0;

    public boolean isMoving = true;

    public Color color;

    public Particle(Vector2 pos, Color color) {
        this.color = color;
        this.pos = new Vector2();
        this.pos.x = pos.x;
        this.pos.y = pos.y;

        animatedVector = new Vector2();

        gravity = (int) (Math.random() * 20);
        xSpeed = (int) (Math.random() * 20) - 10;
    }

    @Override
    float getSpeed() {
        return 0;
    }

    @Override
    public void update(long dt) { // 매 프레임마다 particle의 좌표를 조정해주는 메서드
        // 가속도를 주어 매 프레임마다 바닥에 떨어지는 효과를 준다
        if (!isMoving) return;
        gravity += 1.2;
        animatedVector.y += gravity * (dt / 100.0);
        animatedVector.x += xSpeed * (dt / 100.0);

        time += (dt / 1000.0);
        if (time >= 0.5) opacity -= (dt / 1000.0);

        if (opacity <= 0) {
            isMoving = false;
            opacity = 0;
        }
    }
}

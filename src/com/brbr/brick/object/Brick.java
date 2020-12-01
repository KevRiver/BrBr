package com.brbr.brick.object;

public class Brick extends AnimationObject {

    public int health;
    private boolean isMoving = false;

    public Brick() {
        animatedValue = 1f;
    }

    @Override
    float getSpeed() {
        if (isMoving) return 1 / 150f; else return 0f;
    }

    public void animateMove() {
        animatedValue = 0f;
        isMoving = true;
    }

    @Override
    public void update(long dt) {
        if (!isMoving) return;

        animatedValue = animatedValue + getSpeed() * dt;
        if (animatedValue > 1) {
            isMoving = false;
            animatedValue = 1f;
        }
    }
}

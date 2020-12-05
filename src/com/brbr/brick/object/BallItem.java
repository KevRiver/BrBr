package com.brbr.brick.object;

import com.brbr.brick.physics.Collider;

public class BallItem extends AnimationObject {
    private boolean isMoving = false;
    public float moveAnimatedValue = 1f;
    public boolean isEaten = false;

    @Override
    float getSpeed() {
        return 1 / 600f;
    }

    public void animateMove() {
        moveAnimatedValue = 0f;
        isMoving = true;
    }

    @Override
    public void update(long dt) {
        super.update(dt);
        if (!isMoving) return;

        moveAnimatedValue = moveAnimatedValue + (1 / 150f) * dt;
        if (moveAnimatedValue > 1) {
            isMoving = false;
            moveAnimatedValue = 1f;
        }
    }

    @Override
    public void onCollisionEnter(Collider collider) {
        this.isEaten = true;
    }
}

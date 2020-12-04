package com.brbr.brick.object;

public abstract class AnimationObject extends GameObject {

    abstract float getSpeed();
    public float animatedValue = 0f;
    private float _animatedValue = 0f;

    public void update(long dt) {
        _animatedValue = (_animatedValue + getSpeed() * dt) % 2;
        animatedValue = Math.abs(_animatedValue - 1f);
    }
}

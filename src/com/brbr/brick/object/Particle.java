package com.brbr.brick.object;

import com.brbr.brick.math.Vector2;

public class Particle extends AnimationObject {
    public Vector2 pos;

    public float gravity;
    public Vector2 animatedVector;
    public float xSpeed;
    public float opacity = 1;
    public float time = 0;

    public boolean isMoving = true;

    public Particle(Vector2 pos) {
        animatedVector = new Vector2();

        this.pos = new Vector2();
        this.pos.x = pos.x;
        this.pos.y = pos.y;

        gravity = (int)(Math.random() * 20);
        xSpeed = (int)(Math.random() * 10);
    }

    @Override
    float getSpeed() { return 0; }

    @Override
    public void update(long dt) {
        if(!isMoving) return ;
        gravity += 1.2;
        animatedVector.y += gravity * (dt / 100.0);
        animatedVector.x += xSpeed * (dt / 100.0);

        time += (dt / 1000.0);
        if(time >= 0.5) opacity -= (dt / 1000.0);


        if(opacity <= 0){
            isMoving = false;
            opacity = 0;
        }
    }
}

package com.brbr.math;

public class Transform {
    public Vector2 position;
    public Vector2 scale;
    public float rotation;

    public Transform(){
        position = new Vector2();
        scale = new Vector2();
        rotation = 0;
    }

    public void Translate(Vector2 speed){
        position.x += speed.x;
        position.y += speed.y;
    }

    public void Rotate(float rot){
        rotation = (rotation + rot) % 360;
    }

}

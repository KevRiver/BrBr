package com.brbr.math;

public class Transform {
    public Vector2 position;
    public Vector2 scale;
    public double rotation;

    public Transform(){
        position = new Vector2();
        scale = new Vector2();
        rotation = 0;
    }

    public Transform(double x, double y){
        position = new Vector2(x, y);
        scale = new Vector2();
        rotation = 0;
    }

    public void translate(Vector2 speed){
        position.x += speed.x;
        position.y += speed.y;
    }

    public void rotate(float rot){
        rotation = (rotation + rot) % 360;
    }

}

package com.brbr.brick.math;

public class Transform {
    public com.brbr.brick.math.Vector2 position;
    public com.brbr.brick.math.Vector2 scale;
    public double rotation;

    public Transform(){
        position = new com.brbr.brick.math.Vector2();
        scale = new com.brbr.brick.math.Vector2();
        rotation = 0;
    }

    public Transform(double x, double y){
        position = new com.brbr.brick.math.Vector2(x, y);
        scale = new com.brbr.brick.math.Vector2();
        rotation = 0;
    }

    public void translate(com.brbr.brick.math.Vector2 speed){
        position.x += speed.x;
        position.y += speed.y;
    }

    public void rotate(float rot){
        rotation = (rotation + rot) % 360;
    }

}

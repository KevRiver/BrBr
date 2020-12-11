package com.brbr.brick.math;

// 위치, 스케일, 회전 값을 저장하는 클래스
public class Transform {
    public com.brbr.brick.math.Vector2 position;
    public com.brbr.brick.math.Vector2 scale;
    public double rotation;

    public Transform() {
        position = new com.brbr.brick.math.Vector2();
        scale = new com.brbr.brick.math.Vector2();
        rotation = 0;
    }

    public Transform(double x, double y) {
        position = new com.brbr.brick.math.Vector2(x, y);
        scale = new com.brbr.brick.math.Vector2();
        rotation = 0;
    }

    public void translate(com.brbr.brick.math.Vector2 speed) {
        position.x += speed.x;
        position.y += speed.y;
    } // 파라미터로 들어온 속도 만큼 위치 변경

    public void rotate(float rot) {
        rotation = (rotation + rot) % 360;
    }

}

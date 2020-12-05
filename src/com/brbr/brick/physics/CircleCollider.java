package com.brbr.brick.physics;

import com.brbr.brick.math.Bounds;
import com.brbr.brick.math.Vector2;

public class CircleCollider extends Collider {
    public double radius;
    public Vector2 center;

    public CircleCollider() {
        super();
    }

    public CircleCollider(ColliderType type) {
        super(type);
    }

    public CircleCollider(double radius) {
        this();
        this.radius = Math.abs(radius);
        this.center = new Vector2(0, 0);
    }

    public CircleCollider(double radius, ColliderType type) {
        this(type);
        this.radius = radius;
        this.center = new Vector2(0, 0);
    }

    public void setCenter(Vector2 position) {
        center.x = position.x;
        center.y = position.y;
    }

    public Vector2 getPositionRelativeTo(BoxCollider collider) {
        Bounds bound = collider.bounds;
        if (bound.getMinX() <= center.x && center.x <= bound.getMaxX()) {
            if (center.y <= bound.getMinY()) { // 사각형의 위에 위치
                //Debugger.Print("Relatively up");
                return Vector2.up;
            } else { // 사각형의 아래에 위치
                //Debugger.Print("Relatively down");
                return Vector2.down;
            }
        }

        if (bound.getMinY() <= center.y && center.y <= bound.getMaxY()) {
            if (center.x <= bound.getMinX()) { // 사각형의 왼쪽에 위치
                //Debugger.Print("Relatively left");
                return Vector2.left;
            } else { // 사각형의 오른쪽에 위치
                //Debugger.Print("Relatively right");
                return Vector2.right;
            }
        }

        if (center.x <= bound.getMinX() && center.y <= bound.getMinY()) { // 사각형의 왼쪽 상단에 위치
            //Debugger.Print("Relatively leftUp");
            return Vector2.leftUp;
        }

        if (center.x >= bound.getMaxX() && center.y <= bound.getMinY()) { // 사각형의 오른쪽 상단에 위치
            //Debugger.Print("Relatively rightUp");
            return Vector2.rightUp;
        }

        if (center.x <= bound.getMinX() && center.y >= bound.getMaxY()) { // 사각형의 왼쪽 하단에 위치
            //Debugger.Print("Relatively leftDown");
            return Vector2.leftDown;
        }

        if (center.x >= bound.getMaxX() && center.y >= bound.getMaxY()) { // 사각형의 오른쪽 하단에 위치
            //Debugger.Print("Relatively righttDown");
            return Vector2.rightDown;
        }

        //Debugger.Print("Relatively up");
        return Vector2.up;

    }
}

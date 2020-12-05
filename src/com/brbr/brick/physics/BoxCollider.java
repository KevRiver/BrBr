package com.brbr.brick.physics;

import com.brbr.brick.object.GameObject;
import com.brbr.brick.math.Bounds;
import com.brbr.brick.math.Vector2;

public class BoxCollider extends Collider {
    public Bounds bounds;

    public BoxCollider(ColliderType type) {
        super(type);
        bounds = new Bounds();
    }

    public BoxCollider(int width, int height, ColliderType type) {
        this(type);
        bounds = new Bounds(width, height);
    }

    @Override
    public void initWith(GameObject gameObject) {
        super.initWith(gameObject);
        setCenter(transform.position);
    }

    @Override
    public void setCenter(Vector2 position) {
        bounds.setCenter(position);
    }

    public void setBounds(int width, int height) {
        bounds.setBounds(width, height);
    }

    public Vector2 getPositionRelativeTo(BoxCollider collider) {
        Bounds b = collider.bounds;
        //Debugger.Print(collider.tag +" "+ b.toString());
        if (bounds.getMaxX() >= b.getMinX() && bounds.getMinX() < b.getMinX()) {
            // 상대적으로 왼쪽에 위치함
            //Debugger.Print("Relatively left");
            return Vector2.left;
        }
        if (bounds.getMinX() <= b.getMaxX() && bounds.getMaxX() > b.getMaxX()) {
            // 상대적으로 오른쪽에 위치함
            //Debugger.Print("Relatively right");
            return Vector2.right;
        }
        if (bounds.getMaxY() >= b.getMinY() && bounds.getMinY() < b.getMinY()) {
            //Debugger.Print("Relatively down");
            // 상대적으로 아래쪽에 위치함
            return Vector2.down;
        }
        //Debugger.Print("Relatively up");
        return Vector2.up;

    }
}

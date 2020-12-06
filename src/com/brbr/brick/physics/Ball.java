package com.brbr.brick.physics;

import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Bounds;
import com.brbr.brick.object.GameObject;
import com.brbr.brick.math.MathExtension;
import com.brbr.brick.math.Vector2;

public class Ball extends GameObject {
    private final int ballSpeed = 500;
    private boolean isMoving = false;
    private Collider collider;
    public Vector2 direction;
    public boolean needToDestroy = false;

    public Ball(int x, int y) {
        super(x, y);
        collider = (CircleCollider) (addComponent(new CircleCollider(Coordinates.BALL_SIZE / 2f)));
        collider.setTag("Ball");
        collider.type = ColliderType.KINEMATIC;
        direction = new Vector2();
    }

    public void setDirection(double rot) {
        double rad = MathExtension.deg2rad(rot);
        direction.x = Math.cos(rad);
        direction.y = Math.sin(rad);
    }

    public void setDirection(Vector2 dir) {
        Vector2.normalize(dir);
        direction.x = dir.x;
        direction.y = dir.y;
    }

    public void throwBall(double rot) {
        setDirection(rot);
        isMoving = true;
    }

    public void throwBall() {
        Debugger.Print("throw ball");
        isMoving = true;
    }

    public void update(double dt) {
        if (!isMoving) return;
        transform.translate(direction.multiply(ballSpeed * dt));

        collider.setCenter(transform.position);
    }

    public void changeRot(int status) {
        if (status == 1)
            transform.rotation = Math.PI - transform.rotation;

        else
            transform.rotation = -transform.rotation;

        while (transform.rotation < -Math.PI * 2) {
            transform.rotation += Math.PI * 2;
        }

        while (transform.rotation > Math.PI * 2) {
            transform.rotation -= Math.PI * 2;
        }
    }

    @Override
    public void onCollisionEnter(Collider collider) {
        if (collider.tag.equals("brick") || collider.tag.equals("wall")) {
            Vector2 relativePosition = this.collider.getPositionRelativeTo((BoxCollider) collider);
            if (relativePosition.equals(Vector2.up) || relativePosition.equals(Vector2.down)) {
                direction.y *= -1;
            } else if (relativePosition.equals(Vector2.left) || relativePosition.equals(Vector2.right)) {
                direction.x *= -1;
            } else {
                Vector2 collisionPos;
                Bounds bound = ((BoxCollider) collider).bounds;
                if (relativePosition.equals(Vector2.leftUp))
                    collisionPos = new Vector2(bound.getMinX(), bound.getMinY());

                else if (relativePosition.equals(Vector2.leftDown))
                    collisionPos = new Vector2(bound.getMinX(), bound.getMaxY());

                else if (relativePosition.equals(Vector2.rightUp))
                    collisionPos = new Vector2(bound.getMaxX(), bound.getMinY());

                else
                    collisionPos = new Vector2(bound.getMaxX(), bound.getMaxY());

                double rot = Math.atan2(transform.position.y - collisionPos.y, transform.position.x - collisionPos.x);
                setDirection(MathExtension.rad2deg(rot));
            }
            transform.translate(relativePosition);
            this.collider.setCenter(transform.position);
        } else if (collider.tag.equals("wall_bottom")) {
            needToDestroy = true;
        }
    }

}

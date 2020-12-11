package com.brbr.brick.physics;

import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Bounds;
import com.brbr.brick.object.GameObject;
import com.brbr.brick.math.MathExtension;
import com.brbr.brick.math.Vector2;
import com.brbr.brick.render.CircleRenderComponent;

import java.awt.*;

public class Ball extends GameObject { // 게임에서 발사되는 공의 클래스
    private final int ballSpeed = 500;
    private boolean isMoving = false;
    private Collider collider;
    private CircleRenderComponent circleRenderComponent;
    public Vector2 direction;
    public boolean needToDestroy = false;

    public Ball(int x, int y) {
        super(x, y);
        collider = (CircleCollider) (addComponent(new CircleCollider(Coordinates.BALL_SIZE / 2f)));
        collider.setTag("Ball");
        collider.type = ColliderType.KINEMATIC;

        circleRenderComponent = ((CircleRenderComponent) this.addComponent(new CircleRenderComponent(this.transform.position, Coordinates.BALL_SIZE / 2)));
        circleRenderComponent.stroke = 3;
        circleRenderComponent.color = Color.RED;

        direction = new Vector2();
    }

    public void setDirection(double rot) { // degree를 받아와서 cos, sin값으로 direction을 지정해주는 메서드
        double rad = MathExtension.deg2rad(rot);
        direction.x = Math.cos(rad);
        direction.y = Math.sin(rad);
    }

    public void setDirection(Vector2 dir) { // 방향 값을 가져와서 direction에 저장하는 메서드
        direction.x = dir.x;
        direction.y = dir.y;
    }

    public void throwBall() {
        isMoving = true;
    } // 볼을 발사할 때 호출해주는 메서드, isMoving이 true일 때만 공이 움직임

    public void update(double dt) { // 매 프레임 마다 공을 움직여주는 메서드
        if (!isMoving) return;
        transform.translate(direction.multiply(ballSpeed * dt));
        circleRenderComponent.setPosition(transform.position);

        collider.setCenter(transform.position);
    }

    @Override
    public void onCollisionEnter(Collider collider) { // 공이 어떤 GameObejct와 충돌되었을 때 호출되는 메서드
        // 벽이나 벽돌에 닿았을 경우 direction값을 수정하여 공이 튕기게 해준다.
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

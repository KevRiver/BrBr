package com.brbr.brick.object;

import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.math.Vector2;
import com.brbr.brick.physics.CircleCollider;
import com.brbr.brick.physics.Collider;
import com.brbr.brick.physics.ColliderType;
import com.brbr.brick.render.DelegateRenderComponent;
import com.brbr.brick.render.DrawDelegate;

import java.awt.*;

public class BallItem extends AnimationObject {
    private boolean isMoving = false;
    public float moveAnimatedValue = 1f;
    public boolean isEaten = false;
    private final int radius = Coordinates.ITEM_SIZE;
    private BallItemDrawDelegate ballItemDrawDelegate;
    private DelegateRenderComponent delegateRenderComponent;
    private CircleCollider circleCollider;

    protected BallItem(){
        super();
    }
    public BallItem(Vector2 _position){
        this();
        circleCollider = ((CircleCollider) this.addComponent(new CircleCollider(radius / 2, ColliderType.STATIC)));
        circleCollider.setTag("item");

        ballItemDrawDelegate = new BallItemDrawDelegate();
        delegateRenderComponent = ((DelegateRenderComponent) this.addComponent(new DelegateRenderComponent(ballItemDrawDelegate)));

        setPosition(_position);
    }

    @Override
    public void setPosition(Vector2 _position) {
        super.setPosition(_position);
        circleCollider.setCenter(_position);
    }

    @Override
    float getSpeed() {
        return 1 / 600f;
    }

    public void animateMove() {
        moveAnimatedValue = 0f;
        isMoving = true;
    }

    @Override
    public void update(long dt) {
        super.update(dt);
        if (!isMoving) return;

        moveAnimatedValue = moveAnimatedValue + (1 / 150f) * dt;
        if (moveAnimatedValue > 1) {
            isMoving = false;
            moveAnimatedValue = 1f;
        }
    }
    
    @Override
    public void onCollisionEnter(Collider collider) {
        this.isEaten = true;
    }

    class BallItemDrawDelegate extends DrawDelegate{
        @Override
        public void draw(Graphics g) {
            float x = ((float) transform.position.x);
            float y = ((float) transform.position.y) - (1 - moveAnimatedValue) * Coordinates.BRICK_HEIGHT;
            float minx, miny, animatedRadius;

            animatedRadius = Coordinates.ITEM_SIZE + 5 + animatedValue * 6;
            minx = x - animatedRadius;
            miny = y - animatedRadius;
            g.setColor(Color.GREEN);
            g.fillOval(Math.round(minx), Math.round(miny), Math.round(animatedRadius * 2), Math.round(animatedRadius * 2));

            animatedRadius = Coordinates.ITEM_SIZE + animatedValue * 6;
            minx = x - animatedRadius;
            miny = y - animatedRadius;
            g.setColor(Color.WHITE);
            g.fillOval(Math.round(minx), Math.round(miny), Math.round(animatedRadius * 2), Math.round(animatedRadius * 2));

            animatedRadius = Coordinates.ITEM_SIZE;
            minx = x - animatedRadius;
            miny = y - animatedRadius;

            g.setColor(Color.GREEN);
            g.fillOval(Math.round(minx), Math.round(miny), Math.round(animatedRadius * 2), Math.round(animatedRadius * 2));
        }
    }
}

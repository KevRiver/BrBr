package com.brbr.brick.object;

import com.brbr.brick.assets.Colors;
import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.math.Vector2;
import com.brbr.brick.physics.BoxCollider;
import com.brbr.brick.physics.Collider;
import com.brbr.brick.physics.ColliderType;
import com.brbr.brick.render.DelegateRenderComponent;
import com.brbr.brick.render.DrawDelegate;

import javax.swing.*;
import java.awt.*;

// 충돌하면 체력이 깎기는 벽돌 객체
public class Brick extends AnimationObject {
    public int health;
    private boolean isMoving = false;
    private BoxCollider boxCollider;
    private BrickDrawDelegate brickDrawDelegate;
    private DelegateRenderComponent delegateRenderComponent;

    public Brick() {
        animatedValue = 1f;
    }

    public Brick(Vector2 _position, int _health){
        this();
        transform.position.x = _position.x;
        transform.position.y = _position.y;
        health = _health;
        boxCollider = ((BoxCollider) this.addComponent(new BoxCollider(Coordinates.BRICK_WIDTH, Coordinates.BRICK_HEIGHT, ColliderType.STATIC)));
        boxCollider.setTag("brick");

        brickDrawDelegate = new BrickDrawDelegate();
        delegateRenderComponent = ((DelegateRenderComponent) this.addComponent(new DelegateRenderComponent(brickDrawDelegate)));
    }

    public void setPosition(Vector2 _position){
        transform.position.x = _position.x;
        transform.position.y = _position.y;
        boxCollider.setCenter(transform.position);
    }

    @Override
    float getSpeed() {
        if (isMoving) return 1 / 150f;
        else return 0f;
    }

    public void animateMove() {
        animatedValue = 0f;
        isMoving = true;
    }

    @Override
    public void update(long dt) {
        if (!isMoving) return;

        animatedValue = animatedValue + getSpeed() * dt;
        if (animatedValue > 1) {
            isMoving = false;
            animatedValue = 1f;
        }
    }

    @Override
    public void onCollisionEnter(Collider collider) {
        this.health--;
    }

    class BrickDrawDelegate extends DrawDelegate{
        public final int width = Coordinates.BRICK_WIDTH;
        public final int height = Coordinates.BRICK_HEIGHT;
        @Override
        public void draw(Graphics g) {
            ((Graphics2D) g).setStroke(new BasicStroke(1));
            int max = Colors.BRICK_COLOR_LEVEL.length - 1;
            int healthLevel = health - 1;
            int x = ((int) transform.position.x);
            int y = ((int) transform.position.y);
            float minx = x - width / 2;
            float miny = y - height / 2;
            float lerp = (1 - animatedValue) * height * -1;
            g.setColor(Colors.BRICK_COLOR_LEVEL[healthLevel <= max ? healthLevel : max]);
            g.fillRect(((int) minx),
                    ((int) (miny + lerp)),
                    width,
                    height);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(health), x, ((int) (y + lerp)));
        }
    }
}

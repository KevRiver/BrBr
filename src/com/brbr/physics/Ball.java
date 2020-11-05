package com.brbr.physics;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import com.brbr.brick.object.GameObject;
import com.brbr.debug.Debugger;
import com.brbr.math.Bounds;
import com.brbr.math.MathExtention;
import com.brbr.math.Transform;
import com.brbr.math.Vector2;

public class Ball extends GameObject {
    public final int size = 20;
    private final int ballSpeed = 500;
    private boolean isMoving = false;
    private BoxCollider collider;
    public Vector2 direction;

    public Ball(){
        super();
        transform = new Transform((int)(Math.random() * (400 - size)), 680 - size);
        collider = (BoxCollider)(addComponent(new BoxCollider(size, size)));
        collider.setTag("Ball");
        collider.type = ColliderType.KINEMATIC;
        direction = new Vector2();
    }

    public Ball(int x, int y){
        super(x,y);
        collider = (BoxCollider)(addComponent(new BoxCollider(size, size)));
        collider.setTag("Ball");
        collider.type = ColliderType.KINEMATIC;
        direction = new Vector2();
    }

    public void setDirection(double rot){
        double rad = MathExtention.deg2rad(rot);
        direction.x = Math.cos(rad);
        direction.y = Math.sin(rad);
    }

    public void throwBall(double rot){
        setDirection(rot);
        isMoving = true;
    }

    public void update(double dt){
        if(!isMoving) return;
        transform.translate(direction.multiply(ballSpeed * dt));

        collider.bounds.setCenter(transform.position);

    }

    public void changeRot(int status){
        if(status == 1)
            transform.rotation = Math.PI - transform.rotation;

         else
            transform.rotation = -transform.rotation;

        while(transform.rotation < -Math.PI * 2) {
            transform.rotation += Math.PI * 2;
        }

        while(transform.rotation > Math.PI * 2) {
            transform.rotation -= Math.PI * 2;
        }
    }

    @Override
    public void onCollisionEnter(Collider collider){
        Vector2 relativePosition = this.collider.getPositionRelativeTo((BoxCollider)collider);
        if(relativePosition.equals(Vector2.up) || relativePosition.equals(Vector2.down)){
            direction.y *= -1;
        }else{
            direction.x *= -1;
        }
    }
}

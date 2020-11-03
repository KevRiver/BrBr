package com.brbr.physics;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import com.brbr.debug.Debugger;
import com.brbr.math.Transform;
import com.brbr.math.Vector2;

public class Ball extends Collider{
    public final int size = 25;
    private final int ballSpeed = 300;
    private boolean isMoving = false;
    private Transform transform;

    public Ball(){
        super(new Rectangle(0,0, 25, 25));
        transform = new Transform((int)(Math.random() * (400 - size)), 680 - size);
    }

    public Ball(int x, int y){
        super(new Rectangle(0,0, 25, 25));
        transform = new Transform(x, y);
    }

    public void throwBall(double rot){
        transform.rotation = rot;
        isMoving = true;
    }

    public void update(double dt){
        if(!isMoving) return;
        transform.translate(new Vector2(Math.cos(transform.rotation) * ballSpeed * dt,
                Math.sin(transform.rotation) * ballSpeed * dt));

        rectangle2D.setRect(transform.position.x, transform.position.y,size,size);

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

    public void onCollisionEnter(Collider collider){
        if((collider.rectangle2D) != null) {
            if(collider.rectangle2D.getX() <= rectangle2D.getX() &&
                    rectangle2D.getX() <= collider.rectangle2D.getX() + collider.rectangle2D.getWidth()  &&
                    collider.rectangle2D.getY() < rectangle2D.getY() &&
                    rectangle2D.getY() + size < collider.rectangle2D.getY()+collider.rectangle2D.getHeight()){//오른쪽 충돌
                transform.position.x += 2;
                rectangle2D.setRect(transform.position.x, transform.position.y,size,size);
                changeRot(1);
                Debugger.Print("왼벽 충돌");
            }
            if(collider.rectangle2D.getX() <= rectangle2D.getX() + size &&
                    rectangle2D.getX() + size <= collider.rectangle2D.getX() + collider.rectangle2D.getWidth() &&
                    collider.rectangle2D.getY() < rectangle2D.getY() &&
                    rectangle2D.getY() + size < collider.rectangle2D.getY()+collider.rectangle2D.getHeight()){//왼쪽 충돌
                Debugger.Print("오른벽 충돌");
                transform.position.x -= 2;
                rectangle2D.setRect(transform.position.x, transform.position.y,size,size);
                changeRot(1);

            }
            if(collider.rectangle2D.getY() <= rectangle2D.getY() &&
                    rectangle2D.getY() <= collider.rectangle2D.getY() + collider.rectangle2D.getHeight() &&
                    collider.rectangle2D.getX() < rectangle2D.getX() &&
                    rectangle2D.getX() + size < collider.rectangle2D.getX() + collider.rectangle2D.getWidth() ){//아래쪽 충돌
                Debugger.Print("윗벽 충돌");
                transform.position.y += 2;
                rectangle2D.setRect(transform.position.x, transform.position.y,size,size);
                changeRot(0);
            }
            if(collider.rectangle2D.getY() <= rectangle2D.getY() + size &&
                    rectangle2D.getY() + size <= collider.rectangle2D.getY() + collider.rectangle2D.getHeight() &&
                    collider.rectangle2D.getX() < rectangle2D.getX() &&
                    rectangle2D.getX() + size < collider.rectangle2D.getX() + collider.rectangle2D.getWidth()){//위 충돌
                Debugger.Print("아랫벽 충돌");
                transform.position.y -= 2;
                rectangle2D.setRect(transform.position.x, transform.position.y,size,size);
                changeRot(0);
            }
        }
    }

    public Transform getTransform(){
        return transform;
    }
}

package com.brbr.physics;

import com.brbr.brick.Canvas;
import com.brbr.debug.Debugger;
import com.brbr.math.Transform;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class TestPhysics extends JFrame {
    public class Canvas extends JPanel {
        Ball ball;

        public Canvas(Ball ball){
            this.ball = ball;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Transform transform = ball.getTransform();
            g.fillOval((int)transform.position.x, (int)transform.position.y, ball.size , ball.size);

            for(Collider wall: walls){

                g.fillRect((int)wall.rectangle2D.getX(), (int)wall.rectangle2D.getY(),
                        (int)wall.rectangle2D.getWidth(), (int)wall.rectangle2D.getHeight());
            }
        }
    }

    private Canvas canvas;
    private PhysicManager physicManager;
    private Ball ball;
    private Collider[] walls;
    private final int width = 400;
    private final int height = 700;
    private double lastTime = 0;

    public TestPhysics(){
        physicManager  = PhysicManager.getInstance();

        ball = new Ball();

        walls = new Collider[4];
        walls[0] = new Collider(new Rectangle(0,0,width, 10), ColliderType.STATIC); // top wall
        walls[1] = new Collider(new Rectangle(width - 10,0,10, height), ColliderType.STATIC); // right wall
        walls[2] = new Collider(new Rectangle(0,height - 20,width, 10), ColliderType.STATIC); // bottom wall
        walls[3] = new Collider(new Rectangle(0,0,10, height), ColliderType.STATIC); // left wall

        for(int i = 0; i < 4; i++)
            physicManager.addEntity(walls[i]);

        physicManager.addEntity(ball);

        physicManager.collisionCheck();
        ball.throwBall(-1.1);

        canvas = new Canvas(ball);
        this.add(canvas);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(width, height));
        setVisible(true);

        lastTime = System.currentTimeMillis();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double dt = (System.currentTimeMillis() - lastTime) / 1000.0;
                lastTime = System.currentTimeMillis();
                ball.update(dt);
                physicManager.collisionCheck();
                this.repaint();
            }
        }).start();
    }


    public static void main(String[] args){
        new TestPhysics();
    }
}

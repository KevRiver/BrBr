package com.brbr.physics;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TestPhysics extends JFrame {

    private PhysicManager physicManager;
    private Collider[] colliders;

    public TestPhysics(){
        physicManager  = PhysicManager.getInstance();
        colliders = new Collider[3];
        colliders[0] = new Collider(new Rectangle(185,340,100,100),false);
        colliders[1] = new Collider(new Rectangle(85,340,100,100));
        colliders[2] = new Collider(new Rectangle(185,340,50,50));

        for(int i=0; i <3;i++){
           physicManager.addEntity(colliders[i]);
        }
        physicManager.collisionCheck();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(370,680));
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        final Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};
        int i = 0;
        for(var c: colliders){
            Rectangle2D r = c.rectangle2D;
            g.setColor(colors[i++]);
            g.fillRect((int)r.getX(),(int)r.getY(),(int)r.getWidth(),(int)r.getHeight());
        }
    }

    public static void main(String[] args){
        new TestPhysics();
    }
}

package com.brbr.brick;
import com.brbr.debug.Debugger;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseEventListener extends MouseInputAdapter {
    private boolean active;
    private Point source;
    private Point destination;

    // Constructor
    public MouseEventListener(){
        Init();
    }

    public void Init(){
        active = false;

        source = new Point();
        destination = new Point();
    } // Init

    // Getter
    public Point getSource(){return source;}
    public Point getDestination(){return destination;}

    // Setter
    public void setActive(boolean activation){
        active = activation;
    }
    protected void setSource(int x, int y){
        source.x = x;
        source.y = y;
    }
    protected void setDestination(int x, int y){
        destination.x = x;
        destination.y = y;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        try {
            source.setLocation(new Point(e.getX(), e.getY()));
            destination.setLocation(new Point(e.getX(), e.getY()));
            Debugger.Print("Mouse Pressed ",source);
        }catch (Exception exception){
            System.out.println(exception.toString());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        try{
            destination.setLocation(e.getX(),e.getY());
            Debugger.Print("Mouse Dragged ",destination);
        }catch (Exception exception){
            System.out.println(exception.toString());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try{
            Point dest = destination.getLocation();
            Debugger.Print("Mouse Released ", dest);
        }catch (Exception exception){
            System.out.println(exception.toString());
        }
    }


}

package com.brbr.brick;
import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseEventListener implements MouseListener, MouseMotionListener {
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
    public Vector2 getRelativePosition(Vector2 point){
        Vector2 ret = new Vector2(point.x - destination.x, point.y - destination.y);
        return ret;
    }

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
            source.setLocation(e.getX(),e.getY());
            Debugger.Print("Mouse Pressed ",source);

        }catch (Exception exception){
            System.out.println(exception.toString());
        }
    }

    public void mouseReleased(MouseEvent e) {
        try{
            destination.setLocation(e.getX(),e.getY());
            Debugger.Print("Mouse Released ",destination);

        }catch (Exception exception){
            System.out.println(exception.toString());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        try{
            destination.setLocation(e.getX(),e.getY());

        }catch (Exception exception){
            System.out.println(exception.toString());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}


}

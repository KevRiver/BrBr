package com.brbr.brick;
import com.brbr.debug.Debugger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseEventListener implements MouseListener, MouseMotionListener {
    private boolean active;
    public Point source;
    public Point destination;

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
    //public Point getSource(){return source;}
    //public Point getDestination(){return destination;}

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

            ((JPanel)e.getSource()).repaint();
            Debugger.Print("Dest after release",destination);
            Debugger.Print("Src after release",source);
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

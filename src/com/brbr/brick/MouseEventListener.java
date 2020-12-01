package com.brbr.brick;
import com.brbr.brick.debug.Debugger;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import com.brbr.brick.event.DragEventBus;
import com.brbr.brick.event.PressEventBus;
import com.brbr.brick.event.ReleaseEventBus;

public class MouseEventListener implements MouseListener, MouseMotionListener {
    private boolean active;

    // Constructor
    public MouseEventListener(){ }


    // Setter
    public void setActive(boolean activation){
        active = activation;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            PressEventBus.getInstance().raise(e);
            Debugger.Print("Mouse Pressed ");
        }catch (Exception exception){
            System.out.println(exception.toString());
        }
    }

    public void mouseReleased(MouseEvent e) {
        try{
            ReleaseEventBus.getInstance().raise(e);
            Debugger.Print("Mouse Released ");
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
            DragEventBus.getInstance().raise(e);
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

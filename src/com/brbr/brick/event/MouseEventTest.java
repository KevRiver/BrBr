package com.brbr.brick.event;

import com.brbr.brick.debug.Debugger;
import com.brbr.brick.object.BallShooter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseEventTest {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500,500));
        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                DragEventBus.getInstance().raise(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                PressEventBus.getInstance().raise(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ReleaseEventBus.getInstance().raise(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        BallShooter ballShooter = new BallShooter(250, 480);

        frame.setVisible(true);
    }
}

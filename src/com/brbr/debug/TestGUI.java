package com.brbr.debug;

import com.brbr.brick.InputManager;
import com.brbr.brick.MouseEventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TestGUI {
/*
    TestGUI(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        BRPanel primary = new BRPanel();

        frame.getContentPane().add(primary, BorderLayout.CENTER);
        frame.setSize(370,680);
        frame.setVisible(true);
    }
*/
/*
    class BRPanel extends JPanel{
        Point src, dest;
        public BRPanel(){
            src = new Point();
            dest = new Point();
            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    src = e.getPoint();
                    Debugger.Print("Pressed: ",src);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    dest = e.getPoint();
                    Debugger.Print("Released: ",dest);
                    repaint();
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            this.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    dest = e.getPoint();
                    repaint();
                }

                @Override
                public void mouseMoved(MouseEvent e) {

                }
            });
        }

        @Override
        public void paint(Graphics g) {
            Debugger.Print("paintComponent called");
            super.paint(g);
            if(src == null || dest == null) return;
            g.setColor(Color.ORANGE);
            g.drawLine(src.x, src.y, dest.x, dest.y);
        }
    }
*/
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel primary = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                MouseEventListener ml = InputManager.getInstance().mouseEventListener;
                Debugger.Print("obtained src: ",ml.source);
                Debugger.Print("obtained dest: ",ml.destination);
                g.drawLine(ml.source.x, ml.source.y, ml.destination.x, ml.destination.y);
            }
        };
        primary.addMouseListener(InputManager.getInstance().mouseEventListener);
        primary.addMouseMotionListener(InputManager.getInstance().mouseEventListener);

        frame.getContentPane().add(primary);
        frame.setSize(370,680);
        frame.setVisible(true);

    }
}

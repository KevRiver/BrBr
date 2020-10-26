package com.brbr.debug;

import com.brbr.brick.InputManager;

import javax.swing.*;
import java.awt.*;

public class TestGUI {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(370, 680));

        JPanel primary = new JPanel();
        primary.setPreferredSize(new Dimension(370 ,680));
        primary.addMouseMotionListener(InputManager.getInstance().mouseEventListener);
        InputManager.getInstance().setActive(true);

        frame.getContentPane().add(primary);
        frame.pack();
        frame.setVisible(true);

    }
}

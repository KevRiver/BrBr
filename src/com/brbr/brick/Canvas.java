package com.brbr.brick;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JPanel {

    private ArrayList<Ball> ballList;

    public Canvas(ArrayList<Ball> ballList) {
        this.ballList = ballList;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Ball ball : ballList) {
            g.drawOval(ball.x, ball.y, 50, 50);
        }
    }
}

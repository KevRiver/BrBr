package com.brbr.brick.render;

import com.brbr.brick.assets.Colors;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawBackground(g);
    }

    private void drawBackground(Graphics g) {
        g.setColor(Colors.BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}

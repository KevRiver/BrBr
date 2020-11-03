package com.brbr.brick.render;

import com.brbr.brick.GameObject;
import com.brbr.brick.Scene;
import com.brbr.brick.assets.Colors;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {

    private Scene scene;

    public Renderer(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawBackground(g);
        drawGameFrame(g);
        drawGameObject(g);
    }

    private void drawBackground(Graphics g) {
        g.setColor(Colors.BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawGameFrame(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 100, getWidth(), 10);
        g.fillRect(0, 700, getWidth(), 10);
    }

    // game grid : w*h = 8*6. (상단에 1칸, 상하좌우 5px 여 있음)
    // brick : w*h = 95 * 60.
    private void drawGameObject(Graphics g) {
        for (GameObject gameObject : scene.gameObjectList) {
            g.setColor(Color.RED);
            int x = (int) gameObject.transform.position.x;
            int y = (int) gameObject.transform.position.y;
            System.out.println("x : " + x + " y : " + y);

            g.fillRect(
                    x * 95 + 5 * (x + 1),
                    y * 60 + 110 + 5 * (y + 1),
                    95,
                    60
            );
            // TODO : 상수 정리
        }
    }
}

package com.brbr.brick.render;

import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.object.Brick;
import com.brbr.brick.object.GameObject;
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
        g.fillRect(0, Coordinates.GAME_FRAME_Y, getWidth(), Coordinates.GAME_FRAME_STROKE);
        g.fillRect(
                0,
                Coordinates.GAME_FRAME_Y + Coordinates.GAME_FRAME_STROKE +
                        Coordinates.BRICK_HEIGHT * Coordinates.BRICK_GRID_HEIGHT +
                        (Coordinates.BRICK_MARGIN + 1) * Coordinates.BRICK_GRID_HEIGHT,
                getWidth(),
                Coordinates.GAME_FRAME_STROKE
        );
    }

    // game grid : w*h = 8*6. (상단에 1칸, 상하좌우 5px 여 있음)
    // brick : w*h = 95 * 60.
    private void drawGameObject(Graphics g) {
        for (GameObject gameObject : scene.gameObjectList) {
            if (gameObject instanceof Brick) {
                g.setColor(Color.RED);

                g.fillRect(
                        ((Brick) gameObject).getAbsoluteX(),
                        Coordinates.GAME_FRAME_Y + Coordinates.GAME_FRAME_STROKE +
                                ((Brick) gameObject).getAbsoluteY(),
                        Coordinates.BRICK_WIDTH,
                        Coordinates.BRICK_HEIGHT
                );
            }
        }
    }
}

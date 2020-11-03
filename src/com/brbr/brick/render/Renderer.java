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
        int minBrickHealth = Integer.MAX_VALUE;
        int maxBrickHealth = Integer.MIN_VALUE;
        for (GameObject gameObject : scene.gameObjectList) {
            if (gameObject instanceof Brick) {
                int health = ((Brick) gameObject).health;
                if (minBrickHealth > health) minBrickHealth = health;
                if (maxBrickHealth < health) maxBrickHealth = health;
            }
        }
        int[] healthLevelStep = new int[Colors.BRICK_COLOR_LEVEL.length];
        for (int i = 0; i < Colors.BRICK_COLOR_LEVEL.length; i++) {
            healthLevelStep[i] = (maxBrickHealth - minBrickHealth) /
                    Colors.BRICK_COLOR_LEVEL.length * i +
                    minBrickHealth;
        }

        for (GameObject gameObject : scene.gameObjectList) {
            if (gameObject instanceof Brick) {
                Brick brick = (Brick) gameObject;
                int healthLevel = 0;
                for (int i = 0; i < Colors.BRICK_COLOR_LEVEL.length; i++) {
                    if (brick.health <= healthLevelStep[i]) {
                        healthLevel = i;
                        break;
                    }
                }
                g.setColor(Colors.BRICK_COLOR_LEVEL[healthLevel]);
                g.fillRect(
                        brick.getAbsoluteX(),
                        Coordinates.GAME_FRAME_Y + Coordinates.GAME_FRAME_STROKE + brick.getAbsoluteY(),
                        Coordinates.BRICK_WIDTH,
                        Coordinates.BRICK_HEIGHT
                );

                g.setColor(Color.WHITE);
                g.drawString(
                        String.valueOf(brick.health),
                        brick.getAbsoluteX() + Coordinates.BRICK_WIDTH / 2,
                        Coordinates.GAME_FRAME_Y + Coordinates.GAME_FRAME_STROKE + brick.getAbsoluteY()
                                + Coordinates.BRICK_HEIGHT / 2
                );
            }
        }
    }
}

package com.brbr.brick.render;

import com.brbr.brick.InputManager;
import com.brbr.brick.UI.UI;
import com.brbr.brick.UI.UIManager;
import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Vector2;
import com.brbr.brick.object.BallItem;
import com.brbr.brick.object.Brick;
import com.brbr.brick.object.GameObject;
import com.brbr.brick.Scene;
import com.brbr.brick.assets.Colors;
import com.brbr.brick.object.Wall;
import com.brbr.brick.physics.Ball;
import com.brbr.brick.physics.BoxCollider;
import com.brbr.brick.physics.CircleCollider;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {
    private Scene scene;
    private UIManager uiManager;

    private InputManager inputManager; //Todo: delete

    public Renderer(Scene scene) {
        this.scene = scene;
        uiManager = UIManager.getInstance();

        //Todo: delete
        inputManager = InputManager.getInstance();
        addMouseListener(inputManager.mouseEventListener);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawBackground(g);
        //drawGameFrame(g);
        drawGameObject(g);

        drawDebugText(g);

        uiManager.drawUI(g, getWidth(), getHeight());
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
                Coordinates.GAME_FRAME_Y + Coordinates.GAME_FRAME_STROKE + scene.frameHeight,
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
            healthLevelStep[i] = (int) ((maxBrickHealth - minBrickHealth) /
                    (float) Colors.BRICK_COLOR_LEVEL.length * (i + 1) +
                    minBrickHealth);
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
                BoxCollider collider = (BoxCollider) (brick.getComponent("BoxCollider"));

                g.setColor(Colors.BRICK_COLOR_LEVEL[healthLevel]);
                g.fillRect(
                        (int) (collider.bounds.getMinX()),
                        (int) (collider.bounds.getMinY() - (1 - brick.animatedValue) * Coordinates.BRICK_HEIGHT),
                        collider.bounds.getWidth(),
                        collider.bounds.getHeight()
                );

                g.setColor(Color.WHITE);
                g.drawString(
                        String.valueOf(brick.health),
                        (int) (collider.bounds.getCenter().x),
                        (int) (collider.bounds.getCenter().y)
                );
            } else if (gameObject instanceof Ball) {
                Ball ball = (Ball) gameObject;
                CircleCollider circle = ((CircleCollider)ball.getComponent("CircleCollider"));
                Vector2 position = new Vector2(circle.center.x - circle.radius, circle.center.y - circle.radius);
                g.setColor(Color.RED);
                g.drawOval((int)position.x, (int)position.y, ball.size, ball.size );
            } else if (gameObject instanceof Wall) {
                Wall wall = (Wall) gameObject;
                int x, y, width, height;
                BoxCollider collider = (BoxCollider)(wall.getComponent("BoxCollider"));
                x = (int)(collider.bounds.getMinX());
                y = (int)(collider.bounds.getMinY());
                width = collider.bounds.getWidth();
                height = collider.bounds.getHeight();
                g.setColor(Color.BLACK);
                g.fillRect(x,y,width,height);
            } else if (gameObject instanceof BallItem) {
                BallItem ballItem = (BallItem) gameObject;
                int x = (int) ballItem.transform.position.x;
                int y = (int) ballItem.transform.position.y;

                g.setColor(Color.GREEN);
                drawOval(g, x, y, 15 + ballItem.animatedValue * 6);
                g.setColor(Color.WHITE);
                drawOval(g, x, y, 10 + ballItem.animatedValue * 6);
                g.setColor(Color.GREEN);
                drawOval(g, x, y, 10);
            }
        }
    }

    private void drawOval(Graphics g, float centerX, float centerY, float radius) {
        float x = centerX - radius;
        float y = centerY - radius;
        g.fillOval(
                Math.round(x),
                Math.round(y),
                Math.round(radius * 2),
                Math.round(radius * 2)
        );
    }

    private void drawDebugText(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("fps : " + scene.framePerSecond, 0, 15);
    }
}

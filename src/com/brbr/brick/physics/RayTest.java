package com.brbr.brick.physics;

import com.brbr.brick.MouseEventListener;
import com.brbr.brick.Scene;
import com.brbr.brick.assets.Colors;
import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.debug.Debugger;
import com.brbr.brick.object.Brick;
import com.brbr.brick.object.GameObject;
import com.brbr.brick.object.Wall;
import com.brbr.brick.physics.*;
import com.brbr.brick.render.Renderer;
import com.brbr.brick.math.Transform;
import com.brbr.brick.math.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

public class RayTest extends JFrame {
    private Scene scene;
    private Renderer renderer;
    private PhysicManager physicManager;
    private MouseEventListener mouseEventListener;
    private Vector2 source;
    private Vector2 destination;
    private Vector2 direction;
    private final static int GAME_WIDTH = 605;
    private final static int GAME_HEIGHT = 800;

    public RayTest(){
        init();
    }

    private void init() {
        source = new Vector2();
        destination = new Vector2();
        direction = new Vector2();
        mouseEventListener = new MouseEventListener();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // TODO : init managers
        scene = new Scene();
        renderer = new Renderer(scene);
        physicManager = PhysicManager.getInstance();
        renderer.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //((JPanel) e.getSource()).repaint();

                Graphics g = ((JPanel) e.getSource()).getGraphics();
                Graphics2D g2 = ((Graphics2D) g);
                g2.setColor(Colors.LINE_COLOR);
                destination.x = e.getX();
                destination.y = e.getY();
                direction = Vector2.subtract(destination, source);
                Vector2.normalize(direction);
                ArrayList<Vector2> rayPathPoints = (ArrayList<Vector2>) physicManager.getRayPath(source, direction, 1000);

                g2.setStroke(new BasicStroke(5));
                for(int i = 0; i < rayPathPoints.size() - 1; i++){
                    int sx = ((int) rayPathPoints.get(i).x);
                    int sy = ((int) rayPathPoints.get(i).y);
                    int dx = ((int) rayPathPoints.get(i + 1).x);
                    int dy = ((int) rayPathPoints.get(i + 1).y);
                    g2.draw(new Line2D.Double(sx,sy,dx,dy));
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        renderer.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                destination.x = e.getX();
                destination.y = e.getY();


                // set Direction
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // reset destination
                Graphics g = ((JPanel) e.getSource()).getGraphics();
                ((JPanel) e.getSource()).repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        source.x = GAME_WIDTH / 2;
        source.y = GAME_HEIGHT - 250;

        // init scene frame
        scene.frameMarginTop = Coordinates.GAME_FRAME_Y;
        scene.frameWidth = GAME_WIDTH;
        scene.frameHeight = Coordinates.BRICK_HEIGHT * Coordinates.BRICK_GRID_HEIGHT +
                (Coordinates.BRICK_MARGIN + 1) * Coordinates.BRICK_GRID_HEIGHT;

        createDummyData();

        frame.getContentPane().add(renderer);
        frame.setSize(GAME_WIDTH, GAME_HEIGHT);
        frame.setVisible(true);
    }

    // dummy data TODO : remove
    private void createDummyData() {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            Vector2 vector2 = new Vector2();
            vector2.x = (float) (random.nextInt(6)) * Coordinates.BRICK_WIDTH;
            vector2.y = (float) (random.nextInt(8)) * Coordinates.BRICK_HEIGHT;
            Brick brick = new Brick();
            Transform transform = new Transform();
            transform.translate(vector2);
            brick.transform = transform;
            brick.health = random.nextInt(50) + 1;
            ((BoxCollider) brick.addComponent(new BoxCollider(Coordinates.BRICK_WIDTH, Coordinates.BRICK_HEIGHT, ColliderType.STATIC))).setTag("brick0");
            scene.gameObjectList.add(brick);
            physicManager.addEntity((BoxCollider)(brick.getComponent("BoxCollider")));
        }

        int[] ballXList = {100, 200, 300};
        int ballY = 350;
        for (int ballX : ballXList) {
            Ball ball = new Ball(ballX, ballY);
            scene.gameObjectList.add(ball);
            physicManager.addEntity((CircleCollider) (ball.getComponent("CircleCollider")));
            ball.throwBall(-45);
        }

        Wall wall1 = new Wall(scene.frameWidth / 2, 0);
        ((BoxCollider) wall1.addComponent(new BoxCollider(scene.frameWidth, 10, ColliderType.STATIC))).setTag("wall0");

        Wall wall2 = new Wall(0, scene.frameHeight / 2);
        ((BoxCollider) wall2.addComponent(new BoxCollider(10, scene.frameHeight, ColliderType.STATIC))).setTag("wall1");

        Wall wall3 = new Wall(scene.frameWidth / 2, scene.frameHeight);
        ((BoxCollider) wall3.addComponent(new BoxCollider(scene.frameWidth, 10, ColliderType.STATIC))).setTag("wall2");

        Wall wall4 = new Wall(scene.frameWidth, scene.frameHeight / 2);
        ((BoxCollider) wall4.addComponent(new BoxCollider(10, scene.frameHeight, ColliderType.STATIC))).setTag("wall3");

        scene.gameObjectList.add(wall1);
        physicManager.addEntity((BoxCollider) (wall1.getComponent("BoxCollider")));
        scene.gameObjectList.add(wall2);
        physicManager.addEntity((BoxCollider) (wall2.getComponent("BoxCollider")));
        scene.gameObjectList.add(wall3);
        physicManager.addEntity((BoxCollider) (wall3.getComponent("BoxCollider")));
        scene.gameObjectList.add(wall4);
        physicManager.addEntity((BoxCollider) (wall4.getComponent("BoxCollider")));
    }

    public static void main(String[] args){
        new RayTest();
    }
}

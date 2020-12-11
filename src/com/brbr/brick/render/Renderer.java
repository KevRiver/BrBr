package com.brbr.brick.render;

import com.brbr.brick.UI.UIManager;
import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.math.Vector2;
import com.brbr.brick.object.*;
import com.brbr.brick.Scene;
import com.brbr.brick.assets.Colors;

import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {
    private Scene scene;
    private UIManager uiManager;
    private String[] keys = {RectRenderComponent.class.getSimpleName(),
            CircleRenderComponent.class.getSimpleName(),
            DelegateRenderComponent.class.getSimpleName()
    };

    public Renderer(Scene scene) {
        this.scene = scene;
        uiManager = UIManager.getInstance();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawBackground(g); // 배경
        drawRayPath(g); // 공 발사 궤적

        drawGameObject(g); // 게임 오브젝트

        drawDebugText(g);

        uiManager.drawUI(g, getWidth(), getHeight()); // UI
    }

    private void drawBackground(Graphics g) {
        g.setColor(Colors.BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawRayPath(Graphics g) { // 공 발사 궤적 렌더
        if (scene.rayPath == null) return;
        if (!scene.rayPath.isActive) return;
        if (scene.rayPath.getRayPathPoints().size() == 0) return;

        Graphics2D g2 = ((Graphics2D) g);
        g2.setStroke(new BasicStroke(10));
        g2.setColor(Colors.LINE_COLOR);
        List<Vector2> rayPathPoints = scene.rayPath.getRayPathPoints();
        double sx, sy, dx, dy;
        for (int i = 0; i < rayPathPoints.size() - 1; i++) {
            sx = rayPathPoints.get(i).x;
            dx = rayPathPoints.get(i + 1).x;
            sy = rayPathPoints.get(i).y;
            dy = rayPathPoints.get(i + 1).y;
            g2.draw(new Line2D.Double(sx, sy, dx, dy));
        }
    }

    private void drawGameObject(Graphics g) { // 씬에 등록된 게임 오브젝트의 렌더 컴포넌트에 접근, 모든 렌더 컴포넌트의 draw 함수를 호출
        for (GameObject gameObject : scene.gameObjectList) {
            RenderComponent renderComponent;
            for(String key: keys){
                renderComponent = ((RenderComponent) gameObject.getComponent(key));
                if(renderComponent == null) continue;
                if(!renderComponent.isActive) break;
                renderComponent.draw(g);
            }

            if (gameObject instanceof Particle) { // 파티클 게임 오브젝트 렌더
                Particle particle = (Particle) gameObject;
                g.setColor(new Color(particle.color.getRed(),
                        particle.color.getGreen(),
                        particle.color.getBlue(),
                        (int) (particle.opacity * 255)));

                g.fillRect(
                        (int) (particle.pos.x + particle.animatedVector.x),
                        (int) (particle.pos.y + particle.animatedVector.y),
                        Coordinates.PARTICLE_SIZE, Coordinates.PARTICLE_SIZE
                );
            }
        }
    }

    private void drawDebugText(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("fps : " + scene.framePerSecond, 0, 15);
    }
}

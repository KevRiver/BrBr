package com.brbr.brick.render;

import com.brbr.brick.math.Vector2;

import java.awt.*;

// 직사각형 모양을 그리는 렌더 컴포넌트
public class RectRenderComponent extends RenderComponent {
    public int width, height;

    public RectRenderComponent(){
        super();
    }

    public RectRenderComponent(Vector2 position, int width, int height, Color color){
        this();
        this.position.x = position.x - width / 2;
        this.position.y = position.y - height / 2;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.fillRect(((int) position.x), ((int) position.y), width, height);
    }
}

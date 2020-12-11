package com.brbr.brick.render;

import com.brbr.brick.math.Vector2;

import java.awt.*;

// 동그란 모양을 그리는 렌더 컴포넌트
public class CircleRenderComponent extends RenderComponent{
    public int radius;

    public CircleRenderComponent(){
        super();
    }

    public CircleRenderComponent(Vector2 _position, int _radius){
        this();
        this.radius = _radius;
        setPosition(_position);

    }

    @Override
    public void setPosition(Vector2 _position) {
        this.position.x = _position.x - radius;
        this.position.y = _position.y - radius;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawOval(((int) position.x), ((int) position.y),  radius * 2, radius * 2);
    }
}

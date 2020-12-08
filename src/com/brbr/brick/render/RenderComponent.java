package com.brbr.brick.render;

import com.brbr.brick.core.Component;
import com.brbr.brick.math.Vector2;

import java.awt.*;

public class RenderComponent extends Component {
    public Vector2 position;
    public Color color;
    public float stroke = 1;

    protected RenderComponent(){
        super();
        position = new Vector2();
    }

    public void setPosition(Vector2 _position){
        this.position.x = _position.x;
        this.position.y = _position.y;
    }

    public void draw(Graphics g){
        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(stroke));
    }
}

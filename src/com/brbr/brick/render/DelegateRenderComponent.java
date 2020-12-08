package com.brbr.brick.render;

import java.awt.*;

public class DelegateRenderComponent extends RenderComponent{
    private DrawDelegate drawDelegate;
    public DelegateRenderComponent(DrawDelegate _drawDelegate){
        this.drawDelegate = _drawDelegate;
    }

    @Override
    public void draw(Graphics g) {
        drawDelegate.draw(g);
    }
}

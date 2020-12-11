package com.brbr.brick.render;

import java.awt.*;

// 복합적인 모양을 그리는 렌더 컴포넌트
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

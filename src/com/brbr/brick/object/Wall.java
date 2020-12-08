package com.brbr.brick.object;

import com.brbr.brick.physics.BoxCollider;
import com.brbr.brick.physics.ColliderType;
import com.brbr.brick.render.RectRenderComponent;

import java.awt.*;

public class Wall extends GameObject {
    public int width, height;
    private BoxCollider boxCollider;
    private RectRenderComponent rectRenderComponent;
    public Wall(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        boxCollider = ((BoxCollider) this.addComponent(new BoxCollider(width, height, ColliderType.STATIC)));
        boxCollider.setCenter(this.transform.position);
        rectRenderComponent = ((RectRenderComponent) this.addComponent(new RectRenderComponent(this.transform.position, width, height, Color.BLACK)));
    }

    public void setTag(String tag){
        boxCollider.setTag(tag);
    }
}

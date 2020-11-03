package com.brbr.brick.object;

import com.brbr.brick.assets.Coordinates;

public class Brick extends GameObject {

    public int health;

    public int getAbsoluteX() {
        int x = (int) transform.position.x;
        return x * Coordinates.BRICK_WIDTH + (x + 1) * Coordinates.BRICK_MARGIN;
    }

    public int getAbsoluteY() {
        int y = (int) transform.position.y;
        return y * Coordinates.BRICK_HEIGHT + (y + 1) * Coordinates.BRICK_MARGIN;
    }
}

package com.brbr.brick.math;

public class Bounds {
    private Vector2 center;
    private Vector2 min;
    private Vector2 max;
    private int width;
    private int height;

    // constructor
    public Bounds() {
        center = new Vector2();
        min = new Vector2();
        max = new Vector2();
    }

    public Bounds(int width, int height) {
        this();
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public Vector2 getMin() {
        return min;
    }

    public Vector2 getMax() {
        return max;
    }

    public double getMinX() {
        return min.x;
    }

    public double getMinY() {
        return min.y;
    }

    public double getMaxX() {
        return max.x;
    }

    public double getMaxY() {
        return max.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2 getCenter() {
        return center;
    }

    public void setBounds(int width, int height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public void setCenter(Vector2 position) {
        this.center.x = position.x;
        this.center.y = position.y;
        updateBounds();
    } // center 값을 변경 후 min, max 갱신

    public String toString() {
        return "" + "min(" + min.x + ", " + min.y + ")" + " max(" + max.x + ", " + max.y + ")";
    }

    private void updateBounds() {
        min.x = center.x - (double) width / 2;
        min.y = center.y - (double) height / 2;
        max.x = center.x + (double) width / 2;
        max.y = center.y + (double) height / 2;
    } // min, max 갱신
}

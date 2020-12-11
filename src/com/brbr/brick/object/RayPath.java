package com.brbr.brick.object;

import com.brbr.brick.math.Vector2;

import java.util.ArrayList;
import java.util.List;

// 공 발사 궤적 모델
public class RayPath extends GameObject {
    public boolean isActive;
    private Vector2 raySource;
    private List<Vector2> rayPathPoints;
    private double length;

    public RayPath(int x, int y, double length) {
        super(x, y);
        this.length = length;
        init();
    }

    private void init() {
        isActive = false;
        raySource = transform.position;
        rayPathPoints = new ArrayList<>();
    }

    public Vector2 getRaySource() {
        return raySource;
    }

    public List<Vector2> getRayPathPoints() {
        return rayPathPoints;
    }

    public void setRayPathPoints(List<Vector2> pathPoints) {
        if (pathPoints == null) return;
        rayPathPoints = pathPoints;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double l) {
        length = l;
    }
}

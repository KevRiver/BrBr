package com.brbr.brick.math;

import java.awt.geom.Point2D;

// 2차원 실수 좌표 계산을 위한 클래스
public class Vector2 extends Point2D.Double {
    public final static Vector2 up = new Vector2(0, -1);
    public final static Vector2 down = new Vector2(0, 1);
    public final static Vector2 right = new Vector2(1, 0);
    public final static Vector2 left = new Vector2(-1, 0);
    public final static Vector2 leftUp = new Vector2(-1, -1);
    public final static Vector2 leftDown = new Vector2(-1, 1);
    public final static Vector2 rightUp = new Vector2(1, -1);
    public final static Vector2 rightDown = new Vector2(1, 1);

    public Vector2() {
        super();
    }

    public Vector2(double x, double y) {
        super(x, y);
    }

    public static double getDistance(Vector2 lhs, Vector2 rhs) {
        return Math.sqrt(
                Math.pow(lhs.x - rhs.x, 2) + Math.pow(lhs.y - rhs.y, 2)
        );
    }

    public static Vector2 getNormalized(Vector2 dir) {
        double x = dir.x;
        double y = dir.y;
        final double m = Math.pow(x, 2) + Math.pow(y, 2);
        return new Vector2(x / Math.sqrt(m), y / Math.sqrt(m));
    } // 파라미터로 넘긴 벡터에 대한 단위벡터를 리턴

    public static void normalize(Vector2 dir) {
        double x = dir.x;
        double y = dir.y;
        final double m = Math.pow(x, 2) + Math.pow(y, 2);
        dir.x = (dir.x / Math.sqrt(m));
        dir.y = (dir.y / Math.sqrt(m));
    } // 파라미터로 넘긴 벡터를 단위벡터로 변경

    public static Vector2 add(Vector2 lhs, Vector2 rhs) {
        return new Vector2(lhs.x + rhs.x, lhs.y + rhs.y);
    }

    public static Vector2 subtract(Vector2 lhs, Vector2 rhs) {
        return new Vector2(lhs.x - rhs.x, lhs.y - rhs.y);
    }

    public Vector2 multiply(double multiplier) {
        return new Vector2(this.x * multiplier, this.y * multiplier);
    }

    public boolean equals(Vector2 v) {
        return x == v.x && y == v.y;
    }

    public String toString() {
        return "x: " + x + " y: " + y;
    }
}

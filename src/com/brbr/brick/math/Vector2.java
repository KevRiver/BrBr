package com.brbr.brick.math;

import java.awt.geom.Point2D;

public class Vector2 extends Point2D.Double {
    public final static Vector2 up = new Vector2(0,-1);
    public final static Vector2 down = new Vector2(0,1);
    public final static Vector2 right = new Vector2(1,0);
    public final static Vector2 left = new Vector2(-1,0);
    public final static Vector2 leftUp = new Vector2(-1,-1);
    public final static Vector2 leftDown = new Vector2(-1,1);
    public final static Vector2 rightUp = new Vector2(1,-1);
    public final static Vector2 rightDown = new Vector2(1,1);

    // constructor
    public Vector2(){
        super();
    }
    public Vector2(double x, double y) {
        super(x,y);
    }
    // methods

    // distance between two vectors
    public static double getDistance(Vector2 lhs, Vector2 rhs){
        double distance = Math.sqrt(
                Math.pow(lhs.x - rhs.x, 2) + Math.pow(lhs.y - rhs.y, 2)
        );
        return distance;
    }

    public static Vector2 getNormalized(Vector2 dir){
        double x = dir.x;
        double y = dir.y;
        final double m = Math.pow(x, 2) + Math.pow(y, 2);
        return new Vector2(x / Math.sqrt(m), y/Math.sqrt(m));
    }

    public static void normalize(Vector2 dir){
        double x = dir.x;
        double y = dir.y;
        final double m = Math.pow(x, 2) + Math.pow(y, 2);
        dir.x = (dir.x / Math.sqrt(m));
        dir.y = (dir.y / Math.sqrt(m));
    }

    // add two vector
    public static Vector2 add(Vector2 lhs, Vector2 rhs){
        Vector2 ret = new Vector2(lhs.x + rhs.x, lhs.y+ rhs.y );
        return ret;
    }
    // subtract two vector
    public static Vector2 subtract(Vector2 lhs, Vector2 rhs){
        Vector2 ret = new Vector2(lhs.x - rhs.x, lhs.y - rhs.y );
        return ret;
    }
    // multiply
    public Vector2 multiply(double multiplier){
        Vector2 ret = new Vector2(this.x * multiplier, this.y * multiplier);
        return ret;
    }
    // check equality
    public boolean equals(Vector2 v){
        if(x == v.x && y == v.y) return true;
        return false;
    }

    public String toString(){
        String toStr = "x: " + x + " y: " + y;
        return toStr;
    }
}

package com.brbr.brick.math;

public class MathExtension {
    public static double deg2rad(double degree) {
        return degree * Math.PI / 180;
    }

    public static double rad2deg(double radian) {
        return radian * 180 / Math.PI;
    }

}
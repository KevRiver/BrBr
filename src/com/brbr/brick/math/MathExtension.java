package com.brbr.brick.math;

public class MathExtension {
    public static double deg2rad(double degree) {
        return degree * Math.PI / 180;
    } // 각도 값을 호도 값으로 변경

    public static double rad2deg(double radian) {
        return radian * 180 / Math.PI;
    } // 호도 값을 각도 값으로 변경

}
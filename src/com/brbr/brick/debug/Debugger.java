package com.brbr.brick.debug;

import java.awt.*;
import java.awt.geom.Point2D;

public class Debugger {
    public final static boolean DEBUG = true;

    public static void Print(String str){
        if(!DEBUG) return;
        System.out.println(str);
    }
    public static void Print(String tag, Point point){
        if(!DEBUG) return;
        System.out.println(tag + " " + point.toString());
    }
    public static void Print(String tag, Point2D.Double point2D){
        if(!DEBUG) return;
        System.out.println(tag + " " + point2D.toString());
    }
    public static void Print(String tag,Point2D.Float point2D){
        if(!DEBUG) return;
        System.out.println(tag + " " + point2D.toString());
    }
}

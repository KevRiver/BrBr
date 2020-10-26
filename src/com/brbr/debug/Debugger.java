package com.brbr.debug;

import java.awt.geom.Point2D;

public class Debugger {
    public final static boolean DEBUG = true;
    public static void Print(Point2D point2D, String tag){
        if(!DEBUG) return;
        System.out.println(tag + " " + point2D.toString());
    }
}

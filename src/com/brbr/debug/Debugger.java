package com.brbr.debug;

import java.awt.*;

public class Debugger {
    public final static boolean DEBUG = true;
    public static void Print(String tag, Point point){
        if(!DEBUG) return;
        System.out.println(tag + " " + point.toString());
    }
}

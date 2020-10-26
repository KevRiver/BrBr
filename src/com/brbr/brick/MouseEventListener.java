package com.brbr.brick;
import com.brbr.debug.Debugger;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

public class MouseEventListener {
    private boolean active = false;
    private Point2D point2D;
    private double _x, _y;

    private double _sX, _sY;

    public void setActive(boolean activation){
        active = activation;
    }


}

package com.brbr.physics;
import com.brbr.debug.Debugger;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Collider {
    boolean isKinematic;
    public Rectangle2D rectangle2D;


    public Collider(Rectangle2D r){
        isKinematic = true;
        rectangle2D = r;
    }

    public Collider(Rectangle2D r, boolean isKinematic){
        this.isKinematic = isKinematic;
        rectangle2D = r;
    }

    public void collisionEnter(Collider collider){
        Debugger.Print("Collision Enter: " + collider.toString());
        RectangularShape s;
        if((s = collider.rectangle2D) != null) {
            Debugger.Print("Center Pos: ", new Point2D.Double(s.getCenterX(),s.getCenterY()));
        }
    }
}

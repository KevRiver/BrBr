package com.brbr.physics;
import com.brbr.debug.Debugger;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Collider {
    String tag;
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

    public void setTag(String tag){
        this.tag = tag;
    }

    public void collisionEnter(Collider collider){
        Rectangle2D r;
        if((r = collider.rectangle2D) != null) {
            Debugger.Print(this.tag + " Colliding with " + collider.toString());
        }
    }

    public String toString(){
        String toStr = tag + " center: "+ new Point2D.Double(rectangle2D.getCenterX(),rectangle2D.getCenterY());
        return toStr;
    }
}

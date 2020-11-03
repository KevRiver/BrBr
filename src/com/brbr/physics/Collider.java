package com.brbr.physics;
import com.brbr.debug.Debugger;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Collider {
    String tag;
    ColliderType type;
    public Rectangle2D rectangle2D;

    public Collider(){
        type = ColliderType.KINEMATIC;
    }

    public Collider(Rectangle2D r){
        this();
        rectangle2D = r;
    }

    public Collider(Rectangle2D r, ColliderType t){
        rectangle2D = r;
        type = t;
    }

    public void setTag(String tag){
        this.tag = tag;
    }

    public void onCollisionEnter(Collider collider){
        if((collider.rectangle2D) != null) {
            //Debugger.Print(this.tag + " Colliding with " + collider.toString());
        }
    }

    public void onTriggerEnter(Collider collider){
        if((collider.rectangle2D) != null) {
            //Debugger.Print(this.tag + " Trigger with " + collider.toString());
        }
    }

    public String toString(){
        String toStr = tag + " center: "+ new Point2D.Double(rectangle2D.getCenterX(),rectangle2D.getCenterY());
        return toStr;
    }
}

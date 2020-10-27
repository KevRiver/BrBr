package com.brbr.physics;

import com.brbr.debug.Debugger;

import java.awt.geom.Rectangle2D;
import java.util.*;

public class PhysicManager {
    private static PhysicManager instance = null;

    private ArrayList<Collider> kinematicObjects;
    private ArrayList<Collider> staticObjects;

    private PhysicManager(){
        kinematicObjects = new ArrayList<>();
        staticObjects = new ArrayList<>();
    }

    public static PhysicManager getInstance(){
        if(instance == null) return new PhysicManager();
        return instance;
    }

    public void addEntity(Collider collider){
        if(collider.isKinematic){
            kinematicObjects.add(collider);
        }else{
            staticObjects.add(collider);
        }
    }

    public void collisionCheck(){
        for(var k: kinematicObjects){
            for(var s: staticObjects){
                if(checkAABB(k,s)){
                    Debugger.Print("collision detected");
                    k.collisionEnter(s);
                    s.collisionEnter(k);
                }
            }
        }
    }

    private boolean checkAABB(Collider obj0, Collider obj1){
        Debugger.Print("checkAABB called");
        Rectangle2D r = obj1.rectangle2D;
        return obj0.rectangle2D.intersects(r.getX(),r.getY(),r.getWidth(),r.getHeight());
    }

}

package com.brbr.physics;

import com.brbr.debug.Debugger;

import java.awt.geom.Rectangle2D;
import java.util.*;

public class PhysicManager {
    private static PhysicManager instance = null;

    private List<Collider> kinematicObjects;
    private List<Collider> staticObjects;
    private List<Collider> triggerObejcts;

    private PhysicManager(){
        kinematicObjects = new ArrayList<>();
        staticObjects = new ArrayList<>();
        triggerObejcts = new ArrayList<>();
    }

    public static PhysicManager getInstance(){
        if(instance == null) return new PhysicManager();
        return instance;
    }

    /// 충돌체의 타입에 따라 PhysicManager에 등록
    public void addEntity(Collider collider){
        switch (collider.type){
            case KINEMATIC:
                kinematicObjects.add(collider);
                break;
            case STATIC:
                staticObjects.add(collider);
                break;
            case TRIGGER:
                triggerObejcts.add(collider);
                break;
        }
    }

    /// kinematicObejects에 등록된 충돌체들과 static/trigger Objects에 등록된 충돌체들의 충돌 검사
    public void collisionCheck(){
        for(var k: kinematicObjects){
            for(var s: staticObjects){
                if(checkAABB(k,s)){
                    k.onCollisionEnter(s);
                    s.onCollisionEnter(k);
                }
            }

            for(var t: triggerObejcts){
                if(checkAABB(k,t)){
                    k.onTriggerEnter(t);
                }
            }
        }
    }

    private boolean checkAABB(Collider obj0, Collider obj1){
        //Debugger.Print("checkAABB called");
        Rectangle2D r = obj1.rectangle2D;
        return obj0.rectangle2D.intersects(r.getX(),r.getY(),r.getWidth(),r.getHeight());
    }

}

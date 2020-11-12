package com.brbr.brick.physics;

import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Bounds;

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
        for(Collider k: kinematicObjects){
            for(Collider s: staticObjects){
                if(checkOverlap(k,s)){
                    Debugger.Print("Collision Detected " + k.tag + ", " + s.tag);
                    k.gameObject.onCollisionEnter(s);
                    s.gameObject.onCollisionEnter(k);
                }
            }

            for(Collider t: triggerObejcts){
                if(checkOverlap(k,t)){
                    k.gameObject.onTriggerEnter(t);
                }
            }
        }
    }

    private boolean checkAABB(Collider k, Collider other){
        boolean isIntersect = false;
        // make AABB Logic
        return isIntersect;
    }

    private boolean checkOverlap(Collider k, Collider other){
        Bounds kBounds, oBounds;

        kBounds = ((BoxCollider)k).bounds;
        oBounds = ((BoxCollider)other).bounds;

        if(kBounds.getMaxX() < oBounds.getMinX() || kBounds.getMinX() > oBounds.getMaxX()) return false;
        if(kBounds.getMaxY() < oBounds.getMinY() || kBounds.getMinY() > oBounds.getMaxY()) return false;
        return true;
    }

}

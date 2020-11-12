package com.brbr.physics;

import com.brbr.debug.Debugger;
import com.brbr.math.Bounds;
import com.brbr.math.Transform;
import com.brbr.math.Vector2;

import java.awt.geom.Rectangle2D;
import java.util.*;

import static com.brbr.math.Vector2.getDistance;

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
                if(checkOverlap(k,s)){
                    Debugger.Print("Collision Detected " + k.tag + ", " + s.tag);
                    k.gameObject.onCollisionEnter(s);
                    s.gameObject.onCollisionEnter(k);
                }
            }

            for(var t: triggerObejcts){
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
        if(k.getClass().getSimpleName().equals("BoxCollider") &&
                other.getClass().getSimpleName().equals("BoxCollider")){
            // 사각형 끼리 충돌
            Bounds kBounds, oBounds;

            kBounds = ((BoxCollider)k).bounds;
            oBounds = ((BoxCollider)other).bounds;
            return rrCol(kBounds, oBounds);
        }

        if(k.getClass().getSimpleName().equals("BoxCollider") &&
                other.getClass().getSimpleName().equals("CircleCollider")){
            // 원과 사각형 충돌
            CircleCollider oCircle = (CircleCollider) other;
            Bounds kBounds = ((BoxCollider)k).bounds;

            return crCol(oCircle.center, oCircle.radius, kBounds);
        }

        if(k.getClass().getSimpleName().equals("CircleCollider") &&
                other.getClass().getSimpleName().equals("BoxCollider")) {
            // 원과 사각형 충돌
            CircleCollider kCircle = (CircleCollider) k;
            Bounds oBounds = ((BoxCollider)other).bounds;

            return crCol(kCircle.center, kCircle.radius, oBounds);
        }

        if(k.getClass().getSimpleName().equals("CircleCollider") &&
                other.getClass().getSimpleName().equals("CircleCollider")) {
            // 원끼리 충돌
            CircleCollider kCircle = (CircleCollider) k;
            CircleCollider oCircle = (CircleCollider) other;
            return ccCol(kCircle.center, kCircle.radius, oCircle.center, oCircle.radius);
        }

        return false;
    }

    private boolean ccCol(Vector2 c1, double r1, Vector2 c2, double r2){ //원 끼리 충돌
        double distance = getDistance(c1,c2);
        if(distance > r1 + r2)
            return false;

        return true;
    }

    private boolean rrCol(Bounds bound1, Bounds bound2){ //사각형 끼리 충돌
        if(bound1.getMaxX() < bound2.getMinX() || bound1.getMinX() > bound2.getMaxX()) return false;
        if(bound1.getMaxY() < bound2.getMinY() || bound1.getMinY() > bound2.getMaxY()) return false;
        return true;
    }

    private boolean crCol(Vector2 c, double r, Bounds bound){ //사각형과 원 충돌
        if(c.x < bound.getMinX() - r || bound.getMaxX() + r < c.x ||
                c.y < bound.getMinY() - r || bound.getMaxY() + r < c.y){
            return false;
        }

        if(c.x < bound.getMinX() && c.y < bound.getMinY()){ // 사각형의 왼쪽 상단에 위치
            double distance = getDistance(c, new Vector2(bound.getMinX(), bound.getMinY()));
            if(distance > r) return false;
            return true;
        }

        if(c.x > bound.getMaxX() && c.y < bound.getMinY()){ // 사각형의 오른쪽 상단에 위치
            double distance = getDistance(c, new Vector2(bound.getMaxX(), bound.getMinY()));
            if(distance > r) return false;
            return true;
        }

        if(c.x < bound.getMinX() && c.y > bound.getMaxY()){ // 사각형의 왼쪽 하단에 위
            double distance = getDistance(c, new Vector2(bound.getMinX(), bound.getMaxY()));
            if(distance > r) return false;
            return true;
        }

        if(c.x > bound.getMaxX() && c.y > bound.getMaxY()){ // 사각형의 오른쪽 하단에 위치
            double distance = getDistance(c, new Vector2(bound.getMaxX(), bound.getMaxY()));
            if(distance > r) return false;
            return true;
        }

        return true;
    }
}

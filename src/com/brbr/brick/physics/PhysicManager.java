package com.brbr.brick.physics;

import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Bounds;
import com.brbr.brick.math.Vector2;

import java.util.*;

import static com.brbr.brick.math.Vector2.getDistance;

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
                Debugger.Print("static object added, current entities: " + staticObjects.size());
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

    public List<Vector2> getRayPath(Vector2 source, Vector2 direction, double length){
        List<Vector2> points = new ArrayList<>();
        points.add(new Vector2(source.x, source.y));
        Vector2 src = new Vector2(source.x, source.y);
        Vector2 dir = Vector2.getNormalized(direction);
        RaycastHit hit = null;
        double travelRemain = length;
        while(!(travelRemain <= 0)){
            if((hit = raycast(src, dir, travelRemain)) != null){
                Debugger.Print("Ray hit");
                points.add(new Vector2(hit.point.x, hit.point.y));
                src.x = hit.point.x;
                src.y = hit.point.y;
                dir.x *= hit.reflect.x;
                dir.y *= hit.reflect.y;
                travelRemain -= hit.distance;
            }else{
                Vector2 normalized = Vector2.getNormalized(direction);
                double x = source.x + normalized.x * travelRemain;
                double y = source.y + normalized.y * travelRemain;
                points.add(new Vector2(x,y));
                travelRemain = 0;
            }
        }
        return points;
    }

    public RaycastHit raycast(final Vector2 source, final Vector2 dir, final double length){
        RaycastHit firstHit = null;
        RaycastHit hit;
        // check all static colliders
        for(var s: staticObjects) {
            Bounds b = ((BoxCollider) s).bounds;
            if ((hit = isHit(source, dir, length, b)) != null) {
                if (firstHit == null) {
                    firstHit = hit;
                } else {
                    double d0 = getDistance(source, firstHit.point);
                    double d1 = getDistance(source, hit.point);
                    firstHit = d0 < d1 ? firstHit : hit;
                }
            }
        }
        if(firstHit != null){
            Debugger.Print("Hit reflect: " + firstHit.reflect.x + ", " + firstHit.reflect.y);
        }
        return firstHit;
    }

    private RaycastHit isHit(final Vector2 source,final Vector2 dir,final double length,final Bounds bounds){
        RaycastHit hit;
        Vector2 contact = getPointOfContact(source, dir, bounds);
        if(contact == null) return null;

        double distance = Vector2.getDistance(source, contact);
        if(distance <= length){ // ray hits the bounds
            Debugger.Print("contact: " + contact.x +", "+ contact.y);
            hit = new RaycastHit();
            hit.point = contact;
            hit.distance = distance;
            if(contact.x == bounds.getMinX() || contact.x == bounds.getMaxX()){
                hit.reflect.x = -1;
                hit.reflect.y = 1;
            } // flip by x axis
            else{
                hit.reflect.x = 1;
                hit.reflect.y = -1;
            } // flip by y axis
            return hit;
        }
        return null;
    }

    private Vector2 getDeterminant(final Vector2 dir, final Bounds bounds){
        Vector2 determinant = new Vector2();
        Debugger.Print("getDeterminant::dir : " + dir.x + "," + dir.y);
        if(dir.x > 0 && dir.y > 0){
            determinant.x = bounds.getMinX();
            determinant.y = bounds.getMinY();
            Debugger.Print("Determinant min, min");
        }else if(dir.x < 0 && dir.y > 0){
            determinant.x = bounds.getMaxX();
            determinant.y = bounds.getMinY();
            Debugger.Print("Determinant max, min");
        }else if(dir.x < 0 && dir.y < 0){
            determinant.x = bounds.getMaxX();
            determinant.y = bounds.getMaxY();
            Debugger.Print("Determinant max, max");
        }else if(dir.x > 0 && dir.y < 0){
            determinant.x = bounds.getMinX();
            determinant.y = bounds.getMaxY();
            Debugger.Print("Determinant min, max");
        }else if(dir.x == 0){
            determinant.y =  dir.y > 0 ? bounds.getMinY() : bounds.getMaxY();
            determinant.x = -10;
        }else{
            determinant.x =  dir.x > 0 ? bounds.getMinX(): bounds.getMaxX();
            determinant.y = -10;
        }
        return determinant;
    }

    private Vector2 getPointOfContact(final Vector2 source, final Vector2 dir, final Bounds bounds){
        Vector2 determinant;
        Vector2 contact = new Vector2();
        double a, x, y, dx, dy;
        try{
            determinant = getDeterminant(dir, bounds);
            if(determinant.x != -10){
                a = dir.y / dir.x;
                dx = determinant.x - source.x;
                x = determinant.x;
                dy = a * dx;
                y = source.y + dy;
                if(y > bounds.getMinY() && y <= bounds.getMaxY()) {
                    contact.x = x;
                    contact.y = y;
                    Debugger.Print("contact: " + x + ", " + y);
                    return contact; // contact detected from vertical axis
                }
            }
            if(determinant.y != -10){
                a = dir.x / dir.y;
                dy = determinant.y - source.y;
                y = determinant.y;
                dx = a * dy;
                x = source.x + dx;
                if(x > bounds.getMinX() && x <= bounds.getMaxX()) {
                    contact.x = x;
                    contact.y = y;
                    Debugger.Print("contact: " + x + ", " + y);
                    return contact; // contact detected from horizontal axis
                }
            }
            if(determinant.x != -1){
                contact.x = source.x;
                contact.y = determinant.y;
            }else{
                contact.x = determinant.x;
                contact.y  = source.y;
            }
            return contact;
        } catch (Exception e){
            Debugger.Print(e.toString());
        }
        return null;
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

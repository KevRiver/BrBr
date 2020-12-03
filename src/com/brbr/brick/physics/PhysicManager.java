package com.brbr.brick.physics;

import com.brbr.brick.Scene;
import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Bounds;
import com.brbr.brick.math.Vector2;

import java.util.*;
import java.util.stream.Collectors;

import static com.brbr.brick.math.Vector2.getDistance;

public class PhysicManager {

    private final Scene scene;

    private final double MIN_THRESHOLD = 0.01;

    public PhysicManager(Scene scene) {
        this.scene = scene;
    }

    private List<Collider> getCollisionObjects(ColliderType type) {
        return scene.gameObjectList.stream()
                .flatMap(o -> o.getComponents().values().stream())
                .filter(component -> component instanceof Collider)
                .map(collider -> (Collider) collider)
                .filter(collider -> collider.type == type)
                .collect(Collectors.toList());
    }

    private List<Collider> getKinematicObjects() {
        return getCollisionObjects(ColliderType.KINEMATIC);
    }

    private List<Collider> getStaticObjects() {
        return getCollisionObjects(ColliderType.STATIC);
    }

    private List<Collider> getTriggerObjects() {
        return getCollisionObjects(ColliderType.TRIGGER);
    }

    /// kinematicObejects에 등록된 충돌체들과 static/trigger Objects에 등록된 충돌체들의 충돌 검사
    public void collisionCheck() {
        for (Collider k : getKinematicObjects()) {
            for (Collider s : getStaticObjects()) {
                if (checkOverlap(k, s)) {
                    //Debugger.Print("Collision Detected " + k.tag + ", " + s.tag);
                    k.gameObject.onCollisionEnter(s);
                    s.gameObject.onCollisionEnter(k);
                }
            }

            for (Collider t : getTriggerObjects()) {
                if (checkOverlap(k, t)) {
                    k.gameObject.onTriggerEnter(t);
                }
            }
        }
    }

    public List<Vector2> getRayPath(Vector2 source, Vector2 direction, double length){
        List<Vector2> points = new ArrayList<>();
        points.add(new Vector2(source.x, source.y));
        Vector2 src = new Vector2(source.x, source.y);
        RaycastHit hit;
        double travelRemain = length;
        while(!(travelRemain <= 10)){
            if((hit = raycast(src, direction, travelRemain)) != null){
                points.add(new Vector2(hit.point.x, hit.point.y));
                src.x = hit.point.x;
                src.y = hit.point.y;
                direction.x = hit.reflect.x > 0 ? direction.x : -direction.x;
                direction.y = hit.reflect.y > 0 ? direction.y : -direction.y;
                // direction not correct
                travelRemain -= hit.distance;
            }else{
                double x = src.x + direction.x * travelRemain;
                double y = src.y + direction.y * travelRemain;
                points.add(new Vector2(x,y));
                travelRemain = 0;
            }
        }
        return points;
    }

    // simulate a ray, return hit data
    public RaycastHit raycast(final Vector2 source, final Vector2 dir, final double length){
        RaycastHit firstHit = null;
        RaycastHit hit;
        // check all static colliders
        for(var s: getStaticObjects()) {
            Bounds b = ((BoxCollider) s).bounds;
            if ((hit = isHit(source, dir, length, b)) != null) {
                if (firstHit == null) {
                    firstHit = hit;
                } else {
                    double d0 = getDistance(source, firstHit.point);
                    double d1 = getDistance(source, hit.point);
                    firstHit = d0 <= d1 ? firstHit : hit;
                }
            }
        }
        return firstHit;
    }

    private RaycastHit isHit(final Vector2 source,final Vector2 dir,final double length,final Bounds bounds){
        RaycastHit contact = getPointOfContact(source, dir, bounds);
        if(contact == null) return null;
        if(contact.distance > length) return null;
        return contact;
    }

    private RaycastHit getPointOfContact(final Vector2 source, final Vector2 dir, final Bounds bounds){
        RaycastHit contact = new RaycastHit();
        Vector2 determinant = getDeterminant(source, dir, bounds);
        double a, x, y, dx, dy;

        if(determinant == null) return null;
        // dir parallel to y axis
        if(Math.abs(dir.x) <= MIN_THRESHOLD){
            contact.point.x = source.x;
            contact.point.y = determinant.y;
            contact.distance = getDistance(source, contact.point);
            contact.reflect.x = 1;
            contact.reflect.y = -1;
            return contact;
        }
        // dir parallel to x axis
        if(Math.abs(dir.y) <= MIN_THRESHOLD){
            contact.point.x = determinant.x;
            contact.point.y  = source.y;
            contact.distance = getDistance(source, contact.point);
            contact.reflect.x = -1;
            contact.reflect.y = 1;
            return contact;
        }
        // check if ray hit from sides
        try {
            a = dir.y / dir.x;
            dx = determinant.x - source.x;
            x = determinant.x;
            dy = a * dx;
            y = source.y + dy;
            if (y > bounds.getMinY() && y <= bounds.getMaxY()) {
                contact.point.x = x;
                contact.point.y = y;
                contact.distance = getDistance(source, contact.point);
                contact.reflect.x = -1;
                contact.reflect.y = 1;
                return contact;
            }
        }catch (Exception e){
            Debugger.Print(e.toString());
        }

        try {
            a = dir.x / dir.y;
            dy = determinant.y - source.y;
            y = determinant.y;
            dx = a * dy;
            x = source.x + dx;
            if (x > bounds.getMinX() && x <= bounds.getMaxX()) {
                contact.point.x = x;
                contact.point.y = y;
                contact.distance = getDistance(source, contact.point);
                contact.reflect.x = 1;
                contact.reflect.y = -1;
                return contact;
            }
        }catch (Exception e){
            Debugger.Print(e.toString());
        }
        return null;
    }

    private Vector2 getDeterminant(final Vector2 source, final Vector2 dir, final Bounds bounds){
        Vector2 determinant = new Vector2();

        if(Math.abs(dir.x) <= MIN_THRESHOLD){ // orthogonal to x axis
            if(dir.y > 0){
                determinant.y = bounds.getMinY();
                determinant.x = bounds.getMinX();
                if(determinant.y < source.y) determinant = null;
            }else{
                determinant.y = bounds.getMaxY();
                determinant.x = bounds.getMaxX();
                if(determinant.y > source.y) determinant = null;
            }
            return determinant;
        }
        if(Math.abs(dir.y) <= MIN_THRESHOLD){ // orthogonal to y axis
            if(dir.x > 0){
                determinant.x = bounds.getMinX();
                determinant.y = bounds.getMinY();
                if(determinant.x < source.x) determinant = null;
            }else{
                determinant.x = bounds.getMaxX();
                determinant.y = bounds.getMaxY();
                if(determinant.x > source.x) determinant = null;
            }
            return determinant;
        }

        if(dir.x > 0 && dir.y > 0){
            determinant.x = bounds.getMinX();
            determinant.y = bounds.getMinY();
            if(source.x > determinant.x && source.y > determinant.y)
                determinant = null;
        }else if(dir.x < 0 && dir.y > 0){
            determinant.x = bounds.getMaxX();
            determinant.y = bounds.getMinY();
            if(source.x < determinant.x && source.y > determinant.y)
                determinant = null;
        }else if(dir.x < 0 && dir.y < 0){
            determinant.x = bounds.getMaxX();
            determinant.y = bounds.getMaxY();
            if(source.x < determinant.x && source.y < determinant.y)
                determinant = null;
        }else if(dir.x > 0 && dir.y < 0){
            determinant.x = bounds.getMinX();
            determinant.y = bounds.getMaxY();
            if(source.x > determinant.x && source.y < determinant.y)
                determinant = null;
        }
        return determinant;
    }

    private boolean checkOverlap(Collider k, Collider other) {
        if (k.getClass().getSimpleName().equals("BoxCollider") &&
                other.getClass().getSimpleName().equals("BoxCollider")) {
            // 사각형 끼리 충돌
            Bounds kBounds, oBounds;

            kBounds = ((BoxCollider) k).bounds;
            oBounds = ((BoxCollider) other).bounds;
            return rrCol(kBounds, oBounds);
        }

        if (k.getClass().getSimpleName().equals("BoxCollider") &&
                other.getClass().getSimpleName().equals("CircleCollider")) {
            // 원과 사각형 충돌
            CircleCollider oCircle = (CircleCollider) other;
            Bounds kBounds = ((BoxCollider) k).bounds;

            return crCol(oCircle.center, oCircle.radius, kBounds);
        }

        if (k.getClass().getSimpleName().equals("CircleCollider") &&
                other.getClass().getSimpleName().equals("BoxCollider")) {
            // 원과 사각형 충돌
            CircleCollider kCircle = (CircleCollider) k;
            Bounds oBounds = ((BoxCollider) other).bounds;

            return crCol(kCircle.center, kCircle.radius, oBounds);
        }

        if (k.getClass().getSimpleName().equals("CircleCollider") &&
                other.getClass().getSimpleName().equals("CircleCollider")) {
            // 원끼리 충돌
            CircleCollider kCircle = (CircleCollider) k;
            CircleCollider oCircle = (CircleCollider) other;
            return ccCol(kCircle.center, kCircle.radius, oCircle.center, oCircle.radius);
        }

        return false;
    }

    private boolean ccCol(Vector2 c1, double r1, Vector2 c2, double r2) { //원 끼리 충돌
        double distance = getDistance(c1, c2);
        return !(distance > r1 + r2);
    }

    private boolean rrCol(Bounds bound1, Bounds bound2) { //사각형 끼리 충돌
        if (bound1.getMaxX() < bound2.getMinX() || bound1.getMinX() > bound2.getMaxX()) return false;
        return !(bound1.getMaxY() < bound2.getMinY()) && !(bound1.getMinY() > bound2.getMaxY());
    }

    private boolean crCol(Vector2 c, double r, Bounds bound) { //사각형과 원 충돌
        if (c.x < bound.getMinX() - r || bound.getMaxX() + r < c.x ||
                c.y < bound.getMinY() - r || bound.getMaxY() + r < c.y) {
            return false;
        }

        if (c.x < bound.getMinX() && c.y < bound.getMinY()) { // 사각형의 왼쪽 상단에 위치
            double distance = getDistance(c, new Vector2(bound.getMinX(), bound.getMinY()));
            return !(distance > r);
        }

        if (c.x > bound.getMaxX() && c.y < bound.getMinY()) { // 사각형의 오른쪽 상단에 위치
            double distance = getDistance(c, new Vector2(bound.getMaxX(), bound.getMinY()));
            return !(distance > r);
        }

        if (c.x < bound.getMinX() && c.y > bound.getMaxY()) { // 사각형의 왼쪽 하단에 위
            double distance = getDistance(c, new Vector2(bound.getMinX(), bound.getMaxY()));
            return !(distance > r);
        }

        if (c.x > bound.getMaxX() && c.y > bound.getMaxY()) { // 사각형의 오른쪽 하단에 위치
            double distance = getDistance(c, new Vector2(bound.getMaxX(), bound.getMaxY()));
            return !(distance > r);
        }

        return true;
    }
}

package com.brbr.brick.object;

import com.brbr.brick.InputData;
import com.brbr.brick.math.Vector2;
import com.brbr.brick.physics.PhysicManager;

import java.util.ArrayList;
import java.util.List;

public class RayPath extends GameObject{
    public boolean isActive;
    private Vector2 raySource;
    private List<Vector2> rayPathPoints;
    private double length;

    public RayPath(){
        super();
        init();
    }

    public RayPath(int x, int y){
        super(x,y);
        init();
    }

    public RayPath(int x, int y, double length){
        super(x,y);
        this.length = length;
        init();
    }

    private void init(){
        isActive = false;
        raySource = transform.position;
        rayPathPoints = new ArrayList<>();
    }

    public Vector2 getRaySource(){return raySource;}

    public List<Vector2> getRayPathPoints(){
        return rayPathPoints;
    }

    public void setRayPathPoints(List<Vector2> pathPoints) {
        if(pathPoints == null) return;
        rayPathPoints = pathPoints;
    }

    public double getLength(){
        return length;
    }

    public void setLength(double l){
        length = l;
    }
}

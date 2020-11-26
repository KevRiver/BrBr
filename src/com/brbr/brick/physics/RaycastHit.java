package com.brbr.brick.physics;

import com.brbr.brick.math.Vector2;
import com.brbr.brick.object.GameObject;

public class RaycastHit {
    public Vector2 point; // point where ray hit
    public double distance; // distance between source and point
    public Vector2 reflect; // reflect vector of ray

    public RaycastHit(){
        point = new Vector2();
        reflect = new Vector2();
    }
}

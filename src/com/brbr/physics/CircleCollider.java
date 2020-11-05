package com.brbr.physics;

public class CircleCollider extends Collider{
    double radius;

    // constructor
    public CircleCollider(){
        super();
    }

    public CircleCollider(ColliderType type){
        super(type);
    }

    public CircleCollider(double radius){
        this();
        this.radius = Math.abs(radius);
    }

    public CircleCollider(double radius, ColliderType type){
        this(type);
        this.radius = radius;
    }
}

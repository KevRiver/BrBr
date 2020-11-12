package com.brbr.brick.core;

import com.brbr.brick.physics.Collider;

public interface BRBehavior {
    public void onCollisionEnter(Collider collider);
    public void onTriggerEnter(Collider collider);

}

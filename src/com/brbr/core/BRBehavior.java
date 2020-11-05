package com.brbr.core;

import com.brbr.physics.Collider;

public interface BRBehavior {
    public void onCollisionEnter(Collider collider);
    public void onTriggerEnter(Collider collider);

}

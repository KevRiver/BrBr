package com.brbr.brick.core;

import com.brbr.brick.object.GameObject;
import com.brbr.brick.math.Transform;

public class Component {
    public boolean isActive;
    public GameObject gameObject;
    public Transform transform;

    protected Component() {
        isActive = true;
    }

    public void initWith(GameObject gameObject) {
        this.gameObject = gameObject;
        this.transform = gameObject.transform;
    }
}

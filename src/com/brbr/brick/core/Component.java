package com.brbr.brick.core;

import com.brbr.brick.object.GameObject;
import com.brbr.brick.math.Transform;

public class Component {
    public GameObject gameObject;
    public Transform transform;

    protected Component(){ }
    public void initWith(GameObject gameObject){ this.gameObject = gameObject; this.transform = gameObject.transform;}
}

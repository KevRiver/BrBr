package com.brbr.core;

import com.brbr.brick.object.GameObject;
import com.brbr.debug.Debugger;
import com.brbr.math.Transform;

public class Component {
    public GameObject gameObject;
    public Transform transform;

    protected Component(){ }
    public void initWith(GameObject gameObject){ this.gameObject = gameObject; this.transform = gameObject.transform;}
    public void update(double dt){ }
    public void destroy(){ }
}

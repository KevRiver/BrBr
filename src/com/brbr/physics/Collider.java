package com.brbr.physics;
import com.brbr.debug.Debugger;
import com.brbr.core.Component;
import com.brbr.math.Vector2;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

abstract public class Collider extends Component {
    String tag;
    ColliderType type;

    // constructor
    protected Collider(){
        super();
    }
    protected Collider(ColliderType type){
        this();
        this.type = type;
    }

    abstract public void setCenter(Vector2 position);
    abstract public Vector2 getPositionRelativeTo(BoxCollider collider);
    public void setTag(String tag){
        this.tag = tag;
    }
}

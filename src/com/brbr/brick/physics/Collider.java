package com.brbr.brick.physics;
import com.brbr.brick.core.Component;
import com.brbr.brick.math.Vector2;

public class Collider extends Component {
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

    public void setCenter(Vector2 position){ }

    public void setTag(String tag){
        this.tag = tag;
    }
}

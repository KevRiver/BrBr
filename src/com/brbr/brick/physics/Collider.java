package com.brbr.brick.physics;

import com.brbr.brick.core.Component;
import com.brbr.brick.math.Vector2;

abstract public class Collider extends Component { // 충돌체들을 나타내는 클래스
    String tag;
    ColliderType type;

    protected Collider() {
        super();
    }

    protected Collider(ColliderType type) {
        this();
        this.type = type;
    }

    abstract public void setCenter(Vector2 position);

    abstract public Vector2 getPositionRelativeTo(BoxCollider collider);

    public void setTag(String tag) {
        this.tag = tag;
    }
}

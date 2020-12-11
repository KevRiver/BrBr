package com.brbr.brick.physics;

public enum ColliderType {
    KINEMATIC, // 움직이는 Collider
    STATIC, // 움직이지 않는 Collider
    TRIGGER // GameObject간의 물리적 연산을 하지 않고 충돌을 감지하는 Collider
}

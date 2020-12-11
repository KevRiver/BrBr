package com.brbr.brick.core;

import com.brbr.brick.object.GameObject;
import com.brbr.brick.math.Transform;

// 기능 단위 객체 최상위 클래스
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
    } // 자신이 속해있는 게임 오브젝트에 대한 레퍼런스를 얻는다
}

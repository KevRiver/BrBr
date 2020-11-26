package com.brbr.brick;

import com.brbr.brick.object.AnimationObject;
import com.brbr.brick.object.GameObject;

public class AnimationManager {

    private Scene scene;

    public AnimationManager(Scene scene) {
        this.scene = scene;
    }

    public void update(long dt) {
        for (GameObject object : scene.gameObjectList) {
            if (object instanceof AnimationObject) {
                ((AnimationObject) object).update(dt);
            }
        }
    }

}

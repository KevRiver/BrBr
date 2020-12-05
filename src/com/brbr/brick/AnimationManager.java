package com.brbr.brick;

import com.brbr.brick.debug.Debugger;
import com.brbr.brick.object.AnimationObject;
import com.brbr.brick.object.GameObject;
import com.brbr.brick.object.Particle;

import java.util.stream.Collectors;

public class AnimationManager {

    private Scene scene;

    public AnimationManager(Scene scene) {
        this.scene = scene;
    }

    public void update(long dt) {
        if (scene.gameStatus != Scene.PROCEEDING_GAME) return;

        for (GameObject object : scene.gameObjectList) {
            if (object instanceof AnimationObject) {
                ((AnimationObject) object).update(dt);
            }
        }
        scene.gameObjectList = scene.gameObjectList.stream()
                .filter(gameObject -> {
                    if (gameObject instanceof Particle) return ((Particle) gameObject).isMoving;
                    else return true;
                })
                .collect(Collectors.toList());
    }

}

package com.brbr.brick.level;

import com.brbr.brick.Scene;
import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.math.Transform;
import com.brbr.brick.math.Vector2;
import com.brbr.brick.object.Brick;
import com.brbr.brick.object.GameObject;
import com.brbr.brick.physics.BoxCollider;
import com.brbr.brick.physics.ColliderType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LevelManager {

    private Scene scene;

    public LevelManager(Scene scene) {
        this.scene = scene;
    }

    public void update(long dt) {
        switch (scene.gameState) {
            case INIT -> scene.gameState = GameState.NEED_LEVEL_UPDATE;
            case NEED_LEVEL_UPDATE -> {
                createNewLevel();
                scene.gameState = GameState.PLAYING;
            }
            case PLAYING -> {
                // no-op
            }
        }
    }

    private void createNewLevel() {
        scene.level++;

        for (GameObject gameObject : scene.gameObjectList) {
            if (gameObject instanceof Brick) {
                Brick brick = (Brick) gameObject;
                BoxCollider collider = ((BoxCollider) brick.getComponent("BoxCollider"));
                collider.setCenter(
                        new Vector2(
                                collider.bounds.getCenter().x,
                                collider.bounds.getCenter().y + Coordinates.BRICK_HEIGHT + Coordinates.BRICK_MARGIN
                        )
                );
                brick.animateMove();
            }
        }

        Random random = new Random();

        List<Integer> itemIndexList = new ArrayList<>();
        for (int i = 0; i < 6; i++) itemIndexList.add(i);
        Collections.shuffle(itemIndexList);
        itemIndexList = itemIndexList.subList(0, random.nextInt(3) + 2);

        for (int index : itemIndexList) {
            Vector2 vector2 = new Vector2();
            vector2.x = (float) index * (Coordinates.BRICK_WIDTH + Coordinates.BRICK_MARGIN) + (Coordinates.BRICK_WIDTH + Coordinates.BRICK_MARGIN + Coordinates.GAME_FRAME_STROKE) / 2f;
            vector2.y = (float) Coordinates.BRICK_HEIGHT / 2f + scene.frameMarginTop + Coordinates.GAME_FRAME_STROKE;
            Brick brick = new Brick();
            Transform transform = new Transform();
            transform.translate(vector2);
            brick.transform = transform;
            brick.health = scene.level;
            ((BoxCollider) brick.addComponent(new BoxCollider(Coordinates.BRICK_WIDTH, Coordinates.BRICK_HEIGHT, ColliderType.STATIC))).setTag("brick0");
            scene.gameObjectList.add(brick);
        }
    }

    public enum GameState {
        INIT,
        NEED_LEVEL_UPDATE,
        PLAYING
    }
}

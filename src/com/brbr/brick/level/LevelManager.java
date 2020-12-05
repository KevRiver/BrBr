package com.brbr.brick.level;

import com.brbr.brick.Scene;
import com.brbr.brick.assets.Colors;
import com.brbr.brick.assets.Coordinates;
import com.brbr.brick.math.Transform;
import com.brbr.brick.math.Vector2;
import com.brbr.brick.object.Brick;
import com.brbr.brick.object.GameObject;
import com.brbr.brick.object.Particle;
import com.brbr.brick.physics.BoxCollider;
import com.brbr.brick.physics.ColliderType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LevelManager {

    private Scene scene;

    public LevelManager(Scene scene) {
        this.scene = scene;
    }

    public void createParticles(List particles, Brick brick){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 7; j++){
                BoxCollider collider = (BoxCollider) (brick.getComponent("BoxCollider"));

                Vector2 spawnPos = new Vector2(
                        collider.bounds.getMinX() + j * Coordinates.PARTICLE_SIZE,
                        collider.bounds.getMinY() + i * Coordinates.PARTICLE_SIZE
                );
                Particle particle = new Particle(spawnPos, Colors.BRICK_COLOR_LEVEL[0]);
                particles.add(particle);
            }
        }
    }

    public void update(long dt) {
        if (scene.gameStatus != Scene.PROCEEDING_GAME) return;

        if (scene.needLevelUpdate) {
            createNewLevel();
            scene.needLevelUpdate = false;
        } else {
            List<GameObject> particleList = new ArrayList();

            scene.gameObjectList = scene.gameObjectList.stream()
                    .filter(gameObject -> {
                        if (gameObject instanceof Brick) {
                            Brick brick = ((Brick) gameObject);

                            if(brick.health > 0) return true;

                            createParticles(particleList, brick);
                            return false;
                        } else return true;
                    })
                    .collect(Collectors.toList());
            scene.gameObjectList.addAll(particleList);
        }

        if (scene.gameStatus == Scene.END_GAME) {
            scene.level = 0;
            scene.gameObjectList = scene.gameObjectList.stream()
                    .filter(gameObject -> !(gameObject instanceof Brick))
                    .collect(Collectors.toList());
        }
    }

    private void createNewLevel() {
        scene.level++;
        scene.scoreManager.updateScore(1);

        int maxLevel = 0;
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
                int brickLevel = (int) (collider.bounds.getCenter().y / (Coordinates.BRICK_HEIGHT + Coordinates.BRICK_MARGIN));
                if (maxLevel < brickLevel) maxLevel = brickLevel;
                brick.animateMove();
            }
        }
        if (maxLevel == 10) {
            scene.gameStatus = Scene.END_GAME;
        }

        scene.scheduler.postDelayed(100, () -> {
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
                        brick.level = scene.level;
                        ((BoxCollider) brick.addComponent(new BoxCollider(Coordinates.BRICK_WIDTH, Coordinates.BRICK_HEIGHT, ColliderType.STATIC))).setTag("brick0");
                        scene.gameObjectList.add(brick);
                    }
                }
        );
    }
}

package com.brbr.brick;

import com.brbr.brick.level.LevelManager;
import com.brbr.brick.object.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    public List<GameObject> gameObjectList = new ArrayList();

    public int level = 0;
    public LevelManager.GameState gameState = LevelManager.GameState.INIT;
    public int framePerSecond;

    public int frameMarginTop = 100;
    public int frameWidth = UNINITIALIZED;
    public int frameHeight = UNINITIALIZED;

    private static final int UNINITIALIZED = -1;

}

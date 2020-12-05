package com.brbr.brick;

import com.brbr.brick.object.GameObject;
import com.brbr.brick.object.RayPath;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    public static final int BEFORE_GAME = 0;
    public static final int PROCEEDING_GAME = 1;
    public static final int PAUSE_GAME = 2;
    public static final int END_GAME = 3;

    public ScoreManager scoreManager = new ScoreManager();
    public Scheduler scheduler = new Scheduler();
    public int gameStatus = BEFORE_GAME;

    public List<GameObject> gameObjectList = new ArrayList();
    public RayPath rayPath;

    public int level = 0;
    public int framePerSecond;

    public int frameMarginTop = 100;
    public int frameWidth = UNINITIALIZED;
    public int frameHeight = UNINITIALIZED;

    public boolean needLevelUpdate = true;

    private static final int UNINITIALIZED = -1;

}

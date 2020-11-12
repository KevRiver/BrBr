package com.brbr.brick;

import com.brbr.brick.object.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    public int framePerSecond;
    public List<GameObject> gameObjectList = new ArrayList();
    public int frameMarginTop = UNINITIALIZED;
    public int frameWidth = UNINITIALIZED;
    public int frameHeight = UNINITIALIZED;

    private static final int UNINITIALIZED = -1;
}

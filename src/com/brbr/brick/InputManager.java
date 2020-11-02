package com.brbr.brick;

public class InputManager {
    private static InputManager instance = null;
    public MouseEventListener mouseEventListener;

    private InputManager(){
        mouseEventListener = new MouseEventListener();
    }

    public static InputManager getInstance(){
        if(instance == null) return new InputManager();
        return instance;
    }

    public void setActive(boolean activation){
        mouseEventListener.setActive(activation);
    }



}


package com.brbr.brick;

import javax.swing.*;

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

    public void setTarget(JComponent component){
        component.addMouseMotionListener(mouseEventListener);
        component.addMouseListener(mouseEventListener);
    }


}

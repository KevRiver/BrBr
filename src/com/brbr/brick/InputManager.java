package com.brbr.brick;

import com.brbr.brick.debug.Debugger;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Queue;

public class InputManager {
    private static InputManager instance = null;
    private Queue<InputData> input;
    private MouseEventListener mouseEventListener;
    private boolean isActive;

    private InputManager(){
        mouseEventListener = new MouseEventListener();
        input = new LinkedList<>();
        isActive = true;

    }

    public static InputManager getInstance(){
        if(instance == null) return new InputManager();
        return instance;
    }

    public void setActive(boolean activation){
        isActive = activation;
    }

    public void setTarget(JComponent component){
        component.addMouseMotionListener(mouseEventListener);
        component.addMouseListener(mouseEventListener);
    }

    public InputData poll(){
        if(input.isEmpty()){
            Debugger.Print("Queue is empty");
            return null;
        }
        return input.poll();
    }

    class MouseEventListener implements MouseMotionListener, MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if(!isActive) return;
            Debugger.Print("MouseClicked");
            InputData inputData = new InputData(InputData.InputType.Click, e.getX(), e.getY());
            input.add(inputData);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(!isActive) return;
            Debugger.Print("MousePressed");
            InputData inputData = new InputData(InputData.InputType.Press, e.getX(), e.getY());
            input.add(inputData);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(!isActive) return;
            InputData inputData = new InputData(InputData.InputType.Release, e.getX(), e.getY());
            input.add(inputData);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(!isActive) return;
            InputData inputData = new InputData(InputData.InputType.Drag, e.getX(), e.getY());
            input.add(inputData);
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }



}

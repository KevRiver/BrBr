package com.brbr.brick.event;

import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Vector2;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DragEvent implements IEvent<IEventListener<DragEvent>, MouseEvent>{
    private static DragEvent instance = null;
    private MouseEvent mouseEvent;
    private List<IEventListener<DragEvent>> listeners;

    // constructor
    private DragEvent(){
        listeners = new ArrayList<>();
    }

    public static DragEvent getInstance(){
        if(instance == null){
            instance = new DragEvent();
        }
        return instance;
    }
    // getter
    public MouseEvent getMouseEvent(){return mouseEvent;}
    @Override
    public void raise(final MouseEvent e) {
        mouseEvent = e;
        for(var listener: listeners){
            listener.onEvent(instance);
        }
    }

    @Override
    public void addListener(IEventListener<DragEvent> listener) {
        listeners.add(listener);
        Debugger.Print("DragEvent listener added: " + listeners.size());
    }

    @Override
    public void removeListener(IEventListener<DragEvent> listener) {
        listeners.remove(listener);
        Debugger.Print("listener removed: " + listeners.size());
    }
}

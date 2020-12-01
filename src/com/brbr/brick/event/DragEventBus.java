package com.brbr.brick.event;

import com.brbr.brick.debug.Debugger;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DragEventBus implements IEventBus<IEventListener<DragEventBus>, MouseEvent> {
    private static DragEventBus instance = null;
    private MouseEvent mouseEvent;
    private List<IEventListener<DragEventBus>> listeners;

    // constructor
    private DragEventBus(){
        listeners = new ArrayList<>();
    }

    public static DragEventBus getInstance(){
        if(instance == null){
            instance = new DragEventBus();
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
    public void addListener(IEventListener<DragEventBus> listener) {
        listeners.add(listener);
        Debugger.Print("DragEvent listener added: " + listeners.size());
    }

    @Override
    public void removeListener(IEventListener<DragEventBus> listener) {
        listeners.remove(listener);
        Debugger.Print("listener removed: " + listeners.size());
    }
}

package com.brbr.brick.event;

import com.brbr.brick.debug.Debugger;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ReleaseEventBus implements IEventBus<IEventListener<ReleaseEventBus>, MouseEvent> {
    private static ReleaseEventBus instance = null;
    private MouseEvent mouseEvent;
    private List<IEventListener<ReleaseEventBus>> listeners;

    private ReleaseEventBus(){
        listeners = new ArrayList<>();
    }
    public static ReleaseEventBus getInstance(){
        if(instance == null){
            instance = new ReleaseEventBus();
        }
        return instance;
    }

    public MouseEvent getMouseEvent(){return mouseEvent;}

    @Override
    public void raise(MouseEvent e) {
        mouseEvent = e;
        for(var listener : listeners){
            listener.onEvent(instance);
        }
    }

    @Override
    public void addListener(IEventListener<ReleaseEventBus> listener) {
        listeners.add(listener);
        Debugger.Print("ReleaseEvent listener added: " + listeners.size());
    }

    @Override
    public void removeListener(IEventListener<ReleaseEventBus> listener) {
        listeners.remove(listener);
        Debugger.Print("ReleaseEvent listener removed: " + listeners.size());
    }
}

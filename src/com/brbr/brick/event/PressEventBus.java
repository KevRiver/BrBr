package com.brbr.brick.event;

import com.brbr.brick.debug.Debugger;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PressEventBus implements IEventBus<IEventListener<PressEventBus>, MouseEvent> {
    private static PressEventBus instance = null;
    private MouseEvent mouseEvent;
    private List<IEventListener<PressEventBus>> listeners;

    private PressEventBus(){
        listeners = new ArrayList<>();
    }

    public static PressEventBus getInstance(){
        if(instance == null){
            instance = new PressEventBus();
        }
        return instance;
    }

    // getter
    public MouseEvent getMouseEvent(){return mouseEvent;}

    @Override
    public void raise(final MouseEvent e) {
        mouseEvent = e;
        for(var listener : listeners){
            listener.onEvent(instance);
        }
    }

    @Override
    public void addListener(IEventListener<PressEventBus> listener) {
        listeners.add(listener);
        Debugger.Print("PressEvent listener added: " + listeners.size());
    }

    @Override
    public void removeListener(IEventListener<PressEventBus> listener) {
        listeners.remove(listener);
        Debugger.Print("PressEvent listener removed: " + listeners.size());
    }
}

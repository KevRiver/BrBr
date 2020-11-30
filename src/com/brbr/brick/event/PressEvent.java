package com.brbr.brick.event;

import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Vector2;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PressEvent implements IEvent<IEventListener<PressEvent>, MouseEvent>{
    private static PressEvent instance = null;
    private MouseEvent mouseEvent;
    private List<IEventListener<PressEvent>> listeners;

    private PressEvent(){
        listeners = new ArrayList<>();
    }

    public static PressEvent getInstance(){
        if(instance == null){
            instance = new PressEvent();
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
    public void addListener(IEventListener<PressEvent> listener) {
        listeners.add(listener);
        Debugger.Print("PressEvent listener added: " + listeners.size());
    }

    @Override
    public void removeListener(IEventListener<PressEvent> listener) {
        listeners.remove(listener);
        Debugger.Print("PressEvent listener removed: " + listeners.size());
    }
}

package com.brbr.brick.event;

import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Vector2;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ReleaseEvent implements IEvent<IEventListener<ReleaseEvent>, MouseEvent>{
    private static ReleaseEvent instance = null;
    private MouseEvent mouseEvent;
    private List<IEventListener<ReleaseEvent>> listeners;

    private ReleaseEvent(){
        listeners = new ArrayList<>();
    }
    public static ReleaseEvent getInstance(){
        if(instance == null){
            instance = new ReleaseEvent();
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
    public void addListener(IEventListener<ReleaseEvent> listener) {
        listeners.add(listener);
        Debugger.Print("ReleaseEvent listener added: " + listeners.size());
    }

    @Override
    public void removeListener(IEventListener<ReleaseEvent> listener) {
        listeners.remove(listener);
        Debugger.Print("ReleaseEvent listener removed: " + listeners.size());
    }
}

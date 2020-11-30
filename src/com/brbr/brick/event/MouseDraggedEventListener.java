package com.brbr.brick.event;

import com.brbr.brick.debug.Debugger;

import java.io.IOException;

public class MouseDraggedEventListener implements IEventListener<DragEvent>{

    public MouseDraggedEventListener(){
        register(DragEvent.getInstance());
    }

    @Override
    public void register(DragEvent dragEvent) {
        dragEvent.addListener(this);
    }

    @Override
    public void deregister(DragEvent dragEvent) {
        dragEvent.removeListener(this);
    }

    @Override
    public void onEvent(final DragEvent event) {
        try {
            Debugger.Print("Dragged: " + event.getMouseEvent().getX() + ", " + event.getMouseEvent().getY());
        }catch (Exception exception){
            Debugger.Print(exception.toString());
        }
    }
}

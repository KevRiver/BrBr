package com.brbr.brick.event;

import com.brbr.brick.debug.Debugger;

import java.io.Closeable;
import java.io.IOException;

public class MousePressedEventListener implements IEventListener<PressEvent>{
    public MousePressedEventListener(){
        register(PressEvent.getInstance());
    }

    @Override
    public void register(PressEvent pressEvent) {
        pressEvent.addListener(this);
    }

    @Override
    public void deregister(PressEvent pressEvent) {
        pressEvent.removeListener(this);
    }

    @Override
    public void onEvent(final PressEvent event) {
        try {
            Debugger.Print("Pressed: " + event.getMouseEvent().getX() + ", " + event.getMouseEvent().getY());
        }catch (Exception exception){
            Debugger.Print(exception.toString());
        }
    }

}

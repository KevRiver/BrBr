package com.brbr.brick.event;

import com.brbr.brick.debug.Debugger;

import java.io.IOException;

public class MouseReleasedEventListener implements IEventListener<ReleaseEvent> {

    public MouseReleasedEventListener(){
        register(ReleaseEvent.getInstance());
    }

    @Override
    public void register(ReleaseEvent releaseEvent) {
        releaseEvent.addListener(this);
    }

    @Override
    public void deregister(ReleaseEvent releaseEvent) {
        releaseEvent.removeListener(this);
    }

    @Override
    public void onEvent(final ReleaseEvent event) {
        try {
            Debugger.Print("Released: " + event.getMouseEvent().getX() + ", " + event.getMouseEvent().getY());
        }catch (Exception exception){
            Debugger.Print(exception.toString());
        }
    }
}

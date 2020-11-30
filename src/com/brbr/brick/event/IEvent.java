package com.brbr.brick.event;

import java.util.EventObject;

/// T : IEventListener, U another event
public interface IEvent<T extends IEventListener, U extends EventObject> {
    void raise(U u);
    void addListener(T t);
    void removeListener(T t);
}

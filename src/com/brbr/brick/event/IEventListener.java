package com.brbr.brick.event;

import java.io.Closeable;
import java.io.IOException;

public interface IEventListener<T extends IEvent>{
    void register(T t);
    void deregister(T t);
    void onEvent(T t);
}

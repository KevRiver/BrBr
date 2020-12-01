package com.brbr.brick.event;

public interface IEventListener<T extends IEventBus>{
    void register(T t);
    void deregister(T t);
    void onEvent(T t);
}

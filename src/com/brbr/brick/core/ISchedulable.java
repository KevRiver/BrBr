package com.brbr.brick.core;

public interface ISchedulable {
    void addSchedule(double sec);
    void onSchedule();
}

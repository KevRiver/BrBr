package com.brbr.brick.core;

public class Schedule implements Comparable<Schedule>{
    double when;
    ISchedulable schedulable;

    public Schedule(){}
    public Schedule(double _when, ISchedulable _schedulable){
        when = _when;
        schedulable = _schedulable;
    }

    @Override
    public int compareTo(Schedule _other) {
        return ((int) (when - _other.when));
    }
}

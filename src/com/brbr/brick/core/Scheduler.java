package com.brbr.brick.core;

import com.brbr.brick.Scene;
import com.brbr.brick.debug.Debugger;

import java.util.PriorityQueue;

public class Scheduler {
    private static Scheduler instance = null;
    private double timer;
    PriorityQueue<Schedule> queue;

    private Scheduler(){
        timer = 0;
        queue = new PriorityQueue<>();
    }// constructor

    // methods
    public static Scheduler getInstance(){
        if(instance == null){
            instance = new Scheduler();
        }
        return instance;
    }

    public void update(double dt){
        addTime(dt);
        checkSchedule();
    }

    public void setTimer(double _time){
        timer = _time;
    }

    private void addTime(double _time){
        timer += _time;
    }

    public void addSchedule(final double sec, final ISchedulable s){
        Schedule schedule = new Schedule(timer + sec, s);
        if(!queue.offer(schedule)) Debugger.Print("error Scheduler.addSchedule");
    }

    private void checkSchedule(){
        while(!queue.isEmpty() && queue.peek().when <= timer){
            Schedule schedule = queue.poll();
            schedule.schedulable.onSchedule();
        }

        if(queue.isEmpty()){
            setTimer(0);
        }
    }
}

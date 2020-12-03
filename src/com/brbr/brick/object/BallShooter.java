package com.brbr.brick.object;

import com.brbr.brick.core.ISchedulable;
import com.brbr.brick.core.Scheduler;
import com.brbr.brick.debug.Debugger;
import com.brbr.brick.event.IEventListener;
import com.brbr.brick.event.PressEventBus;
import com.brbr.brick.event.ReleaseEventBus;
import com.brbr.brick.math.Vector2;
import com.brbr.brick.physics.Ball;

import java.util.ArrayList;
import java.util.List;

public class BallShooter extends GameObject {
    private double distanceBetweenBalls; // ball - ball distance;
    private double ballSpeed;
    private double shootInterval;
    private Vector2 shootDirection;
    private List<Ball> balls;
    private int ballIndex;
    private ReleaseEventListener releaseEventListener;
    private PressEventListener pressEventListener;

    // constructor
    public BallShooter(){
        super();
        init();
    }
    public BallShooter(int x, int y){
        super(x,y);
        init();
    }

    public void init(){
        releaseEventListener = new ReleaseEventListener();
        pressEventListener = new PressEventListener();

        ballSpeed = 500;
        distanceBetweenBalls = 20;
        setShootInterval(distanceBetweenBalls, ballSpeed);
        shootDirection = new Vector2();
    }

    // setter
    public void setBallSpeed(double _ballSpeed){
        if(_ballSpeed <= 0){
            ballSpeed = 500;
            return;
        }
        ballSpeed = _ballSpeed;
    }

    public void setDistanceBetweenBalls(double _distance){
        distanceBetweenBalls = _distance;
    }

    private void setShootInterval(double distance, double speed){
        shootInterval = distance / speed;
    }

    private void setShootDirection(Vector2 _dir){
        shootDirection.x = _dir.x;
        shootDirection.y = _dir.y;
    }

    public void setAmmo(int _ammo){
        balls.clear();
        for(int i = 0; i < _ammo; i++){
            balls.add(new Ball());
        }
    }

    public void addAmmo(int _ammo){
        for(int i = 0; i < _ammo; i++){
            balls.add(new Ball());
        }
    }

    private void requestFire(){    }
    private void fire(int i){    }

    class ReleaseEventListener implements IEventListener<ReleaseEventBus>{
        public ReleaseEventListener(){
            register(ReleaseEventBus.getInstance());
        }
        @Override
        public void register(ReleaseEventBus releaseEventBus) {
            releaseEventBus.addListener(this);
        }

        @Override
        public void deregister(ReleaseEventBus releaseEventBus) {
            releaseEventBus.removeListener(this);
        }

        @Override
        public void onEvent(ReleaseEventBus releaseEventBus) {
            double x = releaseEventBus.getMouseEvent().getX();
            double y = releaseEventBus.getMouseEvent().getY();
            Debugger.Print("Released: (" + x + ", " + y + ")");
            Vector2 destination = new Vector2(x, y);
            Vector2 direction = Vector2.subtract(destination, transform.position);
            Debugger.Print("Direction: (" + direction.x + ", " + direction.y + ")");
            // TODO: normalize direction
            setShootDirection(direction);

            ballIndex = 0;
            requestFire();
        }
    }

    class PressEventListener implements IEventListener<PressEventBus>{
        public PressEventListener(){
            register(PressEventBus.getInstance());
        }
        @Override
        public void register(PressEventBus pressEventBus) {
            pressEventBus.addListener(this);
        }

        @Override
        public void deregister(PressEventBus pressEventBus) {
            pressEventBus.removeListener(this);
        }

        @Override
        public void onEvent(PressEventBus pressEventBus) {
            double x = pressEventBus.getMouseEvent().getX();
            double y = pressEventBus.getMouseEvent().getY();
            Debugger.Print("Pressed: (" + x + ", " + y + ")");
        }
    }
}

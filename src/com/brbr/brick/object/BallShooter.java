package com.brbr.brick.object;

import com.brbr.brick.debug.Debugger;
import com.brbr.brick.event.IEventListener;
import com.brbr.brick.event.ReleaseEvent;
import com.brbr.brick.math.Vector2;

public class BallShooter extends GameObject implements IEventListener<ReleaseEvent> {
    private double distanceBetweenBalls; // ball - ball distance;
    private double ballSpeed;
    private double shootInterval;
    private Vector2 shootDirection;
    private int ammo;

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
        register(ReleaseEvent.getInstance());

        ballSpeed = 500;
        distanceBetweenBalls = 20;
        setShootInterval(distanceBetweenBalls, ballSpeed);
        shootDirection = new Vector2();
        ammo = 1;
    }

    // setter
    public void setPosition(Vector2 position){
        transform.position.x = position.x;
        transform.position.y = position.y;
    }

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
        ammo = _ammo > 0 ? _ammo : 1;
    }

    public void addAmmo(int _ammo){
        ammo = _ammo > 0 ? ammo + _ammo : ammo;
    }

    // method
    private void fire(){
        for(int i = 0; i < ammo; i++){
            // throw ball
            // ball.setDirection(shootDirection)
            // wait for interval -> need coroutine

        }
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
    public void onEvent(ReleaseEvent releaseEvent) {
        int x,y;
        x = releaseEvent.getMouseEvent().getX();
        y = releaseEvent.getMouseEvent().getY();
        Vector2 destination = new Vector2(x, y);
        Vector2 direction = Vector2.subtract(destination, transform.position);
        Debugger.Print("Direction: (" + direction.x + ", " + direction.y + ")");
        // normalize direction
        setShootDirection(direction);

        fire();
    }
}

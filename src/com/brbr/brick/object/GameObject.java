package com.brbr.brick.object;

import com.brbr.core.BRBehavior;
import com.brbr.core.Component;
import com.brbr.debug.Debugger;
import com.brbr.math.Transform;
import com.brbr.math.Vector2;
import com.brbr.physics.Collider;

import java.util.HashMap;
import java.util.Map;

public class GameObject implements BRBehavior {
    public Transform transform;
    protected Map<String, Component> componentMap;

    //constructor
    public GameObject(){
        transform = new Transform();
        componentMap = new HashMap<>();
        // add to Scene
    }

    public GameObject(int x, int y){
        this();
        // set GameObject's position to (x, y)
        transform.position.x = x;
        transform.position.y = y;
    }

    public <T extends Component> Component addComponent(T component){
        String key = component.getClass().getSimpleName();
        Debugger.Print("addComponent.key: " + key);
        if(componentMap.get(key) != null){
            Debugger.Print(key + " already exist");
            return null;
        }
        component.initWith(this);
        componentMap.put(key, component);
        return component;
    }

    public Component getComponent(String componentName){
        Component ret = componentMap.get(componentName);
        return ret;
    }

    public void removeComponent(String componentName){
        try{
            componentMap.remove(componentName);
        }catch (Exception exception){
            Debugger.Print(exception.toString());
        }
    }

    @Override
    public void onCollisionEnter(Collider collider) {

    }

    @Override
    public void onTriggerEnter(Collider collider) {

    }
}

package com.brbr.brick.object;

import com.brbr.brick.core.BRBehavior;
import com.brbr.brick.core.Component;
import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Transform;
import com.brbr.brick.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class GameObject extends BRBehavior {
    public Transform transform;
    protected Map<String, Component> componentMap;

    public GameObject() {
        transform = new Transform();
        componentMap = new HashMap<>();
    }

    public GameObject(int x, int y) {
        this();
        transform.position.x = x;
        transform.position.y = y;
    }

    public void setPosition(Vector2 _position){
        transform.position.x = _position.x;
        transform.position.y = _position.y;
    }

    public <T extends Component> Component addComponent(T component) {
        String key = component.getClass().getSimpleName();
        Debugger.Print("addComponent.key: " + key);
        if (componentMap.get(key) != null) {
            Debugger.Print(key + " already exist");
            return null;
        }
        component.initWith(this);
        componentMap.put(key, component);
        return component;
    } // 컴포넌트 해시맵에 해당 클래스의 SimpleName 을 키값으로 추가

    public Component getComponent(String componentName) {
        return componentMap.get(componentName);
    } // 키 값을 통해 컴포넌트에 접근

    public Map<String, Component> getComponents() {
        return Map.copyOf(componentMap);
    } // 컴포넌트 해시맵 접근

    public void removeComponent(String componentName) {
        try {
            componentMap.remove(componentName);
        } catch (Exception exception) {
            Debugger.Print(exception.toString());
        }
    }
}

package com.brbr.brick.UI;

import com.brbr.brick.InputData;
import com.brbr.brick.debug.Debugger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UIManager {
    private static UIManager instance = null;
    private List<UILayer> layerList = new ArrayList();

    public static UIManager getInstance(){
        if(instance == null) instance = new UIManager();
        return instance;
    }

    public void addLayer(UILayer layer){
        layerList.add(layer);
    }

    public void drawUI(Graphics g, int gameWidth, int gameHeight){
        for(UILayer layer: layerList.stream().filter(layer -> layer.visible).collect(Collectors.toList())) {
            layer.drawUI(g, gameWidth, gameHeight);
        }
    }

    public boolean buttonClickCheck(int pointX, int pointY){
        for(UILayer layer: layerList.stream().filter(layer -> !layer.clickUnable).collect(Collectors.toList())) {
            return layer.buttonClickCheck(pointX, pointY);
        }
        return false;
    }

    public boolean buttonClickCheck(InputData inputData){
        if(inputData.type != InputData.InputType.Press) return false;
        Debugger.Print("buttonclickcheck");
        int pointX = inputData.x;
        int pointY = inputData.y;

        for(UILayer layer: layerList.stream().filter(layer -> !layer.clickUnable).collect(Collectors.toList())) {
            return layer.buttonClickCheck(pointX, pointY);
        }
        return false;
    }

}

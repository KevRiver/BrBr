package com.brbr.brick.UI;

import com.brbr.brick.debug.Debugger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIManager {
    private static UIManager instance = null;
    private List<TextUI> textUIList = new ArrayList();
    private List<ButtonUI> buttonUIList = new ArrayList();

    public static UIManager getInstance(){
        if(instance == null) instance = new UIManager();
        return instance;
    }

    public void drawUI(Graphics g){
        for(TextUI textUI: textUIList)
            textUI.drawUI(g);

        for(ButtonUI buttonUI: buttonUIList)
            buttonUI.drawUI(g);
    }

    public void addButtonUI(ButtonUI button){
        buttonUIList.add(button);
    }

    public void addTextUI(TextUI text){
        textUIList.add(text);
    }

    public void clearUIs(){
        textUIList.clear();
        buttonUIList.clear();
    }

    public void buttonClickCheck(int pointX, int pointY){
        for(ButtonUI button: buttonUIList){
            if(button.position.x < pointX && pointX < button.position.x + button.width &&
            button.position.y < pointY && pointY < button.position.y + button.height){
                button.buttonClicked();
            }
        }
    }
}

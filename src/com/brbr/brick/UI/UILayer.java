package com.brbr.brick.UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UILayer {
    private List<TextUI> textUIList = new ArrayList();
    private List<ButtonUI> buttonUIList = new ArrayList();
    public boolean visible = true;
    public boolean clickUnable = false;
    public boolean background = false;

    public void drawUI(Graphics g, int gameWidth, int gameHeight){
        if(background) {
            Color c = new Color(255, 255, 255, 200);
            g.setColor(c);
            g.fillRect(0, 0, gameWidth, gameHeight);
        }

        for(TextUI textUI: textUIList.stream().filter(ui -> ui.visible).collect(Collectors.toList()))
            textUI.drawUI(g);

        for(ButtonUI buttonUI: buttonUIList.stream().filter(ui -> ui.visible).collect(Collectors.toList()))
            buttonUI.drawUI(g);
    }

    public void addButtonUI(ButtonUI button){
        buttonUIList.add(button);
    }

    public void addTextUI(TextUI text){
        textUIList.add(text);
    }

    public void deleteButtonUI(ButtonUI button){ buttonUIList.remove(button); }

    public void deleteTextUI(TextUI text){ textUIList.remove(text); }

    public void clearUIs(){
        textUIList.clear();
        buttonUIList.clear();
    }

    public void setVisible(boolean visible){
        this.visible = visible;
        clickUnable = !visible;
    }

    public void setClickUnable(boolean clickUnable){
        this.clickUnable = clickUnable;
    }

    public boolean buttonClickCheck(int pointX, int pointY){
        for(ButtonUI button: buttonUIList){
            if(button.position.x < pointX && pointX < button.position.x + button.width &&
                    button.position.y < pointY && pointY < button.position.y + button.height){
                button.buttonClicked();
                return true;
            }
        }

        return false;
    }
}

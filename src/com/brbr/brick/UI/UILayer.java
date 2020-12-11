package com.brbr.brick.UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UILayer { // 게임 시작전, 일시정지, 게임종료 등의 게임 화면들을 분리하기 위한 클래스
    private List<TextUI> textUIList = new ArrayList();
    private List<ButtonUI> buttonUIList = new ArrayList();
    public boolean visible = true;
    public boolean clickUnable = false;
    public boolean background = false;

    public void drawUI(Graphics g, int gameWidth, int gameHeight) {
        // UIManager는 UILayer들을 하나씩 렌더해주고 UILayer는 해당 레이어의 오브젝트들을 렌더해준다.
        if (background) {
            Color c = new Color(255, 255, 255, 200);
            g.setColor(c);
            g.fillRect(0, 0, gameWidth, gameHeight);
        }

        for (TextUI textUI : textUIList.stream().filter(ui -> ui.visible).collect(Collectors.toList()))
            textUI.drawUI(g);

        for (ButtonUI buttonUI : buttonUIList.stream().filter(ui -> ui.visible).collect(Collectors.toList()))
            buttonUI.drawUI(g);
    }

    public void addButtonUI(ButtonUI button) { // 버튼을 Layer에 추가
        buttonUIList.add(button);
    }

    public void addTextUI(TextUI text) { // 텍스트를 Layer에 추가
        textUIList.add(text);
    }

    public void deleteButtonUI(ButtonUI button) {
        buttonUIList.remove(button);
    } // 버튼을 레이어에서 제거

    public void deleteTextUI(TextUI text) {
        textUIList.remove(text);
    } // 텍스트를 레이어에서 제거

    public void clearUIs() { // 레이어를 비워준다.
        textUIList.clear();
        buttonUIList.clear();
    }

    public void setVisible(boolean visible) { // 해당 레이어를 visible을 설정해준다.
        this.visible = visible;
        clickUnable = !visible;
    }

    public void setClickUnable(boolean clickUnable) {
        this.clickUnable = clickUnable;
    } // clickUnable을 체크해준다.

    public boolean buttonClickCheck(int pointX, int pointY) { // 버튼이 영역안에 들어왔는지 체크하고 버튼 클릭 되었을 때 콜백메서드를 호출해준다.
        for (ButtonUI button : buttonUIList) {
            if (button.position.x < pointX && pointX < button.position.x + button.width &&
                    button.position.y < pointY && pointY < button.position.y + button.height) {
                button.buttonClicked();
                return true;
            }
        }

        return false;
    }
}

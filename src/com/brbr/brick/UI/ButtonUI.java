package com.brbr.brick.UI;

import com.brbr.brick.math.Vector2;

import java.awt.*;

public class ButtonUI implements UI { // 버튼 UI
    private ButtonClickCallback buttonClickCallback;

    public String text;
    public Vector2 position;
    public int textSize;
    public int width;
    public int height;
    public boolean visible = true;
    public Color backgroundColor;
    public Color textColor;

    public ButtonUI(String text) {
        setText(text);
        position = new Vector2();

        backgroundColor = Color.WHITE;
        textColor = Color.BLACK;
    }

    public ButtonUI(String text, Vector2 position, int textSize, int width, int height) {
        this(text);
        setPosition(position);
        setTextSize(textSize);
        this.width = width;
        this.height = height;
    }

    public void setButtonClickCallback(ButtonClickCallback buttonClickCallback) {
        this.buttonClickCallback = buttonClickCallback;
    }

    public void drawUI(Graphics g) { // 영역 안에 버튼을 렌더해준다.
        g.setFont(new Font("Verdana", Font.BOLD, textSize));
        g.setColor(textColor);
        g.drawRoundRect((int) position.x, (int) position.y, width, height, 10, 10);
        g.setColor(backgroundColor);
        g.fillRoundRect((int) position.x, (int) position.y, width, height, 10, 10);

        g.setColor(textColor);
        g.drawString(text, (int) position.x + width / 2 - (int) ((textSize * text.length()) * 0.65 / 2),
                (int) position.y + 15 + height / 2 - (int) (textSize * 0.9 / 2));
    }

    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }

    public void setTextColor(Color color) {
        textColor = color;
    }

    public void setPosition(Vector2 position) {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void buttonClicked() {
        if (!visible) return;
        this.buttonClickCallback.clicked();
    }
}

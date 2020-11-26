package com.brbr.brick.UI;

import com.brbr.brick.math.Vector2;

import java.awt.*;

public class ButtonUI implements UI{
    private ButtonClickCallback buttonClickCallback;

    public String text;
    public Vector2 position;
    public int textSize;
    public int width;
    public int height;

    public ButtonUI(String text){
        setText(text);
        position = new Vector2();
    }

    public ButtonUI(String text, Vector2 position, int textSize, int width, int height,
                    ButtonClickCallback buttonClickCallback){
        this(text);
        setPosition(position);
        setTextSize(textSize);
        this.width = width;
        this.height = height;
        this.buttonClickCallback = buttonClickCallback;
    }

    public void drawUI(Graphics g){
        g.setFont(new Font("Verdana", Font.BOLD, textSize));
        g.drawRoundRect((int)position.x, (int)position.y, width, height, 10, 10);

        g.drawString(text, (int)position.x + width / 2 - (textSize * text.length()) / 2,
                (int)position.y + 15 + height / 2 - textSize / 2);
    }

    public void setPosition(Vector2 position){
        this.position.x = position.x;
        this.position.y = position.y;
    }

    public void setTextSize(int textSize){
        this.textSize = textSize;
    }

    public void setText(String text){
        this.text = text;
    }

    public void buttonClicked(){
        this.buttonClickCallback.clicked();
    }
}

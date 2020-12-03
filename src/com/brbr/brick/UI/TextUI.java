package com.brbr.brick.UI;

import com.brbr.brick.math.Vector2;

import java.awt.*;

public class TextUI implements UI{
    public String text;
    public Vector2 position;
    public int textSize;
    public boolean visible = true;

    public TextUI(String text){
        setText(text);
        position = new Vector2();
    }

    public TextUI(String text, Vector2 position, int size){
        this(text);
        setPosition(position);
        setTextSize(size);
    }

    public void drawUI(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, textSize));
        g.drawString(text, (int)position.x, (int)position.y);
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
}

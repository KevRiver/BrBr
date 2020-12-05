package com.brbr.brick.UI;

import com.brbr.brick.math.Vector2;

import java.awt.*;

public interface UI {
    void drawUI(Graphics g);

    void setPosition(Vector2 position);

    void setTextSize(int textSize);

    void setText(String text);
}

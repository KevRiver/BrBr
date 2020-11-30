package com.brbr.brick.UI;
import com.brbr.brick.math.Vector2;

import java.awt.*;

public interface UI {
    public void drawUI(Graphics g);

    public void setPosition(Vector2 position);
    public void setTextSize(int textSize);
    public void setText(String text);
}

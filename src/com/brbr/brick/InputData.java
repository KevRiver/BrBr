package com.brbr.brick;

// 입력 데이터 모델
public class InputData {
    public enum InputType {
        Press,
        Drag,
        Release,
        Click
    }

    public InputType type;
    public int x, y; // pointer position

    public InputData(InputType _type, int _x, int _y) {
        type = _type;
        x = _x;
        y = _y;
    }
}

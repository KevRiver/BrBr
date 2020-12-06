package com.brbr.brick.UI;

import com.brbr.brick.InputData;
import com.brbr.brick.Scene;
import com.brbr.brick.debug.Debugger;
import com.brbr.brick.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.brbr.brick.GameManager.GAME_HEIGHT;
import static com.brbr.brick.GameManager.GAME_WIDTH;

public class UIManager {

    private Scene scene;
    private static UIManager instance = null;
    private List<UILayer> layerList = new ArrayList();

    private UILayer beforeLayer;
    private UILayer pauseLayer;
    private UILayer proceedingLayer;

    private TextUI scoreUI;
    private TextUI recordUI;
    private ButtonUI pauseButton;

    public static UIManager getInstance(Scene scene) {
        if (instance == null) instance = new UIManager(scene);
        return instance;
    }

    public static UIManager getInstance() {
        if (instance == null) throw new NullPointerException();
        return instance;
    }

    public UIManager(Scene scene) {
        this.scene = scene;
    }

    public void init() {
        setProceedingUI();
        setBeforeUI();
        setPauseUI();
    }

    private void setBeforeUI() {
        beforeLayer = new UILayer();

        Color backgroundColor = new Color(251, 136, 54);
        Color textColor = Color.WHITE;

        ButtonUI startButton = new ButtonUI("Game Start", new Vector2(GAME_WIDTH / 2f - 100, GAME_HEIGHT / 2f - 55),
                20, 200, 50);
        startButton.setBackgroundColor(backgroundColor);
        startButton.setTextColor(textColor);

        ButtonUI quitButton = new ButtonUI("Quit", new Vector2(GAME_WIDTH / 2f - 100, GAME_HEIGHT / 2f),
                20, 200, 50);
        quitButton.setBackgroundColor(backgroundColor);
        quitButton.setTextColor(textColor);

        startButton.setButtonClickCallback(() -> {
            scene.gameStatus = Scene.PROCEEDING_GAME;
            scene.needToShoot = true;
        });

        quitButton.setButtonClickCallback(() -> {
        });

        beforeLayer.addButtonUI(startButton);
        beforeLayer.addButtonUI(quitButton);

        beforeLayer.background = true;

        addLayer(beforeLayer);
    }

    private void setProceedingUI() {
        proceedingLayer = new UILayer();
        recordUI = new TextUI("RECORD: " + scene.scoreManager.record,
                new Vector2(scene.frameWidth / 2f - 48, 30), 20);
        scoreUI = new TextUI("SCORE: 0", new Vector2(scene.frameWidth / 2f - 48, 60), 20);

        ButtonUI pauseButton = new ButtonUI("Ⅱ", new Vector2(scene.frameWidth - 50, 15),
                20, 40, 40);
        pauseButton.setButtonClickCallback(() -> scene.gameStatus = Scene.PAUSE_GAME);

        proceedingLayer.addTextUI(recordUI);
        proceedingLayer.addTextUI(scoreUI);
        proceedingLayer.addButtonUI(pauseButton);

        addLayer(proceedingLayer);
    }

    private void setPauseUI() {
        pauseLayer = new UILayer();

        Color backgroundColor = new Color(251, 136, 54);
        Color textColor = Color.WHITE;

        ButtonUI resumeButton = new ButtonUI("resume", new Vector2(GAME_WIDTH / 2f - 90, GAME_HEIGHT / 2f - 40),
                20, 180, 50);

        resumeButton.setButtonClickCallback(() -> scene.gameStatus = Scene.PROCEEDING_GAME);
        resumeButton.setBackgroundColor(backgroundColor);
        resumeButton.setTextColor(textColor);

        pauseLayer.addButtonUI(resumeButton);

        pauseLayer.background = true;

        addLayer(pauseLayer);
    }

    private void addLayer(UILayer layer) {
        layerList.add(layer);
    }

    public void drawUI(Graphics g, int gameWidth, int gameHeight) {
        scoreUI.setText("SCORE: " + scene.scoreManager.score);
        recordUI.setText("RECORD: " + scene.scoreManager.record);

        switch (scene.gameStatus) {
            case Scene.BEFORE_GAME -> {
                beforeLayer.setVisible(true);
                pauseLayer.setVisible(false);
                proceedingLayer.setClickUnable(true);
            }
            case Scene.PROCEEDING_GAME -> {
                beforeLayer.setVisible(false);
                pauseLayer.setVisible(false);
                proceedingLayer.setClickUnable(false);
            }
            case Scene.PAUSE_GAME -> {
                beforeLayer.setVisible(false);
                pauseLayer.setVisible(true);
                proceedingLayer.setClickUnable(true);
            }
            case Scene.END_GAME -> {
                // TODO : ending용 화면 필요
                beforeLayer.setVisible(true);
                pauseLayer.setVisible(false);
                proceedingLayer.setClickUnable(true);
            }
        }

        for (UILayer layer : layerList.stream().filter(layer -> layer.visible).collect(Collectors.toList())) {
            layer.drawUI(g, gameWidth, gameHeight);
        }
    }

    public boolean buttonClickCheck(int pointX, int pointY) {
        for (UILayer layer : layerList.stream().filter(layer -> !layer.clickUnable).collect(Collectors.toList())) {
            return layer.buttonClickCheck(pointX, pointY);
        }
        return false;
    }

    public boolean buttonClickCheck(InputData inputData) {
        if (inputData.type != InputData.InputType.Click) return false;
        Debugger.Print("buttonclickcheck");
        int pointX = inputData.x;
        int pointY = inputData.y;

        for (UILayer layer : layerList.stream().filter(layer -> !layer.clickUnable).collect(Collectors.toList())) {
            return layer.buttonClickCheck(pointX, pointY);
        }
        return false;
    }
}

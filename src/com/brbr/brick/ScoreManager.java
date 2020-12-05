package com.brbr.brick;

import com.brbr.brick.debug.Debugger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ScoreManager {
    public int record;
    public int score;

    public ScoreManager() {
        File file = new File("./score.dat");
        try {
            Scanner sc = new Scanner(file);
            record = sc.nextInt();
            Debugger.Print("" + record);
        } catch (FileNotFoundException e) {
            record = 0;
            e.printStackTrace();
        }
    }

    public void saveRecordScore() {
        if (score < record) return;
        try {
            PrintWriter out = new PrintWriter("./score.dat");
            out.println(record);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateScore(int getScore) {
        this.score += getScore;
        if (record < score) record = score;
    }
}

package com.brbr.brick;

import com.brbr.brick.debug.Debugger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ScoreManager { //최고기록을 파일입출력 하여 저장하고 점수를 가지고 있는 클래
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

    public void saveRecordScore() { // 점수를 score.dat파일에 저장하는 함수
        if (score < record) return;
        try {
            PrintWriter out = new PrintWriter("./score.dat");
            out.println(record);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateScore(int getScore) { // 점수가 갱신될 때 호출되며 점수가 최고기록을 넘었을 경우 최고기록도 같이 올려준다.
        this.score += getScore;
        if (record < score) record = score;
    }
}

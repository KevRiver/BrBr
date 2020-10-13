import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sejong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ArrayList<Ball> ballList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ballList.add(
                    new Ball(
                            100 + (int) (Math.random() * 300),
                            100,
                            50 + (int) (Math.random() * 50),
                            200 + (int) (Math.random() * 100)
                    )
            );
        }
        Canvas canvas = new Canvas(ballList);
        frame.add(canvas);

        frame.setSize(500, 500);
        frame.setVisible(true);

        new Thread(() -> {
            int n = 0;
            while (true) {
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (Ball ball : ballList) {
                    ball.y = (int) (400 - easing((float) Math.abs(ball.range - (n % ball.range * 2)) / ball.range) * ball.length);
                }

                canvas.repaint();
                n++;
            }
        }).start();
    }

    static double easing(float k) {
        return (1 - Math.pow(1 - k, 3));
    }
}

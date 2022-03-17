package javastudy.swing_.exercise;

import javax.swing.*;
import java.awt.*;

/**
 * 模拟窗口抖动
 */
public class Exercise03 extends JFrame {
    // 窗口抖动方法
    private void shake() {
        int x = this.getX(); //获取窗体坐标
        int y = this.getY();
        // 循环和延时来模拟抖动效果，为了让抖动后窗口在还原地，循环次数应该是9的倍数
        // 由于是sleep()方法，抖动完成前不能进行其他操作，可以开一个线程进行抖动
        for (int i = 0; i < 27; i++) {
            try {
                Thread.sleep(10);
                int count = i % 8;
                switch (count) {
                    case 0, 2, 4, 6 -> this.setLocation(x, y);
                    case 1 -> this.setLocation(x-5, y-5);
                    case 3 -> this.setLocation(x+5, y+5);
                    case 5 -> this.setLocation(x-5, y+5);
                    case 7 -> this.setLocation(x+5, y-5);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Exercise03() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(300, 300, 500, 350);
        Container container = getContentPane();
        setLayout(null);

        JButton button = new JButton("窗口抖动");
        button.setBounds(200,130,100,40);
        button.addActionListener(e -> {
            shake();
        });
        container.add(button);
    }

    public static void main(String[] args) {
        new Exercise03().setVisible(true);
    }
}
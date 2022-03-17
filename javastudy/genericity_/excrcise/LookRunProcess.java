package javastudy.genericity_.excrcise;

import javax.swing.*;
import java.awt.*;

/**
 * 匿名类的一个小技巧， 查看方法调用情况
 * 可以为类创建匿名类，重写方法加一些自己需要的功能，比如在调用某个方法的时候输入内容，
 * 通过下面的程序可以看出在鼠标移入、移出、按下、松开等操作都会引起JButton重新调用paintComponent()方法
 */
public class LookRunProcess {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JButton button = new JButton("点击我") {
            private int count = 0;
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                System.out.println("button对象重新调用paintComponent()方法 " + (++count) + " 次");
            }
            public Dimension getPreferredSize() {
                return new Dimension(200, 100);
            }
        };
        frame.add(button);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

package javastudy.swing_.layout;

import javax.swing.*;
import java.awt.*;

/**
 * 换行布局管理器的测试类
 */
public class WrapLayoutTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JScrollPane jsp = new JScrollPane(panel);
        panel.setLayout(new WrapLayout(FlowLayout.LEFT));

        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        panel.add(new JButton("测试"));
        JButton button = new JButton("大小不一样的按钮");
        button.setPreferredSize(new Dimension(100,40));
        panel.add(button);

        frame.setContentPane(jsp);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}

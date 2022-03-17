package javastudy.swing_.component;

import javax.swing.*;
import java.awt.*;

/**
 * 滚动条组件测试
 */
public class JScrollBarTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 400, 500);
        // 创建滚动条
        JScrollBar scrollBar = new JScrollBar();
        // 设置滚动条背景色
        scrollBar.setBackground(Color.GREEN);
        JTextArea textArea = new JTextArea();
        // 设置鼠标滑动事件
        frame.addMouseWheelListener(e -> {
            if (e.getWheelRotation() > 0)
                textArea.append("滚动向下滑动...\n");
            else
                textArea.append("滚动向上滑动...\n");
            // 移动滚动条
            scrollBar.setValue(scrollBar.getValue() + e.getWheelRotation());
        });

        frame.add(scrollBar, BorderLayout.EAST);
        frame.add(textArea);
        frame.setVisible(true);
    }
}

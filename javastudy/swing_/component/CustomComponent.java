package javastudy.swing_.component;

import javax.swing.*;
import java.awt.*;

/**
 * 自定义组件
 */
public class CustomComponent {
    public static void main(String[] args) {
        /*TODO 组件JComponent
           1. 常用组件和面板都是继承的JComponent，因此自定义组件也可以继承JComponent
           2. 只要窗口重新绘制，事件处理器就会通知组件，执行所有组件的paintComponent()方法，但是不要自己调用，如果要强制重新绘制屏幕，需要调
           用repaint()方法
           3. 什么动作会触发这个自动相应？窗口大小改变，极小化窗口后又恢复，窗口被另一个窗口覆盖后又重新露出来
        */
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            MyComponent component = new MyComponent();
            frame.add(component);
            frame.pack(); //会根据组件的首选大小计算窗口的大小，大小因getPreferredSize()返回结果不同而不同
            frame.setVisible(true);
        });
    }

    private static class MyComponent extends JComponent {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g.drawString("Not a Hello", 100, 100);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 300);
        }
    }
}

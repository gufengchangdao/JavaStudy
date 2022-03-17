package javastudy.swing_.frame;

import javax.swing.*;
import java.awt.*;

/**
 * Swing的窗体方法演示
 */
public class JFrameTest {
    public static void main(String[] args) {
        /* TODO JFrame窗体
            1. 顶层窗体：没有包含在其他窗口中的窗口 ---> 窗体
            2. JFrame的修饰部件(按钮、标题栏、图标等)是由用户的窗口系统绘制，而不是swing绘制
            3. JFrame有四层窗格，分别为
                玻璃窗格
                内容窗格
                层级窗格
                根窗格
               其中添加到窗体中的所有组件都会自动添加到内容窗格中
         */

        // Swing组件都由事件分派线程配置，这是控制线程将鼠标点击和按钮等事件传递给用户接口组件，是单线程的设计，因此Swing线程不安全，
        // invokeLater()效果是允许事件派发线程调用另一个线程中的任意一个代码块，更安全一些
        EventQueue.invokeLater(() -> {
            MyFrame frame = new MyFrame();
            // 默认关闭窗体只是将窗体隐藏起来，而程序并没有终结，这是设置让程序退出
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    private static class MyFrame extends JFrame {
        public MyFrame() {
            // setSize(400,400);
            // setLocation(100,100);
            // 设置窗体图标
            // setIconImage();
            setBounds(100, 100, 400, 400);
            setTitle("测试窗体");
            // 设置是否允许用户改变窗体大小
            setResizable(false);
            // isResizable(); //boolean型的属性方法开头字母是is
        }
    }
}

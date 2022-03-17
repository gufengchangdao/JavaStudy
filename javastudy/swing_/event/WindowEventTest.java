package javastudy.swing_.event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 添加窗口监听事件
 */
public class WindowEventTest extends JFrame {
    public WindowEventTest() throws HeadlessException {
        setBounds(300,300,500,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("窗口打开");
            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("窗口即将关闭");
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("窗口关闭后");
            }

            @Override
            public void windowIconified(WindowEvent e) {
                System.out.println("窗口最小化");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                System.out.println("出口取消最小化");
            }

            @Override
            public void windowActivated(WindowEvent e) {
                System.out.println("激活窗口");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                System.out.println("窗口未激活");
            }
        });
        addWindowFocusListener(new WindowAdapter() {
            public void windowLostFocus(WindowEvent e) {
                System.out.println("窗口失去焦点");
            }
        });
    }

    public static void main(String[] args) {
        new WindowEventTest().setVisible(true);
    }
}

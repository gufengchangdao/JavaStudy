package javastudy.swing_.event;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * 菜单事件
 */
public class MenuEventTest extends JFrame {
    public MenuEventTest() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("文件");
        menuBar.add(menu);

        JMenuItem[] items = new JMenuItem[]{new JMenuItem("打开文件"), new JMenuItem("编辑文件"), new JMenuItem("删除文件")};
        for (JMenuItem item : items) {
            menu.add(item);
        }

        // 创建菜单的匿名监视器对象
        MenuListener listener = new MenuListener() {
            // 菜单被选择但尚未打开
            public void menuSelected(MenuEvent e) {
                System.out.println("菜单被点击后打开");
                items[0].setEnabled(false); //打开前先让第一个菜单项不可选，这样可以防点击但防不了键盘加速器(快捷键)
            }

            // 菜单被取消选择并且已经关闭后调用
            public void menuDeselected(MenuEvent e) {
                System.out.println("菜单关闭");
            }

            // 菜单被取消时调用
            public void menuCanceled(MenuEvent e) {
                System.out.println("3");
            }
        };

        menu.addMenuListener(listener);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MenuEventTest().setVisible(true));
    }
}

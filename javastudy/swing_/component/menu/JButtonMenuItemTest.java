package javastudy.swing_.component.menu;

import javax.swing.*;
import java.awt.*;

/**
 * 复选框和单选按钮菜单项方法演示
 */
public class JButtonMenuItemTest extends JFrame {
    public JButtonMenuItemTest() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("浏览");
        menuBar.add(menu);

        // 复选框菜单项，作为一个菜单项添加进菜单里面就可以了
        JCheckBoxMenuItem[] checkBoxMenuItems = new JCheckBoxMenuItem[]{new JCheckBoxMenuItem("电视剧"),
                new JCheckBoxMenuItem("动漫",true), new JCheckBoxMenuItem("小说")};
        for (JCheckBoxMenuItem item : checkBoxMenuItems) {
            menu.add(item);
        }
        menu.addSeparator();

        // 单选框菜单项，也需要用到ButtonGroup
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButtonMenuItem[] radioButtonMenuItems = {new JRadioButtonMenuItem("学生"),
                new JRadioButtonMenuItem("教师"), new JRadioButtonMenuItem("军人")};
        for (JRadioButtonMenuItem item : radioButtonMenuItems) {
            // 在这里添加动作命令字符串的话，还能通过按钮组直接得到菜单项
            menu.add(item);
            buttonGroup.add(item);
        }

        // 设置菜单项的点击事件
        for (JCheckBoxMenuItem item : checkBoxMenuItems) {
            item.addActionListener(e -> {
                if (item.isSelected()) //复选框取消的时候不打印
                    System.out.println("选中了" + item.getText());
            });
        }
        for (JRadioButtonMenuItem item : radioButtonMenuItems) {
            item.addActionListener(e -> {
                if (!item.isSelected()) //已经选中的时候不打印
                    System.out.println("选中了" + item.getText());
            });
        }
    }

    public static void main(String[] args) {
        new JButtonMenuItemTest().setVisible(true);
    }
}

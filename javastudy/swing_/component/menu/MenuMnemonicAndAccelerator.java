package javastudy.swing_.component.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * 菜单助记符和加速器方法演示
 */
public class MenuMnemonicAndAccelerator extends JFrame {
    public MenuMnemonicAndAccelerator() throws HeadlessException {
        setBounds(100, 100, 500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("食谱");
        menuBar.add(menu);
        /* TODO 助记符
            1. 助记符就是你按下相应的按键，对应的菜单项(或菜单)就会选择它
            2. 设置了助记符后会匹配对应的菜单项，把菜单项的第一个跟助记符匹配的字符加上下划线，当然，就算没有匹配的助记符也会起作用
            3. 助记符不区分大小写
                键盘加速器
            1. 键盘加速器就是按下快捷键可以直接选中某个菜单项，直接触发与菜单关联的动作事件，菜单都不用打开，比如 CTRL+S 一般都是保存
            总结：就是设置菜单的快捷键的
         */
        // TODO 为菜单设置助记符
        menu.setMnemonic('E'); //按 ALT+E 打开菜单

        // TODO 为菜单项设置助记符
        JMenuItem[] menuItems = new JMenuItem[]{new JMenuItem("Fish", 'f'),
                new JMenuItem("Apple", 'A'), new JMenuItem("Banana", 'B'),
                new JMenuItem("Delicious orange", 'O')};
        // 设置按钮文本中加下划线字符的索引
        menuItems[3].setDisplayedMnemonicIndex(10);
        // 添加事件
        ActionListener listener = e -> System.out.println(((JMenuItem) e.getSource()).getText());

        // TODO 为菜单项添加键盘加速器
        menuItems[0].setAccelerator(KeyStroke.getKeyStroke("ctrl F")); //这个ctrl+F还会提示在这个菜单项的右边

        for (JMenuItem item : menuItems) {
            menu.add(item);
            item.addActionListener(listener);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MenuMnemonicAndAccelerator().setVisible(true));
    }
}

package javastudy.swing_.component.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 菜单组件功能演示
 */
public class JMenuTest extends JFrame {
    public JMenuTest() throws HeadlessException {
        setBounds(300,300,500,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        Container container = getContentPane();

        // TODO 1. 创建一个菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        // TODO 2. 创建一个菜单对象
        JMenu menu1 = new JMenu("文件"); //菜单条目的大小是会根据文字的大小调整，为了让菜单和他的条目一样宽，这里多几个空格
        JMenu menu2 = new JMenu("编辑");
        // 添加菜单项
        menuBar.add(menu1);
        menuBar.add(menu2);
        // TODO 3. 向菜单对象中添加菜单项、分隔符和子菜单
        menu1.add(new JMenuItem("新建"));
        // 创建一个菜单项添加进去并返回该菜单项
        JMenuItem openItem = menu1.add("打开");
        // 传入一个动作创建菜单项添加进去并返回
        AbstractAction action = new AbstractAction("视图") { //菜单项名就写在这里
            public void actionPerformed(ActionEvent e) {
                System.out.println("视图的菜单项事件触发");
            }
        };
        JMenuItem viewItem = menu1.add(action);
        // 在指定位置插入菜单项
        menu1.insert(new JMenuItem("打开最近"),3);
        // 添加一个分隔符
        menu1.addSeparator();
        menu1.insertSeparator(0); //指定位置
        // 删除菜单项
        menu1.remove(0);
        // menu1.remove(openItem);

        // 让菜单和他的条目一样宽，可以加一些空格或者设置首选大小，条目的大小还要加上一些距离
        // menu1.setPreferredSize(new Dimension((int) menuItem.getPreferredSize().getWidth()+3,(int) menu1.getPreferredSize().getHeight()));

        // 要想使用多级菜单，就可以把菜单作为菜单项添加进菜单，像menu1.add(menu2)
    }

    public static void main(String[] args) {
        new JMenuTest().setVisible(true);
    }
}

package javastudy.swing_.exercise;

import javastudy.utilities.ImageUtils;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 关于菜单的练习
 */
public class MenuExercise extends JFrame {
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu viewMenu;
    private JTextArea textArea;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MenuExercise().setVisible(true));
    }

    public MenuExercise() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        textArea = new JTextArea();
        fileMenuInit();
        editMenuInit();
        viewMenuInit();
        popupMenuInit();
        menuStackPrint();
    }

    /**
     * 文件菜单部分
     * 主要练习助记符和键盘加速器(快捷键)
     */
    private void fileMenuInit() {
        fileMenu = new JMenu("文件(F)");
        fileMenu.setMnemonic('F');

        JMenuItem[] menuItems = new JMenuItem[]{new JMenuItem("新建(N)", 'N'), new JMenuItem("打开(O)", 'O'),
                new JMenuItem("打开最近(R)", 'R'), new JMenuItem("关闭项目(J)", 'J'),
                new JMenuItem("设置(T)", 'T')};

        MyActionListener itemListener = new MyActionListener();

        for (int i = 0; i < 4; i++) {
            fileMenu.add(menuItems[i]);
            menuItems[i].addActionListener(itemListener);
        }
        fileMenu.addSeparator();
        fileMenu.add(menuItems[4]);
        menuItems[4].setAccelerator(KeyStroke.getKeyStroke("ctrl alt F"));
        menuItems[4].addActionListener(itemListener);

        menuBar.add(fileMenu);
    }

    /**
     * 编辑菜单部分
     * 主要练习菜单图标
     */
    private void editMenuInit() {
        editMenu = new JMenu("编辑(F)");
        editMenu.setMnemonic('F');

        JMenuItem[] menuItems = new JMenuItem[]{new JMenuItem("取消粘贴(U)", 'U'),
                new JMenuItem("重做(R)", 'R'), new JMenuItem("剪切(T)", 'T'),
                new JMenuItem("复制(C)", 'C'), new JMenuItem("复制路径/引用..."),
                new JMenuItem("粘贴")};

        // 设置菜单项的图标，这里用了之前写的工具类，用于将图标进行缩放
        menuItems[0].setIcon(ImageUtils.imageIconScale(new ImageIcon("images/icon/app.png"), 0.1));
        menuItems[1].setIcon(ImageUtils.imageIconScale(new ImageIcon("images/icon/editor.png"), 0.1));
        menuItems[2].setIcon(ImageUtils.imageIconScale(new ImageIcon("images/icon/effect.png"), 0.1));
        menuItems[3].setIcon(ImageUtils.imageIconScale(new ImageIcon("images/icon/copy.png"), 0.1));
        menuItems[4].setIcon(ImageUtils.imageIconScale(new ImageIcon("images/icon/error.png"), 0.1));
        menuItems[5].setIcon(ImageUtils.imageIconScale(new ImageIcon("images/icon/app.png"), 0.1));

        MyActionListener itemListener = new MyActionListener();
        for (JMenuItem item : menuItems) {
            item.addActionListener(itemListener);
            editMenu.add(item);
        }

        // 取消粘贴和重做同一时间只能选择一个，初始默认取消粘贴可选而重做不可选
        menuItems[1].setEnabled(false);
        menuItems[0].addActionListener(e -> {
            menuItems[0].setEnabled(false);
            menuItems[1].setEnabled(true);
        });
        menuItems[1].addActionListener(e -> {
            menuItems[0].setEnabled(true);
            menuItems[1].setEnabled(false);
        });

        menuBar.add(editMenu);
    }

    /**
     * 视图菜单部分
     * 主要练习多级菜单和菜单按钮
     */
    private void viewMenuInit() {
        viewMenu = new JMenu("视图(V)");
        viewMenu.setMnemonic('V');

        JMenuItem[] menuItems = new JMenuItem[]{new JMenuItem("工具窗口(T)", 'T'),
                new JMenuItem("快速定义(K)", 'K'), new JMenuItem("显示同级"),
                new JMenuItem("快速类型定义")};
        menuItems[1].setAccelerator(KeyStroke.getKeyStroke("ctrl shift I"));

        MyActionListener itemListener = new MyActionListener();
        for (JMenuItem item : menuItems) {
            item.addActionListener(itemListener);
        }

        JMenu facadeMenu = new JMenu("外观(A)");
        JRadioButtonMenuItem[] radioButtonMenuItems = new JRadioButtonMenuItem[]{new JRadioButtonMenuItem("进入演示模式"),
                new JRadioButtonMenuItem("进入免打扰模式"), new JRadioButtonMenuItem("进入全屏"),
                new JRadioButtonMenuItem("进入zen模式")};
        ButtonGroup buttonGroup = new ButtonGroup();

        for (JRadioButtonMenuItem item : radioButtonMenuItems) {
            facadeMenu.add(item);
            buttonGroup.add(item);
            item.addActionListener(itemListener);
        }
        facadeMenu.addSeparator();

        JCheckBoxMenuItem[] checkBoxMenuItems = new JCheckBoxMenuItem[]{new JCheckBoxMenuItem("主菜单", true),
                new JCheckBoxMenuItem("工具栏"), new JCheckBoxMenuItem("导航栏", true), new JCheckBoxMenuItem("工具窗口"),
                new JCheckBoxMenuItem("状态栏", true)};

        for (JCheckBoxMenuItem item : checkBoxMenuItems) {
            facadeMenu.add(item);
            item.addActionListener(itemListener);
        }

        viewMenu.add(menuItems[0]);
        viewMenu.add(facadeMenu);
        viewMenu.add(menuItems[1]);
        viewMenu.add(menuItems[2]);
        viewMenu.add(menuItems[3]);

        menuBar.add(viewMenu);
    }

    /**
     * 右键快捷菜单部分
     * 主要练习弹出菜单
     */
    private void popupMenuInit() {
        JPopupMenu popupMenu = new JPopupMenu();
        textArea.setComponentPopupMenu(popupMenu); //给文本区设置弹出菜单，不要给滚动面板设置
        add(new JScrollPane(textArea));

        JMenuItem[] menuItems = new JMenuItem[]{new JMenuItem("翻译文档"), new JMenuItem("显示上下文操作"), new JMenuItem("粘贴"),
                new JMenuItem("重构"), new JMenuItem("折叠"), new JMenuItem("分析")};
        menuItems[1].setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
        menuItems[2].setAccelerator(KeyStroke.getKeyStroke("ctrl V"));

        MyActionListener listener = new MyActionListener();
        for (JMenuItem item : menuItems) {
            item.addActionListener(listener);
            popupMenu.add(item);
        }
    }

    /**
     * 菜单点击部分
     * 主要练习菜单的点击事件
     */
    private void menuStackPrint() {
        MyMenuListener listener = new MyMenuListener();
        fileMenu.addMenuListener(listener);
        editMenu.addMenuListener(listener);
        viewMenu.addMenuListener(listener);
    }

    private class MyMenuListener implements MenuListener {

        @Override
        public void menuSelected(MenuEvent e) {
            JMenu menu = (JMenu) e.getSource();
            textArea.append("你打开了 " + menu.getText() + " 菜单\n");
        }

        @Override
        public void menuDeselected(MenuEvent e) {
            JMenu menu = (JMenu) e.getSource();
            textArea.append("你关掉了 " + menu.getText() + " 菜单\n");
        }

        @Override
        public void menuCanceled(MenuEvent e) {

        }
    }

    // 菜单项、按钮点击监听器
    private class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                JMenuItem item = (JMenuItem) e.getSource();
                textArea.append(item.getText() + " 被选中\n");
            } catch (ClassCastException ex) {
                ex.printStackTrace();
            }
        }
    }
}

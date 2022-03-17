package javastudy.swing_.component.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 在菜单中显示图标
 */
public class JMenuIcon {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu menu = new JMenu("文件");
        menuBar.add(menu);

        ImageIcon imageIcon1 = change(new ImageIcon("images/icon/app.png"), 0.1);
        ImageIcon imageIcon2 = change(new ImageIcon("images/icon/effect.png"), 0.1);
        ImageIcon imageIcon3 = change(new ImageIcon("images/icon/copy.png"), 0.1);

        // TODO 1. 使用JMenuItem()构造器
        JMenuItem menuItem = new JMenuItem("新建", imageIcon1);
        menu.add(menuItem);

        JMenuItem menuItem1 = new JMenuItem("打开", imageIcon2);
        // 设置文字位置在左。默认为图标在左，文字在右
        menuItem1.setHorizontalTextPosition(SwingConstants.LEFT);
        menu.add(menuItem1);

        // TODO 2. 使用动作构建菜单项
        AbstractAction action = new AbstractAction("复制", imageIcon3) {
            public void actionPerformed(ActionEvent e) {
            }
        };
        JMenuItem menuItem2 = menu.add(action);

        frame.setVisible(true);
    }

    // 用来对图标对象进行缩放，i 为放缩的倍数
    private static ImageIcon change(ImageIcon image, double i) {
        int width = (int) (image.getIconWidth() * i);
        int height = (int) (image.getIconHeight() * i);
        Image img = image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(img);
    }
}

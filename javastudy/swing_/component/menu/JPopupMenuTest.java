package javastudy.swing_.component.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 弹出菜单方法演示
 */
public class JPopupMenuTest extends JFrame {
    public JPopupMenuTest() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        setLayout(new FlowLayout());

        // 创建JPopupMenu对象，填装菜单项就可以了
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem("复制视频地址"));
        popupMenu.add(new JMenuItem("视频色彩调整"));
        popupMenu.add(new JMenuItem("视频音效调节"));
        popupMenu.add(new JMenuItem("更新历史"));
        popupMenu.add(new JMenuItem("视频统计信息"));

        // // TODO 点击按钮弹出菜单
        JButton button = new JButton("弹出菜单");
        button.addActionListener(e -> {
            JButton source = (JButton) e.getSource();
            // TODO 弹出菜单需要使用show()，show需要指定父组件和弹出位置
            popupMenu.show(this, source.getX(), source.getY());
        });
        add(button);

        // TODO 鼠标右键单机弹出菜单
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3)
                    popupMenu.show(JPopupMenuTest.this, e.getX(), e.getY());
            }
        });

        // TODO 如果是在一个组件(或者面板)中，可以直接调用setComponentPopupMenu()实现右键弹出菜单
        button.setComponentPopupMenu(popupMenu); // 现在在按钮上右键也能弹出菜单了
        // JPanel panel = new JPanel();
        // panel.setComponentPopupMenu(popupMenu);
        // add(panel);

        // System.out.println(button.getInheritsPopupMenu());
        // button.setInheritsPopupMenu(true);
        // 获取或设置InheritsPopupMenu特性，如果这个特性已经设置或该组件的弹出菜单为null，就使用其父组件的弹出菜单
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new JPopupMenuTest().setVisible(true));
    }
}

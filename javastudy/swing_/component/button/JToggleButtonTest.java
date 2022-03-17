package javastudy.swing_.component.button;

import javax.swing.*;
import java.awt.*;

/**
 * 切换按钮，该按钮不会弹起，就像一个状态转换开关一样
 * 也可以配合ButtonGroup按钮组来实现一个菜单切换的功能
 */
public class JToggleButtonTest extends JFrame {
    public JToggleButtonTest() throws HeadlessException {
        setBounds(300, 300, 500, 350);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = getContentPane();

        JToggleButton button = new JToggleButton("转换开关");
        container.add(button);

        // 默认是没选中
        // button.setSelected(true);
        button.addActionListener(e -> { //不管选没选中，点击就会更新状态并触发事件
            if (button.isSelected()) {
                System.out.println("更改为选中状态");
            }
            if (!button.isSelected()) {
                System.out.println("取消选中状态");
            }
        });
    }

    public static void main(String[] args) {
        new JToggleButtonTest().setVisible(true);
    }
}

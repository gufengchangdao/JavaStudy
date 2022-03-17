package javastudy.swing_.component.button;

import javax.swing.*;
import java.awt.*;

/**
 * 创建复选框，打印用户所选内容
 */
public class JCheckBoxTest extends JFrame {
    public JCheckBoxTest() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100,100,170,110);
        setLayout(new FlowLayout());

        JCheckBox checkBox1 = new JCheckBox("1",true);
        checkBox1.setToolTipText("快选我ヾ|≧_≦|〃");
        JCheckBox checkBox2 = new JCheckBox("2");
        JCheckBox checkBox3 = new JCheckBox("3",true);

        Container container = getContentPane();
        container.add(checkBox1);
        container.add(checkBox2);
        container.add(checkBox3);

        JButton button = new JButton("打印");
        button.addActionListener(e->{
            System.out.println(checkBox1.getText()+" 按钮选中状态 "+checkBox1.isSelected());
            System.out.println(checkBox2.getText()+" 按钮选中状态 "+checkBox2.isSelected());
            System.out.println(checkBox3.getText()+" 按钮选中状态 "+checkBox3.isSelected());
        });
        container.add(button);

    }

    public static void main(String[] args) {
        new JCheckBoxTest().setVisible(true);
    }
}

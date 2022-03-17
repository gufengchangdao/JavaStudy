package javastudy.swing_.component.border;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * 创建一个工具类，利用空边框模仿padding效果
 */
public class LayoutBox {

    public static void setPadding(JComponent component, int size) {
        setPadding(component, size, size, size, size);
    }

    public static void setPadding(JComponent component, int top, int left, int bottom, int right) {
        Border newBorder = BorderFactory.createEmptyBorder(top, left, bottom, right);
        Border border = component.getBorder();
        if (border != null)
            newBorder = BorderFactory.createCompoundBorder(border, newBorder);
        component.setBorder(newBorder);
    }
}
class Test extends JFrame{
    public Test() {
        setBounds(300,300,500,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton button = new JButton("我是按钮");
        JButton button1 = new JButton("我是按钮");
        LayoutBox.setPadding(button,5);
        getContentPane().add(button);
        getContentPane().add(button1);
    }

    public static void main(String[] args) {
        new Test().setVisible(true);
    }
}
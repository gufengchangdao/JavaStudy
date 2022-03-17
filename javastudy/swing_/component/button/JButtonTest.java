/*
JButton
1. JButton按钮的边框大小默认为[top=5,left=17,bottom=5,right=17]，也就是说，除了文字所在的矩形，外面一圈都是边框，可以用getInsets()查看

*/
package javastudy.swing_.component.button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * 使用JButton类创建不同样式的按钮
 */
public class JButtonTest extends JFrame {
    public JButtonTest() throws HeadlessException {
        Icon icon=new ImageIcon("src/javastudy/swing_/laugh.png");
        Container container = getContentPane();
        setLayout(new GridLayout(3,2,5,5));
        JButton[] buttons = new JButton[6];
        for (int i = 0; i < 6; i++) {
            buttons[i]=new JButton();
            container.add(buttons[i]);
        }
        buttons[0].setText("不可用");
        // setEnabled()设置按钮是否可用
        buttons[0].setEnabled(false);
        buttons[1].setText("有背景色");
        // setBackground()设置按钮的背景颜色
        buttons[1].setBackground(Color.YELLOW);
        buttons[2].setText("无边框");
        // setBorderPainted()设置是否绘制边框，默认显示边框并且为黑色
        buttons[2].setBorderPainted(false);
        buttons[3].setText("有边框");
        // 利用BorderFactory工厂类设置边框的颜色
        buttons[3].setBorder(BorderFactory.createLineBorder(Color.RED));
        buttons[4].setIcon(icon);
        // 为按钮设置提示文字
        buttons[4].setToolTipText("图片按钮");
        buttons[5].setText("可点击");
        // 为按钮添加点击事件
        buttons[5].addActionListener(e->{
            JOptionPane.showMessageDialog(this,"点击按钮"); //点击后弹出通知框
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(200,200,400,200);
        setTitle("创建不同的按钮");
    }

    public static void main(String[] args) {
        new JButtonTest().setVisible(true);
    }
}

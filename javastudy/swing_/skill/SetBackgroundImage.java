package javastudy.swing_.skill;

import javax.swing.*;
import java.awt.*;

/**
 * 设置背景图片的方法
 */
public class SetBackgroundImage extends JFrame {
    public SetBackgroundImage() {
        setBounds(300,300,500,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        Container container = getContentPane();

        JButton button = new JButton("按钮在背景图片上面");
        container.add(button);

        // JFrame里，由根面板，玻璃面板(ContentPane)，分层面(LayeredPane)板结合而成。玻璃面板就像桌子上的一块玻璃，桌子就是分层面板
        // 1. 将内容面板初始化为JPanel类型，然后才可以将这个内容面板设置为透明
        ((JPanel) container).setOpaque(false);
        JLabel background = new JLabel(new ImageIcon("images/4.jpg"));
        // container.add(background); //组件不是添加到内容面板中
        // 2. 为组件设置位置和大小
        background.setBounds(0,0,getWidth(),getHeight());
        // 3. 把图标组件添加到分层面板中
        getLayeredPane().add(background);

        // TODO 还可以对原图片进行缩放
        // imageIcon.setImage(imageIcon.getImage().getScaledInstance(WIDTH,HEIGHT,Image.SCALE_DEFAULT));
        // background=new JLabel(imageIcon);
    }

    public static void main(String[] args) {
        new SetBackgroundImage().setVisible(true);
    }
}

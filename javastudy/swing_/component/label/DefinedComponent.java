package javastudy.swing_.component.label;

import javax.swing.*;
import java.awt.*;

/**
 * 自定义一个控件(好像就是java的绘图吧)
 */
public class DefinedComponent extends JPanel {  //需要继承JPanel类
    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();         //默认是50
        int height = getHeight();       //默认是50
        g.setColor(new Color(255,0,0));
        g.fillRect(0,0,width,height);
    }
}

class TestDefined extends JFrame{
    public TestDefined() {
        setBounds(300,300,500,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        Container container = getContentPane();

        DefinedComponent block = new DefinedComponent();
        block.setPreferredSize(new Dimension(50,50));
        container.add(block);

    }

    public static void main(String[] args) {
        new TestDefined().setVisible(true);
    }
}
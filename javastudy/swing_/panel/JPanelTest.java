/*
JPanel面板
1. JPanel面板默认使用的是间隔为5，居中对齐的流布局

*/
package javastudy.swing_.panel;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel面板的使用
 * 在主容器中使用网格布局将窗口分成四份，分别放置四个面板，设置不同布局，并添加组件
 */
public class JPanelTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("一个窗体使用4种布局风格的面板");
        frame.setLayout(new GridLayout(2,2,10,10)); //主容器使用网格布局
        // TODO 面板是一个容器，被用于容纳其他组件，但面板必须被添加进其他容器中
        // 四种面板使用不同的布局管理器
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel2 = new JPanel(new BorderLayout());
        JPanel panel3 = new JPanel(null);
        JPanel panel4 = new JPanel(new GridLayout(0,4));
        // 给每个面板添加边框和标题，使用BorderFactory工厂类生成带标题的边框对象(很像html使用了fieldset和legend)
        panel1.setBorder(BorderFactory.createTitledBorder("面板1使用流布局"));
        panel2.setBorder(BorderFactory.createTitledBorder("面板2使用边框布局"));
        panel3.setBorder(BorderFactory.createTitledBorder("面板3使用绝对布局"));
        panel4.setBorder(BorderFactory.createTitledBorder("面板4使用网格布局"));
        // 分别给四个面板添加组件
        for (int i = 0; i < 4; i++) {
            panel1.add(new JButton("按钮"+i));
        }
        panel2.add(new JButton("按钮5"),BorderLayout.NORTH);
        panel2.add(new JButton("按钮6"),BorderLayout.SOUTH);
        JButton button7 = new JButton("按钮7");
        button7.setBounds(10,10,100,80);
        panel3.add(button7);
        for (int i = 8; i <13; i++) {
            panel4.add(new JButton("按钮"+i));
        }
        // 向容器添加面板
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.setBounds(200,300,600,350);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // frame.dispose(); //释放面板及组件的资源，在切换到其他面板时很有用
        // frame.validate(); //在修改了组件后需要调用这个方法重新验证操作
    }
}

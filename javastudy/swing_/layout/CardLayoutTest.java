package javastudy.swing_.layout;

import javax.swing.*;
import java.awt.*;

/**
 * 使用CardLayout卡片式布局，
 * 保证同时只有一个面板展示在窗体中，但又可以流畅地切换成其他面板(因为提前加载好了)
 */
public class CardLayoutTest extends JFrame {
    // 控制面板切换的按钮，使用的是切换按钮，选中就表示切换到对应的那个布局
    JToggleButton button1 = new JToggleButton("按钮1");
    JToggleButton button2 = new JToggleButton("按钮2");
    JToggleButton button3 = new JToggleButton("按钮3");
    JToggleButton button4 = new JToggleButton("按钮4");
    // 放入应用卡片式布局的四个面板
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    public CardLayoutTest() throws HeadlessException {
        setBounds(300, 300, 500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = getContentPane();

        JPanel panel = new JPanel();
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        ButtonGroup buttonGroup = new ButtonGroup(); //创建按钮组，使得同一时刻只能选中一个按钮
        buttonGroup.add(button1);
        buttonGroup.add(button2);
        buttonGroup.add(button3);
        buttonGroup.add(button4);
        container.add(panel, BorderLayout.SOUTH);

        panel1.add(new JButton("我是面板1"));
        panel2.add(new JButton("我是面板2"));
        panel3.add(new JButton("我是面板3"));
        panel4.add(new JButton("我是面板4"));
        CardLayout cardLayout = new CardLayout(); // 创建卡片式布局对象
        JPanel cardLayoutPanel = new JPanel(cardLayout); //用一个新面板设置为该布局
        cardLayoutPanel.add(panel1,"panel1");
        cardLayoutPanel.add(panel2,"panel2");
        cardLayoutPanel.add(panel3,"panel3");
        cardLayoutPanel.add(panel4,"panel4");
        container.add(cardLayoutPanel,BorderLayout.CENTER);

        // 给按钮设置点击事件，用于切换对应的面板
        button1.addActionListener(e -> {
            cardLayout.show(cardLayoutPanel,"panel1");
        });
        button2.addActionListener(e -> {
            cardLayout.show(cardLayoutPanel,"panel2");
        });
        button3.addActionListener(e -> {
            cardLayout.show(cardLayoutPanel,"panel3");
        });
        button4.addActionListener(e -> {
            cardLayout.show(cardLayoutPanel,"panel4");
        });
    }

    public static void main(String[] args) {
        new CardLayoutTest().setVisible(true);
    }
}

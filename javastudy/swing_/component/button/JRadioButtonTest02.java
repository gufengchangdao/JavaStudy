package javastudy.swing_.component.button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * 演示通过按钮组来获取单选按钮信息
 */
public class JRadioButtonTest02 {
    public static void main(String[] args) {
        /* TODO 通过按钮组来获取单选按钮信息方法
            1.  原理：
                ButtonGroup的getSelection()会返回与那个按钮关联的模型ButtonModel引用，ButtonModel可以使用getActionCommand()获取动作命令
                又因为单选按钮的动作命令是它的Text内容，而对应的模型的动作命令是null，在对按钮使用setActionCommand()时也会设置模型的动作命令
            2. 方法：
                为每个按钮设置动作命令，然后用buttonGroup.getSelection().getActionCommand()获取模型的动作命令即可
        */
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setBounds(100, 100, 500, 400);
            frame.setLayout(new FlowLayout());

            JRadioButton button01 = new JRadioButton("吃饭");
            JRadioButton button02 = new JRadioButton("睡觉");
            JRadioButton button03 = new JRadioButton("打游戏");

            // 手动为每个按钮的动作命令赋值，才会同时设置模型的动作命令值
            button01.setActionCommand("我想吃饭");
            button02.setActionCommand("我想睡觉");
            button03.setActionCommand("我想打游戏");

            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(button01);
            buttonGroup.add(button02);
            buttonGroup.add(button03);

            ActionListener listener = e -> {
                System.out.println(buttonGroup.getSelection().getActionCommand()); //获取的是自己设置的动作命令字符串
            };

            button01.addActionListener(listener);
            button02.addActionListener(listener);
            button03.addActionListener(listener);

            frame.add(button01);
            frame.add(button02);
            frame.add(button03);
            frame.setVisible(true);
        });
    }
}

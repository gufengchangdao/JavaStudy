package javastudy.swing_.component.button;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

/**
 * 做一个性别选择的选项框，并设置提交按钮，点击后会输出选择内容
 */
public class JRadioButtonTest extends JFrame {
    public JRadioButtonTest() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(500,400,240,165);
        Container container = getContentPane();
        container.setLayout(null);                                              //绝对布局
        JLabel tip = new JLabel("请选择性别：");
        tip.setBounds(5,5,120,15);
        container.add(tip);

        //TODO JRadioButton对象，单选框也支持默认选取、标签、图标
        JRadioButton radioMan = new JRadioButton("男");
        // radioMan.setSelected(true);
        radioMan.setBounds(40,30,75,22);
        container.add(radioMan);
        JRadioButton radioWoman = new JRadioButton("女",true);     //默认选取
        radioWoman.setBounds(120,30,75,22);
        container.add(radioWoman);

        // TODO 创建按钮组，按钮组的功能就是按钮组中的按钮只能选择一个，还提供返回按钮数量和按钮集合的方法，但是不能直接读取选取的按钮不好
        ButtonGroup sexChoice = new ButtonGroup();
        sexChoice.add(radioMan);                                              //把单选按钮添加进按钮组中
        sexChoice.add(radioWoman);

        JButton submit = new JButton("提交");
        submit.setBounds(70,70,70,30);
        container.add(submit);
        // TODO 查询选择框选择的内容，虽然就两个按钮，用不着遍历这么麻烦
        submit.addActionListener(e->{
            Enumeration<AbstractButton> buttons = sexChoice.getElements();   //返回一个按钮组中所有按钮的集合，遍历所有按钮来查找
            while (buttons.hasMoreElements()){
                JRadioButton button=(JRadioButton)buttons.nextElement();
                if (button.isSelected()){                                      //判断该按钮是否处于选取状态
                    System.out.println("你选择的是 "+button.getText());          //打印按钮值
                }
            }
        });

    }

    public static void main(String[] args) {
        new JRadioButtonTest().setVisible(true);

    }
}

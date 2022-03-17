package javastudy.swing_.component.text;

import javax.swing.*;
import java.awt.*;

/**
 * 使用JTextField类创建文本框，显示默认文本内容并并清除
 */
public class JTextFieldTest extends JFrame {
    public JTextFieldTest(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(400,300,250,110);
        Container container = getContentPane();
        setLayout(new FlowLayout());

        // 添加文本
        JTextField text = new JTextField("点击按钮清除文本内容");
        // JTextField text = new JTextField("点击按钮清除文本内容", 20);
        text.setColumns(20);                                            //设置文本的列，一列就是当前字体一个字符的宽度
        text.setFont(new Font("宋体",Font.PLAIN,20));         //设置字体类型和大小
        // text.setEditable(false);                                     //设置内容不可编辑
        // text.setEnabled(false);                                      //设置不可选取
        // text.setHorizontalAlignment(SwingConstants.RIGHT);           //设置文字水平对齐方式
        text.addActionListener(e->{                                     //为文本框设置回车事件
            text.setText("文本框回车触发事件");
        });
        container.add(text);

        // 添加点击按钮
        JButton button = new JButton("清除");
        button.addActionListener(e -> {
            System.out.println(text.getText());                         //打印文本框内容
            text.setText("");
            text.requestFocus();                                        //焦点回到文本框
        });
        container.add(button);

        // 重新设置文本域的列数
        // text.setColumns(40);
        // 改变的文本框的大小后就必须调用外围容器的revalidate()方法
        // revalidate(); //重新计算容器内所有组件的大小，并且对他们重新进行布局。该方法不是马上改变组件大小，而是给这个组件加一个需要改变大小的标记
    }

    public static void main(String[] args) {
        new JTextFieldTest().setVisible(true);
    }
}



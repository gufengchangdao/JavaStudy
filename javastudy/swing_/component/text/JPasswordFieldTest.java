package javastudy.swing_.component.text;

import javax.swing.*;
import java.awt.*;

/**
 * 使用JPasswordField创建一个密码框，显示输入的密码
 */
public class JPasswordFieldTest extends JFrame {
    public JPasswordFieldTest() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(300, 300, 230, 200);
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        Container container = getContentPane();

        container.add(new JLabel("输入密码：           "));

        // 创建密码框
        JPasswordField passwordField = new JPasswordField("123", 14);
        // 设置回显字符，默认是'.'
        // passwordField.setEchoChar((char) 0); //这样设置的话密码就直接暴露了
        container.add(passwordField);

        // 创建点击按钮
        JLabel password = new JLabel("");
        JButton submit = new JButton("确认");
        submit.setPreferredSize(new Dimension(60,23));
        submit.addActionListener(e -> {
            // getPassword()返回的是char[]而不是String，是因为字符串在被垃圾回收之前会一直驻留在虚拟机中。如果要得到更好的安全性，在使用之
            // 后可以覆写数组内容
            password.setText("你输入的密码为" + new String(passwordField.getPassword()));//获取密码框的密码并赋值
        });
        container.add(submit);
        container.add(password);
    }

    public static void main(String[] args) {
        new JPasswordFieldTest().setVisible(true);
    }
}

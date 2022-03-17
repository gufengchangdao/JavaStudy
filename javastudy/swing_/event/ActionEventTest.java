/*
ActionEvent
1. ActionEvent是一个函数式接口，要使用点击事件的话，可以创建一个类实现其方法、创建匿名内部类对象或者使用lambda表达式创建对象


*/
package javastudy.swing_.event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionEventTest extends JFrame {
    private JButton jb=new JButton("(^._.^)ﾉ");

    // 这里创建一个成员内部类实现接口方法
    private class jbAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            jb.setText("ヾ|≧_≦|〃");
        }
    }

    // AbstractAction是实现了ActionListener接口的抽象类，继承这个抽象类也可以
    // private class jbAction extends AbstractAction{
    //     public void actionPerformed(ActionEvent e) {
    //         jb.setText("ヾ|≧_≦|〃");
    //     }
    // }

    public ActionEventTest() throws HeadlessException {
        setBounds(300,300,200,100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Container container = getContentPane();

        jb.setFont(new Font("楷体",Font.BOLD,20));
        // 添加事件监听器
        jb.addActionListener(new jbAction());
        container.add(jb);

    }

    public static void main(String[] args) {
        new ActionEventTest().setVisible(true);
        System.out.println("进程");
    }
}

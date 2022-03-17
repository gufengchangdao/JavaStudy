/*
JDialog类
1. JDialog类是一个对话框，而不是一个面板，他可以在主容器之外显示
2. 对话框有一个特点：可以阻塞主窗体，这表示弹出对话框之后用户无法对对话框后面的主窗体做任何操作
*/
package javastudy.swing_.pane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JDialogTest extends JFrame {                               //继承了JFrame可以把该对象传给其他方法，用于添加元素
    public JDialogTest() throws HeadlessException {
        Container container = getContentPane();
        container.setLayout(null);                                      //容器使用绝对布局，没有这个按钮会占满整个窗口
        JButton button = new JButton("弹出对话框");                  //创建一个按钮
        button.setBounds(10, 10, 100, 21);            //定义按钮在容器中的坐标和大小
        button.addActionListener(e -> {
            MyJDialog dialog=new MyJDialog(this);                 //创建对话框
            // TODO 关闭对话框之前，程序会阻塞在setVisible(true)这里
            System.out.println("开始阻塞");
            dialog.setVisible(true);                                    //使对话框可见
            System.out.println("结束阻塞");
        });

        container.add(button);
        setSize(200, 200);
        setLocation(500,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new JDialogTest();
    }
}

/**
 * 自定义对话框类
 */
class MyJDialog extends JDialog {                                       //继承对话框类
    public MyJDialog(JDialogTest frame) {
        //调用父类构造器，三个参数分别是父窗体、窗体标题、表示阻塞父窗口，设置true后只能处理当前窗体，不能控制其他窗体，也不能关闭他们
        super(frame, "第一个JDialog窗体", true);
        Container container = getContentPane();                         //获取主容器
        container.add(new JLabel("这是一个对话框"));                  //在容器中添加标签
        setBounds(120, 120, 200, 100);                //设置对话框的位置和大小
    }
}


/*
JList
1. JList列表可以多选
2. JList要指定大小，跟下拉列表不同，如果内容溢出就需要滚动效果，需要把列表框装入滚动面板中

*/
package javastudy.swing_.component.list;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * 使用JList类创建一个列表框，并显示用户所显示的内容
 */
public class JListTest extends JFrame {
    public JListTest() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(400, 300, 217, 167);
        Container container = getContentPane();
        setLayout(null);

        // 创建列表框
        String[] contents = {"列表1", "列表2", "列表3", "列表4", "列表5", "列表6", "列表7", "列表8", "列表9"};
        // 除了传入数组这个构造方法，同样还有很多构造方法
        JList<String> list = new JList<>(contents);
        // 把列表框装入滚动面板中，如果内容溢出这个列表框的大小时就会出现滚动条
        JScrollPane scrollPane1 = new JScrollPane(list);
        scrollPane1.setBounds(10, 10, 100, 109);
        container.add(scrollPane1);

        // 创建文本域，用来显示选择的列表项
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane2 = new JScrollPane(textArea);
        scrollPane2.setBounds(118, 10, 73, 80);
        container.add(scrollPane2);

        // 创建点击按钮
        JButton submit = new JButton("确认");
        submit.setBounds(120, 96, 71, 23);
        submit.addActionListener(e -> {
            List<String> choice = list.getSelectedValuesList(); //获取选取的所有列表项
            textArea.setText("");                               //先将文本域的内容置空
            for (String value : choice)
                textArea.append(value + "\n");                    //不断添加内容，\n就是文本域的换行符
        });
        container.add(submit);
    }

    public static void main(String[] args) {
        new JListTest().setVisible(true);
    }
}

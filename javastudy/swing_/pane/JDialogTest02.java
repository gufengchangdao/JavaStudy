package javastudy.swing_.pane;

import javax.swing.*;
import java.awt.*;

/**
 * 另一种创建对话框的方法
 * 创建的对话框类继承JPanel类，类中放有动态属性JDialog，显示时将该面板放进JDialog对象中并setVisible(true)即可
 */
public class JDialogTest02 extends JFrame {
    public JDialogTest02() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        setLayout(new FlowLayout());

        JButton button = new JButton("点击");
        // 创建对话框
        button.addActionListener(e -> new MyDialog(this));
        add(button);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new JDialogTest02().setVisible(true));
    }
}

class MyDialog extends JPanel {
    private JDialog dialog;

    public MyDialog(JFrame frame) { //frame可以为空
        this.dialog = new JDialog(frame, true);

        setLayout(new GridLayout(3, 1));
        add(new JLabel("我真是个天才", SwingConstants.CENTER));
        add(new JLabel("我真是个天才", SwingConstants.CENTER));
        JButton button = new JButton("确实");
        // TODO 关闭对话框只是相当于setVisible(false)，这里可以通过按钮触发对话框的关闭
        // button.addActionListener(e -> dialog.setVisible(false)); //关闭的对话框也可以重新设为true显示出来
        button.addActionListener(e -> dialog.dispose()); //不隐藏而是释放资源
        add(button);

        // 为对话框添加默认按钮(回车)，按下回车就会触发按钮的点击事件
        // 注意：只有面板被包装成对话框后才能设置默认按钮，面板本身没有根窗格
        dialog.getRootPane().setDefaultButton(button);

        // 把面板放进对话框
        dialog.add(this);
        dialog.pack();
        dialog.setVisible(true);
    }
}
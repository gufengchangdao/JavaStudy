/*
KeyEvent
1. KeyEvent类常用方法
    e.getSource()       获得触发此次事件的组件对象
    e.getKeyChar()      获取与此事件相关联的字符
    e.isActionKey()     查看此事件的键是否为“动作键”
    e.KeyCode()         获取与此事件中的键相关联的整数keyCode
    e.isAltDown()       Alt是否被按下
    e.isShiftDown()
    e.isControlDown()

*/
package javastudy.swing_.event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * 模拟虚拟键盘，敲入字符相应键盘按钮会变色
 */
public class KeyEventTest extends JFrame {
    ArrayList<JButton> buttons = new ArrayList<>();

    public KeyEventTest() throws HeadlessException {
        setBounds(300, 300, 600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = getContentPane();

        JTextField text = new JTextField();
        text.setBorder(BorderFactory.createTitledBorder("文本显示区"));
        // 文本框添加键盘事件的监听，可以创建KeyAdapter(抽象类)对象，也可以创建ActionListener(接口)对象
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) { //键盘按下
                char word = e.getKeyChar(); //得到按下的字符
                // for (JButton button : buttons) { //这里不用遍历，因为存入的时候就是顺序存入的
                //     if (button.getText().equalsIgnoreCase(String.valueOf(word)))
                //         button.setBackground(Color.GREEN);
                // }
                if (Character.isAlphabetic(word))
                    buttons.get((int) (word - 'a')).setBackground(Color.GREEN); //改变对应按钮的背景色

                if (e.getKeyCode()==KeyEvent.VK_SPACE){ //按下的是空格
                    System.out.println("按下了空格");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) { //键盘松开
                char word = e.getKeyChar();
                if (Character.isAlphabetic(word))
                    buttons.get((int) (word - 'a')).setBackground(getBackground());
            }

            public void keyTyped(KeyEvent e) {} //按下松开
        });
        container.add(text, BorderLayout.NORTH);

        // 创建a-z的字符按钮
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        for (char i = 'a'; i <= 'z'; i++) {
            JButton button = new JButton("" + i);
            button.setPreferredSize(new Dimension(50, 50));
            buttons.add(button);
            panel.add(button);
        }
        container.add(panel);
    }

    public static void main(String[] args) {
        new KeyEventTest().setVisible(true);
    }
}

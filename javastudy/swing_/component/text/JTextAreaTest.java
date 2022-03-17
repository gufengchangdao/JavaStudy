package javastudy.swing_.component.text;

import javax.swing.*;
import java.awt.*;

/**
 * 使用JTextArea类创建一个文本区
 */
public class JTextAreaTest extends JFrame {
    public JTextAreaTest() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(300, 300, 300, 100);
        Container container = getContentPane();

        JTextArea textArea = new JTextArea("这是初始值");
        // JTextArea textArea = new JTextArea("这是初始值",30,2); //设置列数和行数，注意这里设置的只是首选参数

        // textArea.setText("修改文本框内容");
        // 追加内容，注意文本域的换行就是\n
        // textArea.append("\n添加内容");
        // 得到内容

        String text = textArea.getText();

        // 设置是否自动换行，默认不换行。
        // TODO 注意：换行只是视觉效果，文本中并没有添加'\n'字符
        textArea.setLineWrap(true);

        // textArea.setEnabled(false);

        // 如果为true，超长的行会在单词边界换行，为false就会被截断而不考虑单词边界
        textArea.setWrapStyleWord(true);

        // 设置制表符的列数，制表符不会被转化为空格，但是可以让文本对齐到下一个制表符处
        textArea.setTabSize(5);

        container.add(textArea);

    }

    public static void main(String[] args) {
        new JTextAreaTest().setVisible(true);
    }
}

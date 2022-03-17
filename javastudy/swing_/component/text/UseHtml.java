package javastudy.swing_.component.text;

import javax.swing.*;
import java.awt.*;

/**
 * swing里面可以在按钮，标签和菜单项上使用HTML文本，推荐在标签上使用HTML
 * 需要说明的是，包含在HTML标签的第一个组件需要延迟一段时间才能显示出来，这是因为要加载相当复杂的HTML渲染代码
 */
public class UseHtml extends JFrame {
    public UseHtml() throws HeadlessException {
        setBounds(0,0,1000,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = getContentPane();
        JLabel label = new JLabel();
        container.add(label);
        // TODO 字符串可以是以下两种任意形式
        //  <HTML> 内容
        //  <html> 内容 </html>

        label.setText("<HTML>" +
                "<div style=\"width: 1500px;border-radius:15px;background-color: #ceba7b;font-size: 20px;font-family: 楷体,serif;font-weight: bold\">\n" +
                "    <pre>\n" +
                "    action:规定当表单提交时向何处发送表单数据\n" +
                "    method:规定用于发送表单数据的HTTP方式\n" +
                "        get\n" +
                "        post\n" +
                "    target:规定action属性中提交的页面会在何处打开\n" +
                "        _blank\n" +
                "        _self\n" +
                "    name:  表单名称\n" +
                "    </pre>\n" +
                "</div>" +
                "<a href=\"https://www.baidu.com/\">点击我</a>");

    }

    public static void main(String[] args) {
        new UseHtml().setVisible(true);
    }
}
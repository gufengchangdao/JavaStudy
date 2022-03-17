/*
JScrollerPane滚动面板
1. 该面板带有滚动条，被用于在较小的窗体中显示较大篇幅的内容
2. JScrollerPane不能使用布局管理器，并且只能容纳一个组件，若需要向JScrollerPane面板中添加多个组件，那么需要先将组件添加进Panel面板中，再将
   Panel面板添加JScrollerPane面板中

小知识:
1. 使用JScrollerPane放入一个JPanel面板的时候
   (1) 如果JPanel使用的是流布局的话，JPanel在ScrollPane面板中就会排成一排而不换行，推荐JPanel使用网格布局
*/
package javastudy.swing_.panel;

import javax.swing.*;
import java.awt.*;

/**
 * 使用JScrollPane面板为窗体添加上下滚动条
 */
public class JScrollPaneTest extends JFrame {
    public JScrollPaneTest() {
        Container container = getContentPane();
        // 创建文本域组件，默认为20行，50列
        TextArea textArea = new TextArea(20,50);
        textArea.setText("只有内容溢出JScrollerPane滚动面板才会带有滚动条"); //顺便为文本框设置好默认值
        // 创建JScrollerPane面板，并将文本域添加到滚动面板中
        JScrollPane scrollPane = new JScrollPane(textArea);
        // JScrollPane scrollPane = new JScrollPane();
        // scrollPane.setViewport(textArea);            //如果使用构造器的时候没有添加组件，就使用这个添加

        // 获取滚动面板的滚动条组件
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar(); //水平滚动条
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar(); //垂直滚动条

        container.add(scrollPane);
        setSize(400,200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new JScrollPaneTest().setVisible(true);
    }
}

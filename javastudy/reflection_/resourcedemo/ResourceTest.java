package javastudy.reflection_.resourcedemo;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import javax.swing.*;

/**
 * 读取文件资源并显示一个小弹窗
 * 测试两个Class方法
 * getResource()
 * getResourceAsStream()
 *
 * @author Cay Horstmann
 * @version 1.5 2018-03-15
 */
public class ResourceTest {
    public static void main(String[] args) throws IOException {
        // 获取类对象
        Class<ResourceTest> cl = ResourceTest.class;

        // 图片
        // TODO 找到指定的资源，返回一个可以用来加载 资源的URL，如果没有找到就返回null
        URL aboutURL = cl.getResource("about.gif");
        var icon = new ImageIcon(aboutURL); //这个方法可以直接接收文件路径的

        // 正文
        // TODO 找到指定的资源，返回一个可以用来加载 资源的输入流，如果没有找到就返回null
        InputStream stream = cl.getResourceAsStream("data/about.txt");
        var about = new String(stream.readAllBytes(), "UTF-8");

        // 标题
        InputStream stream2 = cl.getResourceAsStream("data/title.txt");
        var title = new String(stream2.readAllBytes(), StandardCharsets.UTF_8).trim();

        JOptionPane.showMessageDialog(null, about, title, JOptionPane.INFORMATION_MESSAGE, icon);
    }
}

package javastudy.swing_.component.icon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * 在标签中添加一个图标
 */
public class ImageIconTest extends JFrame {
    public ImageIconTest() throws HeadlessException {
        // getResource()可以获取本类(编译后的class文件)所在的完整路径
        // getResource接受一个字符串参数，如果以”/”开头，就在classpath根目录下找（不会递归查找子目录），
        // 如果不以”/”开头，就在调用getResource的字节码对象所在目录下找（同样不会递归查找子目录）。
        URL url = ImageIconTest.class.getResource("laugh.png");

        Icon icon=null; //因为url可能为null
        if (url!=null)
            icon=new ImageIcon(url,"阿拉纳克"); //获取图片的Icon对象

        // 创建标签并添加图标，设置图片和标签水平居中
        // 标签也可以只放一个图标
        JLabel label = new JLabel("你知道我是谁吗",icon, SwingConstants.CENTER);
        Container container = getContentPane();
        container.add(label);

        setSize(400,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new ImageIconTest().setVisible(true);

    }
}

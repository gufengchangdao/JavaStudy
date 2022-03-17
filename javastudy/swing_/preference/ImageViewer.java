package javastudy.swing_.preference;

import java.awt.EventQueue;
import java.awt.event.*;
import java.io.*;
import java.util.prefs.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 测试首选项设置的程序。该程序会记住窗口位置、大小和最后选择的文件
 *
 * @author Cay Horstmann
 * @version 1.10 2018-04-10
 */
public class ImageViewer {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new ImageViewerFrame();
            frame.setTitle("图片查看器");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * 一个图像查看器，可从用户首选项中恢复位置、大小和图像，并在退出时更新首选项。
 */
class ImageViewerFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private String image;

    public ImageViewerFrame() {
        Preferences node = Preferences.userNodeForPackage(this.getClass());

        // 从属性中获取位置、大小、标题，使用get()方法必须提供默认值
        int left = node.getInt("left", 0);
        int top = node.getInt("top", 0);
        int width = node.getInt("width", DEFAULT_WIDTH);
        int height = node.getInt("height", DEFAULT_HEIGHT);
        // 获取的值作为配置数据
        setBounds(left, top, width, height);
        image = node.get("image", null);
        var label = new JLabel();
        if (image != null) label.setIcon(new ImageIcon(image));

        add(label);
        var chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileFilter(new FileNameExtensionFilter("图片文件", "jpg", "png"));

        // 设置菜单栏
        var menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        var menu = new JMenu("File");
        menuBar.add(menu);
        var openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(event -> {
            // 打开文件选择对话框，在面板显示打开的图片
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                image = chooser.getSelectedFile().getPath();
                label.setIcon(new ImageIcon(image)); //其实不设置过滤器也可以，ImageIcon是一个null，label接收一个null不会报错
            }
        });

        // 注意，点"Exit"退出不会保存数据
        var exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(event -> System.exit(0));

        addWindowListener(new WindowAdapter() {
            // 窗口关闭时保存程序的数据，用来作为下一次打开的首选项
            public void windowClosing(WindowEvent event) {
                node.putInt("left", getX());
                node.putInt("top", getY());
                node.putInt("width", getWidth());
                node.putInt("height", getHeight());
                if (image != null) node.put("image", image);
            }
        });
    }
}

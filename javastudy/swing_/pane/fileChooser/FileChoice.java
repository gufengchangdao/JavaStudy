package javastudy.swing_.pane.fileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * 演示文件对话框使用
 */
public class FileChoice extends JFrame {
    // 创建文件选择器
    JFileChooser fileChooser = new JFileChooser();
    JButton button = new JButton("选择图片");
    JTextField textField = new JTextField(30);

    public FileChoice() throws HeadlessException {
        setBounds(300, 300, 500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        Container container = getContentPane();

        container.add(textField);
        container.add(button);

        // 从当前工作目录开始
        fileChooser.setCurrentDirectory(new File("."));
        fileOpen();
        // fileSave();
        // directoryOpen();
    }

    public void fileOpen() {
        // TODO 文件过滤器
        // 文件过滤器需要继承FileFilter,而FileNameExtensionFilter已经继承了,可以通过后缀名来过滤文件
        // 需要注意，有一个FileFilter接口，接口只有一个accept()，也有一个抽象类，这个接口没有被使用，推测是设计者忘了
        FileFilter fileFilter = new FileFilter() {
            // 是否过滤该文件
            public boolean accept(File f) {
                return false;
            }

            // 返回这个文件过滤器的一个描述
            public String getDescription() {
                return null;
            }
        };
        // 创建文件名后缀过滤器，选择文件时只会显示有指定后缀名的文件，可以为一个文件安装多个过滤器对象
        FileNameExtensionFilter filter = new FileNameExtensionFilter("图片文件", "jpg", "png");
        fileChooser.setFileFilter(filter);
        // 清除已添加的过滤器
        fileChooser.resetChoosableFileFilters();

        // 指定默认文件
        fileChooser.setSelectedFile(new File("images/1.jpg"));
        // 可以选多个文件
        fileChooser.setMultiSelectionEnabled(true);

        button.addActionListener(e -> {
            // 显示对话框
            // int ret = fileChooser.showOpenDialog(this); //选择了文件返回0，没有选择就返回1
            int ret = fileChooser.showDialog(this, "确认"); // 可以指定确认按钮的名字
            // 获取用户的选择
            if (ret == JFileChooser.APPROVE_OPTION) {   //确认了操作
                // if (ret!=JFileChooser.CANCEL_OPTION){    //取消了操作
                File file = fileChooser.getSelectedFile();
                // File[] files = fileChooser.getSelectedFiles();
                textField.setText(file.getAbsolutePath());
            }
        });
    }

    public void fileSave() {
        button.addActionListener(e -> {
            // 不同点，弹出文件保存的选择框
            int ret = fileChooser.showSaveDialog(this);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                System.out.println(file.isFile()); //打印文件是否存在
                textField.setText(file.getAbsolutePath());
            }
        });
    }

    public void directoryOpen() {
        // 设置文件选择的类型，默认是只能选择文件，这里设置为只能选择目录
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        button.addActionListener(e -> {
            // 不同点，弹出文件保存的选择框
            int ret = fileChooser.showSaveDialog(this);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                textField.setText(file.getAbsolutePath());
            }
        });
    }

    public static void main(String[] args) {
        new FileChoice().setVisible(true);
    }
}

package javastudy.swing_.pane.fileChooser;

import javastudy.utilities.ImageUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * 文件视图和附件组件的测试
 */
public class FileViewTest extends JFrame {
    public FileViewTest() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        setLayout(new FlowLayout());

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("images"));
        // TODO 设置文件视图来显示有关文件信息
        fileChooser.setFileView(new MyFileView(new FileNameExtensionFilter("图片文件", "jpg", "png"),
                ImageUtils.imageIconScale(new ImageIcon("images/icon/app.png"), 0.1)));

        // TODO 设置一个附件组件
        fileChooser.setAccessory(new ImagePreviewer(fileChooser));

        JButton button = new JButton("打开文件选择器");
        button.addActionListener(e -> {
            int choice = fileChooser.showOpenDialog(this);
            if (choice == JFileChooser.APPROVE_OPTION)
                System.out.println("打开的文件是 " + fileChooser.getSelectedFile().getName());

        });
        add(button);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new FileViewTest().setVisible(true));
    }
}

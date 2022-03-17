package javastudy.errorProcess.logger_.jul.handler;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;

/**
 * A modification of the image viewer program that logs various events.
 *
 * @author Cay Horstmann
 * @version 1.03 2015-08-20
 */
public class LoggingImageViewer {
    private static final String LOGGER_NAME = "src.javastudy.errorProcess.logger_.jul.formatter";
    private static final String FILE_HANDLER_DIR = "src/javastudy/errorProcess/logger_/jul/myLog.log";

    public static void main(String[] args) {
        // TODO 创建日志记录器和文件处理器
        if (System.getProperty("java.util.logging.config.class") == null
                && System.getProperty("java.util.logging.config.file") == null) {
            try {
                Logger logger = Logger.getLogger(LOGGER_NAME);
                // 创建指定的日志记录器和文件处理器
                logger.setLevel(Level.ALL);
                final int LOG_ROTATION_COUNT = 10;
                var handler = new FileHandler(FILE_HANDLER_DIR, 0, LOG_ROTATION_COUNT, true);
                logger.addHandler(handler);
            } catch (IOException e) {
                Logger.getLogger(LOGGER_NAME).log(Level.SEVERE, "Can't create log file handler", e);
            }
        }

        EventQueue.invokeLater(() ->
        {
            var windowHandler = new WindowHandler();
            windowHandler.setLevel(Level.ALL);
            // 拿到之前创建的日志记录器并添加处理器
            Logger.getLogger(LOGGER_NAME).addHandler(windowHandler);

            //开启用来测试的窗口
            var frame = new ImageViewerFrame();
            frame.setTitle("LoggingImageViewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Logger.getLogger(LOGGER_NAME).fine("Showing frame");
            frame.setVisible(true);
        });
    }
}

/**
 * The frame that shows the image.
 */
class ImageViewerFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;

    private JLabel label;
    // 设为静态的可以防止被垃圾回收机制回收了
    private static Logger logger = Logger.getLogger("src/javastudy/errorProcess/logger_/jul/formatter");

    public ImageViewerFrame() {
        logger.entering("ImageViewerFrame", "<init>");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // 设置菜单栏
        var menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        var menu = new JMenu("File");
        menuBar.add(menu);

        var openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(new FileOpenListener());

        var exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                logger.fine("Exiting.");
                System.exit(0); //用这个把两个窗口一并关闭
            }
        });

        // use a label to display the images
        label = new JLabel();
        add(label);
        logger.exiting("ImageViewerFrame", "<init>");
    }

    private class FileOpenListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            logger.entering("ImageViewerFrame.FileOpenListener", "actionPerformed", event.getWhen());

            // set up file chooser
            var chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(".")); //当前工作目录

            // 文件过滤器
            chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File f) {
                    // 只显示png后缀文件和目录(目录都不显示的话就没法点了)
                    return f.getName().toLowerCase().endsWith(".png") || f.isDirectory();
                }

                public String getDescription() {
                    return "png Images";
                }
            });

            // show file chooser dialog
            int r = chooser.showOpenDialog(ImageViewerFrame.this);

            // if image file accepted, set it as icon of the label
            if (r == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getPath();
                logger.log(Level.FINE, "Reading file {0}", name);
                label.setIcon(new ImageIcon(name)); // 把这个图片设为图标
            } else logger.fine("File open dialog canceled.");
            logger.exiting("ImageViewerFrame.FileOpenListener", "actionPerformed"); //表示这个方法结束
        }
    }
}


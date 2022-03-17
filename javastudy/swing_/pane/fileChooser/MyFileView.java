package javastudy.swing_.pane.fileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.io.File;

/**
 * 定制文件视图
 * 为文件选择器显示的文件提供特定的图标和文件描述
 */
public class MyFileView extends FileView {
    private FileFilter fileFilter;
    private Icon icon;

    /*TODO 设置文件视图
        需要继承FileView，原方法的返回值都为null，返回null表示使用默认文件视图
    */

    public MyFileView(FileFilter fileFilter, Icon icon) {
        this.fileFilter = fileFilter;
        this.icon = icon;
    }

    /**
     * 返回文件f的文件名，选择器中会显示该方法的返回值，默认是文件名
     */
    @Override
    public String getName(File f) {
        return "文件名: " + f.getName();
    }

    /**
     * 返回文件f的图标
     */
    @Override
    public Icon getIcon(File f) {
        // 这里使用指定的过滤器进行过滤，如果为true，就返回该图标
        if (!f.isDirectory() && fileFilter.accept(f))
            return icon;
        return null;
    }

    /**
     * 这个方法返回Boolean是因为，Boolean比boolean多了一个选择：null。
     * 表示用户是否可以打开目录，或者使用默认视图
     */
    public Boolean isTraversable(File f) {
        return null;
        // return Boolean.FALSE;
    }
}


package javastudy.swing_.pane.fileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

/**
 * 文件选择器的附件组件，用于呈现图片文件的预览图，
 * 组件会在右边呈现
 */
class ImagePreviewer extends JLabel {
    public ImagePreviewer(JFileChooser chooser) {
        setPreferredSize(new Dimension(150, 100));
        setBorder(BorderFactory.createEtchedBorder());

        // TODO 需要捕捉选择文件的动作，被选择的文件是一个属性，可以使用PropertyChangeListener来监听
        chooser.addPropertyChangeListener(event -> {
            // 如果用户选择了一个新图片文件(不是图片文件后面就抛异常啦)
            if (Objects.equals(event.getPropertyName(), JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
                File f = (File) event.getNewValue();
                if (f == null) {
                    setIcon(null);
                    return;
                }
                var icon = new ImageIcon(f.getPath());

                // 缩放图标到合适的大小，负数表示按照原来比例
                if (icon.getIconWidth() > getWidth())
                    icon = new ImageIcon(icon.getImage().getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));
                setIcon(icon); //设置图标
            }
        });
    }
}

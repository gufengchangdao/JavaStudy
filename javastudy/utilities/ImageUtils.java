package javastudy.utilities;

import javax.swing.*;
import java.awt.*;

/**
 * 图片处理工具类
 */
public class ImageUtils {
    /**
     * 对ImageIcon图标进行缩放，不改变原对象
     *
     * @param image 要缩放的图标对象
     * @param i     缩放倍数
     * @return 缩放后的图标对象
     */
    public static ImageIcon imageIconScale(ImageIcon image, double i) {
        int width = (int) (image.getIconWidth() * i);
        int height = (int) (image.getIconHeight() * i);
        Image img = image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(img);
    }
}

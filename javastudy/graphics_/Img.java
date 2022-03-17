package javastudy.graphics_;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 显示一张图片
 */
public class Img extends JFrame {
    public Img() throws HeadlessException {
        setBounds(300,300,500,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setContentPane(new JPanel(){
            @Override
            public void paint(Graphics g) {
                Graphics2D g2=(Graphics2D) g;
                try {
                    // 读取一张照片
                    Image img = ImageIO.read(new File("images/4.jpg"));
                    // Image image = new ImageIcon("images/4.jpg").getImage();
                    //获取这张图片的大小
                    int imgWidth = img.getWidth(this);
                    int imgHeight = img.getHeight(this);

                    // TODO 图片缩放，围绕左上角，顺时针的
                    // g2.rotate(Math.toRadians(5));

                    // TODO 图片倾斜，参数为水平倾斜量和垂直倾斜量
                    // g2.shear(0.2,0);

                    // TODO 指定图片放置位置
                    // g2.drawImage(img,0,0,this);
                    //指定放置位置和图片大小，改变窗口大小图片也会进行放缩
                    // g2.drawImage(img,0,0,getWidth(),getHeight(),this);
                    // 指定面板区域，执行图像从源到目标的缩放，并设置好背景色
                    // 也就是图片的第一个坐标映射到目标矩阵的第一个坐标，第二个坐标映射到第二个坐标，可以实现缩放和翻转
                    g2.drawImage(img,0,0,getWidth(),getHeight(),imgWidth/2,0,imgWidth,imgHeight,Color.LIGHT_GRAY,this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new Img().setVisible(true);
    }
}

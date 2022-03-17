package javastudy.graphics_;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 使用JSlider创建一个滑动条，滑动滑块时改变图片大小
 */
public class Img02 extends JFrame {
    Image image=null;
    int currentWidth;   //图片的当前宽度
    int currentHeight;  //图片的当前高度

    public Img02() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(300,300,500,350);

        try {
            image=ImageIO.read(new File("images/4.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);
        currentWidth=imageWidth;
        currentHeight=imageHeight;

        // 把图片所在面板放到CENTER区域，滑动条放在南部区域
        add(new JPanel(){
            @Override
            public void paint(Graphics g) {
                g.drawImage(image,0,0,currentWidth,currentHeight,this); //使用当前变量值绘制图片
            }
        });

        // 创建一个水平滑块
        JSlider slider = new JSlider();
        slider.setMaximum(100); //设置滑块的最大值
        slider.setMinimum(10);  //设置滑块的最小值
        slider.setValue(45);    //设置滑块的初始值
        // 添加滑块滑动监听事件
        slider.addChangeListener(e -> {
            int value = slider.getValue();              //得到滑块当前的值
            currentWidth=(int)(imageWidth*value/100);   //按百分比改变当前图片大小
            currentHeight=(int)(imageHeight*value/100);
            repaint();                                  //重新绘制图片，会调用paint()方法
        });
        add(slider,BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new Img02().setVisible(true);
    }
}

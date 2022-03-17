package javastudy.swing_.skill;

import javax.swing.*;
import java.awt.*;

/**
 * 让窗体居中显示的三种方法
 */
public class MakePaneCenter extends JFrame {
    public MakePaneCenter() throws HeadlessException {
        setSize(500,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void resolution01(){
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        Toolkit toolkit = Toolkit.getDefaultToolkit();      //定义工具包
        Dimension screenSize = toolkit.getScreenSize();     //获取屏幕尺寸
        int screenWidth = screenSize.width;                 //获取屏幕的宽
        int screenHeight = screenSize.height;               //获取屏幕的高
        setLocation((screenWidth-frameWidth)/2,(screenHeight-frameHeight)/2);
    }

    // 这里方法是关联相关的窗口，参数为null就会使窗口居中对齐，中心店是通过第三种方法里面的getCenterPoint()来获取的
    // 注意：getCenterPoint()方法获取的中心点不包括下方的任务栏区域，窗体不是屏幕居中，而是桌面居中
    // 第一种效率稍高一些
    private void resolution02(){
        setLocationRelativeTo(null);//窗口在屏幕中间显示
    }

    // 获取中心点后手动设置
    private void resolution03(){
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setLocation(p.x-frameWidth/2,p.y-frameHeight/2);
    }

    public static void main(String[] args) {
        MakePaneCenter main = new MakePaneCenter();

        main.resolution01();

        // main.resolution02();

        // main.resolution03();

        main.setVisible(true);

    }
}

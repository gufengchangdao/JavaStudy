package javastudy.graphics_;

import javax.swing.*;
import java.awt.*;

/**
 * 使用 Graphics类绘制一些基本图形
 */
public class GraphicsTest extends JFrame {
    public GraphicsTest() throws HeadlessException {
        setBounds(300,300,500,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 使用Jpanel类并重写paint()方法，用画笔Graphics g 绘制一些图案
        JPanel panel = new JPanel(){
            public void paint(Graphics g) {
                g.setColor(Color.LIGHT_GRAY); //设置当前颜色为浅灰色

                // TODO 绘制圆或者椭圆
                // g.drawOval(10,10,160,80);
                // TODO 绘制弧线，就是指定一个椭圆的起始角度和结束角度
                // g.drawArc(100,100,100,50,0,90);
                // TODO 绘制直线
                // g.drawLine(50,50,200,50);
                // TODO 绘制多边形，自动闭合
                int[] xPoints={10,50,10,50};
                int[] yPoints={10,10,50,50};
                // g.drawPolygon(xPoints,yPoints,4);
                // TODO 绘制多边线
                g.drawPolyline(xPoints,yPoints,4);
                // TODO 绘制矩形
                g.drawRect(100,100,200,150);
                // TODO 圆角矩形
                g.drawRoundRect(50,50,200,150,20,10);
                // TODO 实心弧形
                // g.fillArc(100,100,100,50,0,90);
                // TODO 实心椭圆
                // g.fillOval(10,10,160,80);
                // TODO 实心多边形
                // g.fillPolygon(xPoints,yPoints,4);
                // TODO 实心矩阵
                // g.fillRect(100,100,200,150);
                // TODO 实心圆角矩阵
                // g.fillRoundRect(50,50,200,150,20,10);
                // TODO
            }
        };
        setContentPane(panel); //设置窗体面板

    }

    public static void main(String[] args) {
        new GraphicsTest().setVisible(true);
    }
}

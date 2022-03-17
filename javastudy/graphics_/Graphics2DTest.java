/*
Graphics2D接口继承了Graphics接口，是Graphics的升级版
1. 图形都是一个抽象类
2. 可以对线条的粗细，端点的样式，交汇处的样式，使用serStroke(Stroke s)设置
   BasicStroke实现了Stroke接口，BasicStroke(float width, int cap, int join)
        width为线条粗细
        cap为线端点的样式
            CAP_BUTT    结束未封闭的子路径和虚线段，不添加装饰
            CAP_ROUND   端点为圆角
            CAP_SQUARE  端点为直角
        join为线段交汇处的样式
            JOIN_MITER  尖的
            JOIN_ROUND  圆的
            JOIN_BEVEL  平的
        */
package javastudy.graphics_;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Graphics2DTest extends JFrame {
    class CanvasPanel extends JPanel { ////绘图面板
        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g; //向下转型

            // 画笔默认是粗细为1像素的正方形，设置0f表示使用默认的
            // g2.setStroke(new BasicStroke(5f));
            g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // shape是一个接口，下面的图形类都是抽象类，实现了shape接口
            // 而且图形类一般有含有Float和Double两个静态内部类，因为float用于内部计算完全足够，但是使用float需要转型或后缀，为懒人设计了Double
            Shape[] shapes = new Shape[4]; //声明图形数组
            shapes[0] = new Ellipse2D.Double(5, 5, 100, 100); //创建圆形对象
            shapes[1] = new Rectangle2D.Double(110, 5, 200, 150); //创建矩阵对象
            shapes[2] = new Arc2D.Double(5, 100, 100, 100, 0, 90, Arc2D.OPEN); //弧线对象
            shapes[3] = new Line2D.Double(10, 200, 200, 200); //线条对象
            g2.setColor(Color.RED); //设置画笔颜色
            for (Shape shape : shapes) {
                // Rectangle2D bounds2D = shape.getBounds2D(); //可以得到图形的坐标，大小等信息
                // g2.fill(shape); //填充图形
                g2.draw(shape); //绘制图形
            }
        }
    }

    public Graphics2DTest() throws HeadlessException {
        setBounds(300, 300, 500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // TODO 三种方式都可以把面板添加进去
        // 添加到主面板的CENTER区域
        add(new CanvasPanel());
        // getContentPane().add(new CanvasPanel());
        // 替换主面板
        // setContentPane(new CanvasPanel());
    }

    public static void main(String[] args) {
        new Graphics2DTest().setVisible(true);

    }
}

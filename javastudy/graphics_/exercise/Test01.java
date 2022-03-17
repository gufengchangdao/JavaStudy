package javastudy.graphics_.exercise;

import javax.swing.*;
import java.awt.*;

/**
 * 填充一个五角星
 */
public class Test01 extends JFrame {
    public Test01() {
        setBounds(300, 300, 500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().add(new JPanel() {
            @Override
            public void paint(Graphics g) {
                int[] xPoints=new int[5];
                int[] yPoints=new int[5];
                int x=200;
                int y=200;
                // 利用圆的性质，360/=72，再让其一次隔着一个角，所以每次加144
                for (int i = 1; i <= 5; i++) {
                    int degree=i*144;
                    xPoints[i-1]=x+(int) (Math.sin(Math.toRadians(degree)) * 100);
                    yPoints[i-1]=y+(int) (Math.cos(Math.toRadians(degree))*100);
                }

                g.fillPolygon(xPoints,yPoints,5);
            }
        });


    }

    public static void main(String[] args) {
        new Test01().setVisible(true);
    }
}
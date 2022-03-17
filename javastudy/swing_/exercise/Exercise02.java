package javastudy.swing_.exercise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 设置四个按钮，控制面板中图标的移动
 * 通过这个案例可以看出设置好的监听事件不会因为后面进入死循环而影响触发
 */
public class Exercise02 extends JFrame {
    JLabel img;
    int currentX=100;
    int currentY=100;
    public Exercise02() throws HeadlessException {
        setBounds(300,300,500,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = getContentPane();

        // 把图标放入使用绝对布局的面板中，再把面板放入主面板中部区域
        JPanel panel = new JPanel();
        panel.setLayout(null);
        Icon icon=new ImageIcon("src/javastudy/swing_/laugh.png");
        img = new JLabel(icon);
        img.setBounds(currentX,currentY,100,100);
        panel.add(img);
        container.add(panel);

        // 为上下左右按钮设置事件
        JButton north = new JButton("上");
        JButton south = new JButton("下");
        JButton west = new JButton("左");
        JButton east = new JButton("右");
        north.addMouseListener(new MouseAdapter() { //设个鼠标事件，换换口味
            public void mouseClicked(MouseEvent e) {
                currentY-=10;
            }
        });
        south.addActionListener(e->{
            currentY+=10;
        });
        west.addActionListener(e->{
            currentX-=10;
        });
        east.addActionListener(e->{
            currentX+=10;
        });
        container.add(north,BorderLayout.NORTH);
        container.add(south,BorderLayout.SOUTH);
        container.add(west,BorderLayout.WEST);
        container.add(east,BorderLayout.EAST);
    }

    public static void main(String[] args) {
        Exercise02 main = new Exercise02();
        main.setVisible(true);

        //循环
        while (true){
            main.img.setLocation(main.currentX, main.currentY);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

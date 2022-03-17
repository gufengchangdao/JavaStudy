package javastudy.swing_.event;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MouseEventTest02 implements MouseMotionListener, MouseListener {
    Frame frame = new Frame("关于鼠标的多重监听器");
    TextField tField = new TextField(30);

    public MouseEventTest02() {
        JLabel label = new JLabel("拖动鼠标");
        frame.add(label, BorderLayout.NORTH);
        frame.add(tField, BorderLayout.SOUTH);
        frame.setBackground(new Color(180, 255, 255));
        // frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);
            }
        });
        frame.setSize(300, 200);
        frame.setLocation(400, 250);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MouseEventTest02();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        String string = "鼠标拖动到位置：（" + e.getX() + "，" + e.getY() + "）";
        tField.setText(string);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    }

}

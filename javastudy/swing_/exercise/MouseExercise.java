package javastudy.swing_.exercise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

/**
 * 使用鼠标事件完成一个简单的图形编辑
 * 单机放置一个图标，双击取消这个图标，并且鼠标可以拖动图标
 */
public class MouseExercise {
    public static void main(String[] args) {
        JFrame frame = new JFrame("绘图模拟");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 400);
        frame.add(new MouseComponent());
        frame.setVisible(true);
    }
}

class MouseComponent extends JComponent {
    private final static int IMAGE_HALF_WIDTH = 30; //图标一半的大小
    private final static int IMAGE_HALF_HEIGHT = 30;
    // 所有图标共用一个Image对象即可，这里集合中记录的是图标中心坐标
    private final LinkedList<Point> points;
    private final Image image;
    private Point current; //当前鼠标所在区域的图标

    public MouseComponent() {
        points = new LinkedList<>();
        image = new ImageIcon("images/1.jpg").getImage();
        current = null;
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (Point point : points) {
            g2.drawImage(image, point.x - IMAGE_HALF_WIDTH, point.y - IMAGE_HALF_HEIGHT,
                    IMAGE_HALF_WIDTH << 1, IMAGE_HALF_HEIGHT << 1, null);
        }
    }

    private Point find(Point point) {
        for (Point p : points) {
            if (point.x >= p.x - IMAGE_HALF_WIDTH && point.x < p.x + IMAGE_HALF_WIDTH &&
                    point.y >= p.y - IMAGE_HALF_HEIGHT && point.y < p.y + IMAGE_HALF_HEIGHT)
                return p;
        }
        return null;
    }

    private void remove(Point delPoint) {
        if (delPoint == null) return;
        if (current == delPoint) current = null;
        points.remove(delPoint);
        repaint();
    }

    // 将鼠标所在的点作为图像的中点添加进去
    private void add(Point newPoint) {
        points.add(newPoint);
        repaint(); //调用paintComponent()来重新绘制集合中的图标
    }

    private class MouseHandler extends MouseAdapter {
        // 双击时删除鼠标所在区域的图标
        @Override
        public void mouseClicked(MouseEvent e) {
            current = find(e.getPoint());
            if (current != null && e.getClickCount() >= 2)
                remove(current);
        }

        // 鼠标按下时绘制一个图标
        @Override
        public void mousePressed(MouseEvent e) {
            current = find(e.getPoint());
            if (current == null) add(e.getPoint());
        }
    }

    private class MouseMotionHandler implements MouseMotionListener {
        // 鼠标拖动时图标也跟着移动
        @Override
        public void mouseDragged(MouseEvent e) {
            if (current != null) {
                current.setLocation(e.getX(), e.getY());
                // 唯一的问题是一但开始拖动图标的中心会直接变为鼠标所在点
                repaint();
            }
        }

        // 鼠标滑到图标上时改变鼠标样式
        @Override
        public void mouseMoved(MouseEvent e) {
            if (find(e.getPoint()) == null) setCursor(Cursor.getDefaultCursor());
            else setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

}
package javastudy.swing_.layout;

import javax.swing.*;
import java.awt.*;

/**
 * 演示自定义的简单的网格布局管理器
 */
public class SimpleLayout implements LayoutManager2 {
    public void addLayoutComponent(Component comp, Object constraints) {
        System.out.println("添加了组件");
    }

    public void removeLayoutComponent(Component comp) {
        System.out.println("删除了组件");
    }

    public Dimension maximumLayoutSize(Container target) {
        return null;
    }

    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    public void invalidateLayout(Container target) {
    }

    // 计算组件的最小布局和首选布局，这两个一般相等
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    /*TODO LayoutManager
        1. 设计布局管理器需要实现LayoutManager或者LayoutManager2，前者只用实现5个方法，后者需要实现10个方法，LayoutManager2的特点是允许带有
        约束的add()方法
        2. 对容器调用setVisible()的时候会自动调用这个方法，并且窗体大小改变就会再次调用对组件重新布局，因为setVisible()不是显示出来而是绘制组件并
       显示出来。在setVisible()之前修改组件大小会被布局管理器中的设置覆盖掉，在setVisible()之后修改组件大小却没有绘制出来，也不起效果，所以说
       布局中无法修改组件大小
        3. layoutContainer()里面可以设置组件的大小和位置
    */
    @Override
    public void layoutContainer(Container parent) {
        int panelWidth = parent.getWidth();                 //获取面板的宽度
        int panelHeight = parent.getHeight();
        Component[] components = parent.getComponents();    //获取面板中所有组件

        int width = Math.min(100, panelWidth);                 //指定组件的宽度
        int height = Math.min(100, panelHeight);               //指定组件的高度
        int x = 0;                                            //下一个组件应该放置的坐标
        int y = 0;
        for (Component component : components) {
            component.setBounds(x, y, width, height);       //对每个组件设置大小和方位
            x += width;
            if (x + width > panelWidth) {                     //换行
                x = 0;
                y += height;
            }
        }
    }
}

class Test extends JFrame {
    public Test() throws HeadlessException {
        setBounds(300, 300, 500, 350);
        setLayout(new SimpleLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.add(new JButton("按钮1"));
        container.add(new JButton("按钮2"));
        container.add(new JButton("按钮3"));
        container.add(new JButton("按钮4"));
        container.add(new JButton("按钮5"));

    }

    public static void main(String[] args) {
        new Test().setVisible(true);
    }
}
/*
TODO 布局管理器类
1. 组件放置在容器中，布局管理器决定容器中组件的位置和大小
2. 这几个布局管理器类都实现了LayoutManager接口，里面最重要的方法就是layoutContainer()方法，会在调用setVisible()时自动调用来设置布局
3. 对容器调用setVisible()的时候会自动调用这个方法，并且窗体大小改变就会再次调用对组件重新布局，因为setVisible()不是显示出来而是绘制组件并
   显示出来。在setVisible()之前修改组件大小会被布局管理器中的设置覆盖掉，在setVisible()之后修改组件大小却没有绘制出来，也不起效果，所以说
   布局中无法修改组件大小(除了绝对布局或者setPreferredSize()方法)
4. 也可以自定义布局管理器，实现LayoutManager接口或者LayoutManager2接口(LayoutManager接口的升级版本)
5. 面板的默认布局管理器是流布局管理器，JFrame的内部窗格的默认布局管理器是边框布局管理器

TODO 设置元素坐标和大小
1. setPreferredSize需要在使用布局管理器的时候使用，布局管理器会获取空间的preferredSize，因而可以生效。例如borderLayout在north中放入一个panel，
   panel的高度可以通过这样实现：panel.setPreferredSize(new Dimension(0, 100));这样就设置了一个高度为100的panel，宽度随窗口变化。
2. setSize,setLocation,setBounds方法需要在不使用布局管理器的时候使用，也就是setLayout(null)的时候可以使用这三个方法控制布局。
*/
package javastudy.swing_.layout;

import javax.swing.*;
import java.awt.*;

public class LayoutTest extends JFrame {
    public static void main(String[] args) {
        LayoutTest main = new LayoutTest();

        // main.nullLayout();

        // main.flowLayout();

        // main.borderLayout();

        main.gridLayout();

        main.setBounds(100,200,500,300);
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
        main.setVisible(true);
    }
    // 绝对布局管理器
    private void nullLayout(){
        //先加入的组件，会显示在最上面，后加入的组件，会在底层
        JButton button1 = new JButton("按钮1");
        JButton button2 = new JButton("按钮2");
        // 使用绝对布局定位
        setLayout(null);
        button1.setBounds(10,10,100,50);
        button2.setBounds(10,120,100,50);

        Container container = getContentPane();
        container.add(button1);
        container.add(button2);
    }
    // 流布局管理器
    private void flowLayout(){
        // 设置流布局，可以指定组件从左向右、居中还是从右向左排列
        FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);
        layout.setHgap(0);
        layout.setVgap(0);//竖直间隙
        setLayout(layout);
        // setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));   //指定组件之间的水平和竖直间隔

        Container container = getContentPane();
        for (int i = 0; i < 20; i++) {
            JButton button = new JButton("按钮" + i);
            // setSize()可以设置JButton的大小，只是如果这个Button在某个布局里，那就已经由布局限定的位置限制了，不能再用setSize()随意设置位置
            // 但是可以使用setPreferredSize设置组件的首选大小
            // button.setSize(100,40);
            button.setPreferredSize(new Dimension(80,30));
            container.add(button);
        }
    }
    // 边界布局管理器，默认的
    private void borderLayout(){
        // 1. 边界布局管理器会把容器分为东西南北中五个区域
        // 2. 默认布局就是边界布局管理器，而默认添加的区域就是CENTER区域，因此多个组件添加并且不指定区域，就会每个组件扩张整个面板并且最后面的覆盖前面的
        // 3. 在添加组件而不指定方向时，会默认添加到CENTER区域，并且后面出现的组件会覆盖前面的组件
        // 4. 这个布局很有用，你想把屏幕分成两份、三份还是五份都很轻松
        Container container = getContentPane();

        container.add(new JButton("东"),BorderLayout.EAST);
        container.add(new JButton("南"),BorderLayout.SOUTH);
        container.add(new JButton("西"),BorderLayout.WEST);
        container.add(new JButton("北"),BorderLayout.NORTH);
        container.add(new JButton("中"));
    }
    // 网格布局管理器
    private void gridLayout(){
        // 1. 网格布局管理器在设置时可以指定行数和列数，水平间隔和竖直间隔
        // 2. 组件的大小跟行数、列数和大小有关，改变窗体大小时，组件的大小也会发生变化
        // 3. 两个参数只允许有一个参数为0，表示这个一行或一列可以排列任意多个组件
        setLayout(new GridLayout(7,3,5,5));
        Container container = getContentPane();

        for (int i = 0; i < 20; i++) {
            container.add(new JButton("按钮"+i));
        }
    }

}

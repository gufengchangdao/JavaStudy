package javastudy.swing_.layout.gridBagLayout;

import javax.swing.*;
import java.awt.*;

/**
 * 演示网格包布局管理器的使用
 */
public class GridBagLayoutTest extends JFrame {
    public GridBagLayoutTest() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);

        /*TODO GridBagLayout(网格包布局管理)
            1. 问题引入：GridLayout布局管理器中要求放入网格中的组件的大小与网格大小一致，不够灵活，于是出现了GridBagLayout
            2. 向容器中添加组件，通过助手类GridBagConstraints中的方法addComponent()来完成的，他为添加到容器中的组件创建了约束集合。
            3. Constraints 对象可用以指定组件在网格中的显示区域以及组件在其显示区域中的放置方式。
        */
        // TODO 下面将创建一个
        //      网格坐标(0, 2)，组件占据两行一列，组件上方靠顶显示，大小为首选大小的按钮
        // TODO 1. 创建网格包布局管理器对象并给面板
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        // TODO 2. 创建约束对象并进行配置
        GridBagConstraints constraints = new GridBagConstraints();
        // (1) 每个方向的单元格增量的最大值，建议都设为100，改变窗口大小后，如果不适合，可以将行或列改为0,0表示该行或该列永远为初始大小，不会扩大和缩小
        constraints.weightx=100;
        constraints.weighty=100;
        // (2) 组件左上角行、列位置，我这个例子中就只有一个组件，设置了位置其实也是全窗口，得设置了其他组件才看得出来效果
        constraints.gridx=0;
        constraints.gridy=2;
        // (3) 组件所占据的行数和列数,默认值为 1
        constraints.gridwidth=2;
        constraints.gridheight=1;
        // (4) 当组件小于其显示区域时，指定组件在这个区域的位置，默认是 CENTER，可以指明任何方向
        constraints.anchor = GridBagConstraints.NORTH;
        // (5) 当组件的显示区域大于组件的所需大小时，用于确定是否（以及如何）调整组件
        // GridBagConstraints.NONE（默认值，就是不变）
        // GridBagConstraints.HORIZONTAL（加宽组件直到它足以在水平方向上填满其显示区域，但不更改其高度）、
        // GridBagConstraints.VERTICAL（加高组件直到它足以在垂直方向上填满其显示区域，但不更改其宽度）
        // GridBagConstraints.BOTH（使组件完全填满其显示区域）。
        // constraints.fill = GridBagConstraints.BOTH;
        constraints.fill = GridBagConstraints.NONE;

        JButton button = new JButton("你好");

        // 与下面方法等效
        // gridBagLayout.setConstraints(button,constraints);
        // add(button);

        add(button,constraints);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(()->new GridBagLayoutTest().setVisible(true));
    }
}

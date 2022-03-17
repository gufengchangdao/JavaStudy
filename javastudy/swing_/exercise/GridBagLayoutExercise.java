package javastudy.swing_.exercise;

import javastudy.swing_.layout.gridBagLayout.GBC;

import javax.swing.*;
import java.awt.*;

/**
 * 网格包布局练习
 * 构建一个类似于B站首页的排版
 */
public class GridBagLayoutExercise extends JFrame {
    public GridBagLayoutExercise() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("哔哩哔哩 (゜-゜)つロ 干杯~-bilibili");
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        // 第一行部分
        add(new JButton("首页"), new GBC(0, 0));
        add(new JButton("直播"), new GBC(1, 0));
        add(new JButton("会员购"), new GBC(2, 0));
        add(new JButton("百大"), new GBC(3, 0));
        add(new JButton("下载APP"), new GBC(4, 0));
        add(new JButton("搜索"), new GBC(7, 0));
        add(new JButton("大会员"), new GBC(8, 0));
        add(new JButton("收藏"), new GBC(9, 0));
        add(new JButton("创造中心"), new GBC(10, 0));

        // 第二行部分
        add(new JButton("动态"), new GBC(0, 1));
        add(new JButton("热门"), new GBC(1, 1));
        add(new JButton("频道"), new GBC(2, 1));
        add(new JButton("乱七八糟的"), new GBC(3, 1, 8, 1).setFill(GBC.HORIZONTAL));

        // 第三行部分
        add(new JButton("轮播图"), new GBC(0, 2, 5, 4).setFill(GBC.BOTH).setWeigh(100, 100));
        for (int i = 5; i < 16; i += 2) {
            if (i < 10)
                add(new JButton("推荐"), new GBC(i, 2, 2, 2).setInsets(3).setFill(GBC.HORIZONTAL));
            else
                add(new JButton("推荐2"), new GBC(i - 6, 4, 2, 2).setInsets(3).setFill(GBC.HORIZONTAL));
        }

        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new GridBagLayoutExercise().setVisible(true));
    }
}

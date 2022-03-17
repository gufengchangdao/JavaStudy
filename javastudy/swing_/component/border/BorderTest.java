package javastudy.swing_.component.border;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * 好几种边框的演示
 */
public class BorderTest extends JFrame {
    public BorderTest() throws HeadlessException {
        setBounds(300,150,200,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        Container container = getContentPane();

        JLabel[] labels=new JLabel[16];
        for (int i = 0; i < 16; i++) {
            labels[i]=new JLabel("文本标签",SwingConstants.CENTER);
            labels[i].setPreferredSize(new Dimension(80,60));
            container.add(labels[i]);
        }

        // 定义边框对象后再赋值，线性边框，指定颜色、宽度和是否为非圆角
        Border border1 = new LineBorder(Color.GREEN, 3, false);
        labels[1].setBorder(border1);

        // 使用边框工厂类，BorderFactory就是把传入的参数作为对应边框类构造器的参数传入并返回响应的对象，更方便一点点
        labels[2].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,4));
        // 圆角线性边框
        labels[3].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,4,true));
        // 带标题的边框
        labels[4].setBorder(BorderFactory.createTitledBorder("边框之王"));
        // 最后两个参数指定标题的位置，一个是标题的位置细微调整，第二个是位置选择
        // 标题位于右边顶部
        labels[5].setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.YELLOW),"边框之皇",
                TitledBorder.RIGHT,TitledBorder.TOP));
        // 标题在边框上面
        labels[6].setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.YELLOW),"边框之皇",
                TitledBorder.CENTER,TitledBorder.ABOVE_BOTTOM));
        // 标题在边框内部
        labels[7].setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.YELLOW),"边框之皇",
                TitledBorder.LEFT,TitledBorder.BELOW_TOP));
        // 创建斜角边框，有立体感
        // Border border8 = new BevelBorder(BevelBorder.RAISED,Color.BLACK,Color.cyan);
        labels[8].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.BLACK,Color.cyan));
        labels[9].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.cyan));
        // 创建软斜角边框
        labels[10].setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED,Color.BLACK,Color.cyan));
        // 分别指定边框四个方向的宽度
        labels[11].setBorder(BorderFactory.createMatteBorder(0,5,0,5,Color.YELLOW));
        // 创建虚线边框，并指定虚线长度和间隔宽度
        labels[12].setBorder(BorderFactory.createDashedBorder(Color.LIGHT_GRAY,10,2));
        // 创建组合边框，两个边框，一个在外面，一个在里面
        labels[13].setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.orange,Color.ORANGE),
            BorderFactory.createDashedBorder(Color.yellow,3,3)
        ));
        // 设置空白边框(就像内边距padding一样)，可用于面板，防止组件贴着面板边缘
        labels[14].setBorder(BorderFactory.createEmptyBorder(6,6,6,6));



        // 获取组件边框的大小，在绝对布局时就需要知道组件的边框大小
        Insets insets = labels[11].getInsets();
        System.out.println(insets); //java.awt.Insets[top=0,left=5,bottom=0,right=5]
        // System.out.println(insets.top);
    }

    public static void main(String[] args) {
        new BorderTest().setVisible(true);
    }
}

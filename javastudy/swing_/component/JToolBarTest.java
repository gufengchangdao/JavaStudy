package javastudy.swing_.component;

import javastudy.utilities.ImageUtils;

import javax.swing.*;
import java.awt.*;

/**
 * 工具条方法演示
 */
public class JToolBarTest extends JFrame {
    /*TODO 工具条
        1. 工具条是一个按钮条，通过它可以快速访问程序中最常用的命令
        2. 工具条在面板使用边框布局管理器时，可以随意拖动和吸附，还能拖出窗口
    */
    public JToolBarTest() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,500,400);
        // 创建工具条，标题和指明水平还是竖直
        // JToolBar toolBar = new JToolBar();
        JToolBar toolBar = new JToolBar("我的工具条",SwingConstants.VERTICAL);//指定组件竖直排列
        JButton button1 = new JButton(ImageUtils.imageIconScale(new ImageIcon("images/icon/app.png"), 0.2));
        JButton button2 = new JButton(ImageUtils.imageIconScale(new ImageIcon("images/icon/copy.png"), 0.2));
        JButton button3 = new JButton(ImageUtils.imageIconScale(new ImageIcon("images/icon/editor.png"), 0.2));
        // 加点提示信息
        button1.setToolTipText("打开应用");
        button2.setToolTipText("复制");
        button3.setToolTipText("编辑");
        toolBar.add(button1);
        toolBar.add(button2);
        toolBar.addSeparator();
        toolBar.add(button3);

        add(toolBar,BorderLayout.WEST);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(()->new JToolBarTest().setVisible(true));
    }
}

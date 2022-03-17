/*
JFrame窗体
1. 开发Swing程序的流程可以概括为：
    (1) 继承JFrame类创建一个窗体
    (2) 向这个窗体添加组件
    (3) 为添加的组件设置监听事件

TODO 关于 setVisible()方法：
1. setVisible(true)目的是使控件可以显示出来,如果该控件已经被显示出来，那么该方法是控件显示在窗口的最前方。像绝对布局里面先创建的组件会覆盖后创建的组件
    JLabel jl = new JLabel("这是一个JFrame窗体");
    jl.setVisible(true);    //让该组件显示出来
    jl.setVisible(true);    //已经显示的组件会被显示在窗口的最前方，在绝对布局里面组件相互重叠的很有效
2. setVisible的对象一般是该对象的使用者调用的。如果setVisible在某个控件(面板)的内部，那么在setVisible函数后面添加的控件就显示不出来了，也就是说一般要
   先添加完所有组件再调用
3. 原因就在于，setVisible(true)并不是告诉JVM让该控件可见，而是在内部调用repaint方法把各个控件画出来进行显示。如果在控件还没完全添加完其他控件就
   setVisible(true)那么在方法后面添加的控件都不能显示。
*/
package javastudy.swing_.pane;

import javax.swing.*;
import java.awt.*;

/**
 * JFrame窗体练习
 */
public class JFrameTest01 {
    public static void main(String[] args) {
        // 创建窗体
        // JFrame jf = new JFrame(); //初始不可见，无标题
        // 设置标题
        // jf.setTitle("登录系统");
        JFrame jf = new JFrame("登录系统"); //初始不可见，有标题
        // 窗体转换为容器，获取主容器
        Container container = jf.getContentPane();
        // 一个文本标签
        JLabel jl = new JLabel("这是一个JFrame窗体");
        // 使标签上的文字居中，也可以添加进主容器之后再设置
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        // 将标签添加进主容器中
        container.add(jl);
        // 设置窗体宽高，这个大小包括上面标题部分
        jf.setSize(300,150);
        // 设置窗体在屏幕中出现的位置
        jf.setLocation(320,240);
        // 这个方法与上面两个等效
        // jf.setBounds(320,240,300,150);
        // 设置窗体的关闭方式，默认是DISPOSE_ON_CLOSE
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        /*
            DO_NOTHING_ON_CLOSE 关闭窗体时不触发任何操作
            HIDE_ON_CLOSE       关闭时释放窗体资源，窗体会消失但程序不会结束
            DISPOSE_ON_CLOSE    关闭窗体时仅隐藏窗体，不释放窗体
            EXIT_ON_CLOSE       关闭时释放窗体资源并关闭程序
         */

        // 让窗体展示出来
        jf.setVisible(true);
    }
}

/*
JOptionPane创建小型对话框
1. 该类可以调用静态方法弹出不同类型的弹出框并返回用户选项
2. 弹出框的类别有：
    showConfirmDialog() 确认框
    showInputDialog()   输入框
    showMessageDialog() 通知框
    showOptionDialog()  自定义对话框
3. 前三种对话框都封装了一套外观样式，自定义对话框就是一个空白的框，要自己指定，相对于自定义对话框，前三个对话框相当于分别受到一些限制
4. 弹出框出现时，进程就会卡在那里，后面的代码不执行(除了点击事件产生的弹出框)

*/
package javastudy.swing_.pane;

import javax.swing.*;
import java.awt.*;

public class JOptionPaneTest {
    public static void main(String[] args) {
        // 自定义对话框
        new OptionDialog(null); //未做出选择会阻塞
        // 确认框，跟自定义对话框像
        // new ConfirmDialog();
        // 输入框或者下拉列表
        // new InputDialog();
        // 提示框，样式最简单
        // new MessageDialog();
    }
}
// 自定义对话框
class OptionDialog {
    protected OptionDialog(Component parentComponent) {
        // parentComponent指明在哪个窗体(frame)上显示，如果传入(JFrame)对象，会显示在该窗体居中位置，传入null就会在屏幕中间弹出
        // 提示信息
        String message = "你有对象吗";
        // 对话框标题
        String title = "提问";
        // 对话框显示的图标
        // 图标为null的话会根据第四个参数使用自带的图标
        Icon icon = new ImageIcon("src/javastudy/swing_/laugh.png");
        // Icon icon = null;
        // 用户的选择的对象组成的数组
        // 选择数组为null的话，会根据第四个参数optionType确定对话框的类型(来决定选项字样和个数)，
        // JButton[] options={new JButton("我有"), new JButton("不想回答")};
        String[] options={"我有","不想回答"};
        // String[] options=null;
        // 表示对话框默认选择的对象，只有在使用options时才有意义
        Object initialValue = null;
        int choice = JOptionPane.showOptionDialog(parentComponent, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, initialValue);
        System.out.println("你的选择是："+options[choice]);   //返回的choice是数组索引
    }
}

// 确认框
class ConfirmDialog{
    public ConfirmDialog() {
        // 默认标题是“选择一个选项”，问号图标，三个选项
        // int choice = JOptionPane.showConfirmDialog(null, "确定退出吗？");

        int choice = JOptionPane.showConfirmDialog(null, "确定退出吗？", "请选择", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        System.out.println("你选择的是第 "+(choice+1)+" 个选项");
    }
}

// 输入框
class InputDialog{
    public InputDialog() {
        // String name = JOptionPane.showInputDialog("请输入你的名字");//点击取消的话name就为null
        // 含有默认值的输入框
        // String name = JOptionPane.showInputDialog(null, "请输入你的名字", "无名之辈");
        // 含有标题和图标与默认值
        // 因为这个方法还有选项数组这一参数，方法会返回这个元素而不是索引，所以返回类型是Object
        // Object name = JOptionPane.showInputDialog(null, "请输入你的名字", "输入", JOptionPane.INFORMATION_MESSAGE, null, null, "无名之辈");
        // 加一个数组，输入框变下拉菜单，如果数组不是字符串数组，会调用元素的toString()方法表示
        String[] options={"无名之辈","李白","张飞","孙悟空"};
        String name = (String) JOptionPane.showInputDialog(null, "请输入你的名字", "输入", JOptionPane.INFORMATION_MESSAGE, null,options,null);

        System.out.println("你输入的名字是 "+name);//点击取消，方法返回的就是null
    }
}

// 通知框
class MessageDialog{
    public MessageDialog() {
        JOptionPane.showMessageDialog(null,"登录成功！");
        JOptionPane.showMessageDialog(null,new JButton("登录成功！"));
        JOptionPane.showMessageDialog(null,"登录成功！","提示框",JOptionPane.PLAIN_MESSAGE);
    }
}
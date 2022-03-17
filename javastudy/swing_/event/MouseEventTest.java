/*
MouseEvent
1. 可以实现MouseListener接口或重写MouseAdapter抽象类来设置鼠标监听事件，他们之间的区别是MouseAdapter抽象类实现了MouseListener接口接口，所以在
    设置监听事件的使用只用重写抽象类中需要的方法而不用实现接口的所有方法
2. 可以使用的监听器和响应的事件：
addMouseListener
    mouseClicked    鼠标点击
    mousePressed    鼠标按下
    mouseReleased   鼠标释放
    mouseEntered    光标移进组件
    mouseExited     光标移出组件
addMouseWheelListener
    mouseWheelMoved 鼠标滚动
addMouseMotionListener
    mouseDragged    鼠标拖动
    mouseMoved      鼠标移动
3. 注意：使用不同的监听器无法监听其他的事件，像addMouseListener就不能监听鼠标的拖动和移动事件，实现其方法也没用
3.MouseEvent事件执行的方法
getX()              获取坐标
getY()              获取坐标
getSource()         返回次事件的组件对象
getButton()         返回鼠标按下、单击、松开的按键类型
getClickCount()     返回单机按键的次数
getWheelRotation()  判断滚轮滑动方向

*/
package javastudy.swing_.event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseEventTest extends JFrame {
    // 判断鼠标按下的是哪个键
    private String  mouseOper(MouseEvent e) {
        int i = e.getButton();
        switch (i) {
            case MouseEvent.BUTTON1 -> {
                return "按下的是左键";
            }
            case MouseEvent.BUTTON2 -> {
                return "按下的是滚轮";
            }
            case MouseEvent.BUTTON3 -> {
                return "按下的是右键";
            }
        }
        return "";
    }

    public MouseEventTest() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(300, 300, 500, 350);
        Container container = getContentPane();

        container.add(new JLabel("西", SwingConstants.CENTER), BorderLayout.WEST);
        container.add(new JLabel("东", SwingConstants.CENTER), BorderLayout.EAST);
        container.add(new JLabel("北", SwingConstants.CENTER), BorderLayout.NORTH);
        container.add(new JLabel("南", SwingConstants.CENTER), BorderLayout.SOUTH);

        JButton button = new JButton("滑动鼠标，显示内容");
        // TODO 监听鼠标点击，移进、移出组件事件
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                button.setText("鼠标单击一次，"+mouseOper(e)+"，单机次数为"+e.getClickCount());
            }

            public void mousePressed(MouseEvent e) {
                button.setText("鼠标被按下，"+mouseOper(e)+"，单机次数为"+e.getClickCount());
            }

            public void mouseReleased(MouseEvent e) {
                button.setText("鼠标被释放，"+mouseOper(e)+"，单机次数为"+e.getClickCount());
            }

            public void mouseEntered(MouseEvent e) {
                button.setText("光标移入组件，鼠标坐标为 (X: "+e.getX()+",Y: "+e.getY()+")");
            }

            public void mouseExited(MouseEvent e) {
                button.setText("光标移出组件");
            }
            // TODO 注意：这里实现了不配套的方法也无法起效果
            public void mouseDragged(MouseEvent e) {}
            public void mouseMoved(MouseEvent e) {}
            public void mouseWheelMoved(MouseWheelEvent e) {}
        });
        // TODO 监听鼠标滚轮滑动事件
        button.addMouseWheelListener(new MouseAdapter() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.getWheelRotation()==1){
                    button.setText("滑轮向前....");
                }
                if(e.getWheelRotation()==-1){
                    button.setText("滑轮向后....");
                }
            }
        });
        // TODO 监听鼠标移动和拖动事件
        button.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                button.setText("鼠标在组件中移动...");
            }

            // 特点：拖动组件后只要不松手，即便组件移出组件所在的区域甚至移出窗口都起效(移出窗口就变成负数了)
            public void mouseDragged(MouseEvent e) {
                button.setText("鼠标拖动到位置：（" + e.getX() + "，" + e.getY() + "）");
            }
        });
        container.add(button);
    }

    public static void main(String[] args) {
        new MouseEventTest().setVisible(true);
    }
}

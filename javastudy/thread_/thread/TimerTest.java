package javastudy.thread_.thread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

/**
 * 使用定时器来重复响铃
 */
public class TimerTest implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(Instant.ofEpochMilli(e.getWhen()));
        Toolkit.getDefaultToolkit().beep(); //beep()发出一声铃响
    }

    public static void main(String[] args) {
        TimerTest main = new TimerTest();
        // TODO 以指定的间隔触发一个或多个ActionEvent
        Timer timer = new Timer(1000, main);
        timer.start();

        // timer.setRepeats(false); //设置是否只重复，默认为true，这里设置为只运行一次

        JOptionPane.showMessageDialog(null, "Quit program?");
        // System.exit(0);//因为开启的线程不是守护线程，会一直执行
        timer.stop(); //停止执行
    }
}

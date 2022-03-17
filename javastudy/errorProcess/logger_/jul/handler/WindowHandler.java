package javastudy.errorProcess.logger_.jul.handler;

import javax.swing.*;
import java.io.OutputStream;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/*
TODO 关于处理器的使用
    1. StreamHandler实现Handler接口
    2. 其他所有的Handler都继承了StreamHandler
    3. 如果你要定义Handler类可以这样写
        class MyHandler extends StreamHandler{
            public void publish(LogRecord record) {
                super.publish(record); //级别检验、数据过滤、数据格式化、输出到缓存流
                flush(); //手动刷新输出
            }
        }
*/

/**
 * 在窗口显示日志的处理器
 */
public class WindowHandler extends StreamHandler {
    private JFrame frame;

    public WindowHandler() {
        frame = new JFrame();
        var output = new JTextArea();
        output.setEditable(false);
        frame.setSize(200, 200);
        frame.add(new JScrollPane(output));
        // frame.setFocusableWindowState(false);
        frame.setVisible(true);
        // 修改处理器的输出位置，内容添加到文本域中
        setOutputStream(new OutputStream() {
            public void write(int b) {
            } // not called

            public void write(byte[] b, int off, int len) {
                output.append(new String(b, off, len));
            }
        });

    }

    /**
     * 格式化并发布一个日志
     * 这里重写是因为处理器会先缓存记录，并且只有在缓存区满了以后才会将它们写入流中，所以要重写，以便处理器获得每个记录之后刷新输出缓冲区
     *
     * @param record 日志记录
     */
    public void publish(LogRecord record) {
        if (!frame.isVisible()) return;
        super.publish(record);
        flush(); //关键
    }
}

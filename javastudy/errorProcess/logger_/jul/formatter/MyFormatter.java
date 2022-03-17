package javastudy.errorProcess.logger_.jul.formatter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.*;

/*
TODO 常用格式化器
    XMLFormatter SimpleFormatter

TODO 常用LogRecord方法
    record.getLevel()
    record.getLoggerName()
    record.getMessage()
    record.getParameters()
    record.getThrown()
    record.getSourceClassName()
    record.getSourceMethodName()
    record.getMillis() 获取创建时间
    record.getInstant() 获取创建时间
    record.getSequenceNumber() 获取这个日志记录的唯一序列号
    record.getThreadID() 获取这个日志记录的线程的唯一ID
*/

/**
 * 日志格式化器的使用
 */
public class MyFormatter extends Formatter {
    // 格式化内容
    @Override
    public String format(LogRecord record) {
        // 获取类名和方法名
        String source;
        if (record.getSourceClassName() != null) {
            source = record.getSourceClassName();
            if (record.getSourceMethodName() != null) {
                source += " " + record.getSourceMethodName();
            }
        } else {
            source = record.getLoggerName();
        }

        // 日志内容
        String message = formatMessage(record);

        //异常数据
        String throwable = "";
        if (record.getThrown() != null) {
            //用 PrintWriter 输出内容，最后用 StringWriter 转换为字符串
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw); //打印堆栈轨迹
            pw.close(); //关闭PrintWriter
            throwable = sw.toString();
        }
        return "来源：" + source + "\nname：" + record.getLoggerName() + "\n级别：" +
                record.getLevel().getLocalizedName() + "\n内容：" + message + "\n异常：" + throwable;
    }

    // 添加头部
    @Override
    public String getHead(Handler h) {
        return "------------- 自定义日志信息格式 -------------\n";
    }

    // 添加尾部
    // 使用ConsoleHandler处理器的时候方法不会被调用，但是使用FileHandler处理器会被调用
    @Override
    public String getTail(Handler h) {
        return "-----------------  日志结束 -----------------\n";
    }
}

class FormatterTest {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger("test01");

        MyFormatter myFormat = new MyFormatter();
        logger.setLevel(Level.ALL);

        logger.setUseParentHandlers(false); //关闭顶级记录器的ConsoleHandler处理器
        FileHandler fileHandler = new FileHandler("src/javastudy/errorProcess/logger_/jul/myLog.log", true);
        fileHandler.setLevel(Level.ALL);
        fileHandler.setFormatter(myFormat); //处理器应用格式化器
        logger.addHandler(fileHandler);

        logger.throwing("Test", "main", new RuntimeException("我错了"));
        // logger.info("这就是首席的力量o(￣ヘ￣o＃).");
    }
}
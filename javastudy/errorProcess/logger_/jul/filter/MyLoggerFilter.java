package javastudy.errorProcess.logger_.jul.filter;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * 演示日志过滤器的使用
 */
public class MyLoggerFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        System.out.println(record.getMessage()); //日志内容
        System.out.println(record.getLoggerName()); //记录器的name
        System.out.println(record.getSourceClassName()); //类名
        System.out.println(record.getSourceMethodName()); //方法名
        System.out.println(record.getResourceBundleName());
        return true;
    }
}

class FilterTest {
    public static void main(String[] args) {
        String className=new Object() {}.getClass().getEnclosingClass().getName();
        Logger logger = Logger.getLogger(className);

        // 先设置日志记录器和处理器的级别，级别都不过的话就不会调用过滤器
        // TODO 源码流程是：记录器级别 --> 记录器过滤器 --> 处理器级别 --> 处理器过滤器
        logger.setLevel(Level.ALL);
        logger.getParent().getHandlers()[0].setLevel(Level.ALL);

        // 既可以给日志记录器添加过滤器，又可以给日志处理器添加过滤器，但是都只能添加一个
        logger.setFilter(new MyLoggerFilter());
        // logger.getParent().getHandlers()[0].setFilter(new MyLoggerFilter());

        logger.warning("这就是首席的力量");
        logger.info("这就是首席的力量");
        logger.config("这就是首席的力量");
        RuntimeException exception = new RuntimeException("报错啦");
        logger.throwing(className,"main",exception);

    }
}
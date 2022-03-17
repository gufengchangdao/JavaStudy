package javastudy.errorProcess.logger_.jul.logger;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;

/**
 * JUL日志测试
 */
public class JULTest {
    private static final String DIRECTORY = "src/javastudy/errorProcess/logger_/jul/"; //为了方便，这里定义一下目录

    /**
     * 日志的使用
     */
    @Test
    public void test01() {
        // 1. 获取日志记录器对象
        // 每一个日志记录器都有一个唯一标识，通常以当前的全类名命名
        Logger logger = Logger.getLogger("javastudy.errorProcess.logger_.jul.logger.JULTest"); // 这个方法是创建或获取(已有)日志记录器
        // Logger global = Logger.getGlobal(); // 全局日志，是为了兼容老版本留下的

        // 2. 日志记录输出
        logger.info("hello jul");

        // 通过方法进行日志记录
        logger.log(Level.INFO, "info msg");

        // 通过占位符方法输出变量值
        String name = "艾希";
        String sex = "女";
        logger.log(Level.INFO, "用户数据：{0}, {1}", new Object[]{name, sex}); //{0} 就是数组索引
    }

    /**
     * 日志级别
     */
    @Test
    public void test02() {
        /* TODO 日志的级别
            1. severe、warning等这些方法是调用的log()方法，在log方法里面会判断logger的日志级别，如果级别低于该方法需要的级别才可以调用
            2. 如果想要设置日志的级别，需要同时修改日志记录器和处理器的级别才可以
         */
        Logger logger = Logger.getLogger("javastudy.errorProcess.logger_.jul.logger.JULTest");

        // TODO 记录日志记录
        logger.severe("severe"); //1000
        logger.warning("warning"); //900
        logger.info("info"); //800 JUL默认的日志级别，级别低于800的日志信息不会输出

        logger.config("config"); //700
        logger.fine("fine"); //500
        logger.finer("finer"); //400
        logger.finest("finest"); //300

        //修改日志记录器级别
        logger.setLevel(Level.ALL);
        //修改处理器的日志级别，logger自己是没有处理器的，用的是顶级记录器的处理器，这里找到定义处理器的ConsoleHandler处理器设置日志级别
        logger.getParent().getHandlers()[0].setLevel(Level.ALL);
        logger.config("修改了日志记录器和日志处理器后的config");

        // TODO 记录一个描述进入/退出方法的日志记录
        logger.entering("JULTest", "test02");
        logger.exiting("JULTest", "test02");


    }

    /**
     * 日志的配置
     */
    @Test
    public void test03() throws IOException {
        Logger logger = Logger.getLogger("javastudy.errorProcess.logger_.jul.logger.JULTest");

        /* TODO 日志处理器和
            1. 常用处理器：ConsoleHandler、FileHandler、SocketHandler、StreamHandler
            2. 默认情况下，日志记录器会将记录发送到自己的处理器和父日志记录器的处理器，比如顶级记录器有一个ConsoleHandler处理器，而这个处理器
            会将INFO及以上的记录发送到控制台。也就是说的logger没添加处理器时用的都是父记录器的处理器
            3. 子日志记录器的记录会被父日志记录器的处理器处理，如果不想要请使用 setUseParentHandlers(false) 关掉

         */

        // 关闭父日志记录器的处理器(""的ConsoleHandler)，不然会打印两次
        logger.setUseParentHandlers(false);
        // 创建Handler对象
        ConsoleHandler consoleHandler = new ConsoleHandler(); //控制台输出
        FileHandler fileHandler = new FileHandler(DIRECTORY + "myLog.log"); //文件输出
        // 创建简单格式转换对象
        SimpleFormatter formatter = new SimpleFormatter();
        // 进行关联
        consoleHandler.setFormatter(formatter);
        fileHandler.setFormatter(formatter);
        logger.addHandler(consoleHandler);
        logger.addHandler(fileHandler);
        // 设置级别
        logger.setLevel(Level.ALL); //级别最低，就是所说有日志都可以输出
        // logger.setLevel(Level.OFF); //关闭所有日志
        consoleHandler.setLevel(Level.ALL);
        fileHandler.setLevel(Level.ALL);

        logger.severe("severe"); //1000
        logger.warning("warning"); //900
        logger.info("info"); //800
        logger.config("config"); //700
        logger.fine("fine"); //500
        logger.finer("finer"); //400
        logger.finest("finest"); //300
    }

    /**
     * Logger对象父子关系
     */
    @Test
    public void test04() throws IOException {
        Logger logger1 = Logger.getLogger("com.test");
        Logger logger2 = Logger.getLogger("com");
        //所有日志记录器的顶级父元素是 LogManager$RootLogger ， name为 ""
        Logger logger3 = logger2.getParent(); //顶级父元素
        System.out.println(logger2.getParent().getClass() + "  " + logger2.getParent().getName());
        System.out.println(Logger.getGlobal().getParent() == logger3); //true 全局日志的父元素也是name为""的这个对象

        //与包名类似，日志记录器也有层次结构。但是日志记录器的父与子之间将共享某些属性。比如对父级及录器设置了日志级别，子类也会继承这个级别
        //把上面test03的代码拷贝下来，设置logger2的日志级别，此时logger1的日志级别也会更改
        System.out.println(logger1.getParent() == logger2); //true

    }

    /**
     * JUL配置文件
     */
    @Test
    public void test05() throws IOException {
        // 读取配置文件，通过类加载器
        // InputStream ins = JULTest.class.getClassLoader().getResourceAsStream("logging.properties");
        FileInputStream ins = new FileInputStream(DIRECTORY + "logging.properties");

        // 创建LogManager
        LogManager logManager = LogManager.getLogManager();
        // 通过LogManager加载配置文件
        logManager.readConfiguration(ins);
        // 合并老配置和新配置
        // logManager.updateConfiguration(key -> (oldValue, newValue) -> newValue == null ? oldValue : newValue);
        // 创建日志记录器记录日志，可以看到创建的日志记录器设置都已更改，因为顶级父级已经配置过了
        test02();
    }

    /**
     * 使用日志来记录异常
     */
    @Test
    public void test06() {
        Logger logger = Logger.getLogger("javastudy.errorProcess.logger_.jul.logger.JULTest");

        // throwing()日志的级别是FINER，需要先配置一下
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);
        consoleHandler.setLevel(Level.FINER);
        logger.setLevel(Level.FINER);

        try {
            // TODO 典型用法一
            if (true/*判断*/) {
                IOException e = new IOException("文件打不开");
                logger.throwing(this.getClass().getName(), "test06", e);
                logger.log(Level.WARNING, "IO异常", e);
                throw e;
            }
        } catch (IOException e) {
            //交给外围方法来处理异常
        }

        // TODO 典型用法二
        try {
            throw new IOException("文件打不开");
        } catch (IOException e) {
            logger.throwing(this.getClass().getName(), "test06", e);
            // 如果只是打印控制台的话还不如printStackTrace(),但是可以设置日志的输出方向
        }
    }
}

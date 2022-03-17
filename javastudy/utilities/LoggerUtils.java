package javastudy.utilities;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 日志工具类，用于快速生成日志信息
 */
public class LoggerUtils {
    /**
     * 将日志信息输出到文件中，文件与类同一目录下生成或追加内容
     * 文件名为 [fileName].log
     *
     * @param aClass   以 com.该类所在包名 为日志记录器命名
     * @param level    日志信息的级别
     * @param message  日志内容
     * @param fileName 文件名，不加后缀。如果已经存在文件则进行追加，否则就创建并添加内容
     */
    public static void addFileLoggerInformation(Class<?> aClass, Level level, String message, String fileName) {
        addFileLoggerInformation(aClass, level, message, false, "javaDialog");
    }

    /**
     * 将日志信息输出到文件中，文件与类同一目录下生成或追加内容
     * 文件名为 javaDialog.log
     *
     * @param aClass  以 com.该类所在包名 为日志记录器命名
     * @param level   日志信息的级别
     * @param message 日志内容
     */
    public static void addFileLoggerInformation(Class<?> aClass, Level level, String message) {
        addFileLoggerInformation(aClass, level, message, false, "javaDialog");
    }

    /**
     * 将日志信息输出到文件中，文件与类同一目录下生成或追加内容
     * 文件名为 javaDialog.log
     *
     * @param aClass        以 com.该类所在包名 为日志记录器命名
     * @param level         日志信息的级别
     * @param message       日志内容
     * @param isCloseParent 是否阻止控制台输出日志消息
     * @param fileName      文件名，不加后缀。如果已经存在文件则进行追加，否则就创建并添加内容
     */
    public static void addFileLoggerInformation(Class<?> aClass, Level level, String message, boolean isCloseParent, String fileName) {
        Logger logger = Logger.getLogger("com." + aClass.getPackageName());
        try {
            if (isCloseParent) logger.setUseParentHandlers(false); //关闭顶级记录器的控制台处理器
            FileHandler fileHandler = new FileHandler("src/" + aClass.getPackageName().replaceAll("\\.", "/") + "/" + fileName + ".log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);
            logger.log(level, message);
        } catch (IOException e) {
            System.err.println("无法创建日志的文本处理器");
            e.printStackTrace();
        }
    }

}

package javastudy.thread_.thread;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 线程中UncaughtExceptionHandler接口的使用
 */
public class UncaughtExceptionHandlerTest {
	/**
	 * 1. 引出Thread. UncaughtExceptionHandler接口
	 */
	public static void test01() {
        /*TODO
            1. run()方法不能抛出检查型异常，但是非检查型异常可能导致线程终止，在没有try-catch下抛出一个检查型异常时，线程在死亡之前会将异常传
            递给一个用于处理未捕获异常的处理器
            2. 这个处理器必须实现Thread的一个内部接口UncaughtExceptionHandler，该接口只有一个方法
                void uncaughtException(Thread t, Throwable e);
               把异常交给该方法处理
         */
		Runnable runnable = () -> {
			throw new ArrayIndexOutOfBoundsException();
		};
		new Thread(runnable).start();
	}

	/**
	 * 2. 实现UncaughtExceptionHandler接口，创建一个自定义的异常处理程序
	 * 引出ThreadGroup对象
	 */
	public static void test02() {
		Runnable runnable = () -> {
			throw new ArrayIndexOutOfBoundsException();
		};
		Thread thread = new Thread(runnable);
		// 获取未捕获异常的处理器，如果没有为线程安装处理器，那么处理器就是该线程的ThreadGroup对象
		// TODO 线程组是可以一起管理线程的集合，默认下创建的所有线程都属于同一个线程组(也可以建立其他的线程组)
		System.out.println(thread.getUncaughtExceptionHandler()); //java.lang.ThreadGroup[name=main,maxpri=10]
		// 设置未捕获异常的处理器
		thread.setUncaughtExceptionHandler(new MyUncaughtException());
		thread.start();
	}

	/**
	 * 3. 引出默认处理器，这个处理器只有在线程组没有专有的未捕获异常处理器时才调用
	 */
	public static void test03() {
		Runnable runnable = () -> {
			throw new ArrayIndexOutOfBoundsException();
		};
		Thread thread = new Thread(runnable);
		// TODO Thread有两个静态方法，用来获取或设置默认处理器
		Thread.UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		System.out.println(uncaughtExceptionHandler); // null，如果没有安装默认处理器getXXX()就会返回null
		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtException());

		thread.start();
	}

	public static void main(String[] args) {
		// test02();

		Thread thread = new Thread();
		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtException());
		System.out.println(Thread.getDefaultUncaughtExceptionHandler());
		System.out.println(thread.getUncaughtExceptionHandler());
	}
}

/**
 * 自定义的未捕获异常处理器，将异常输出到 uncaughtException.log 文件中
 */
class MyUncaughtException implements Thread.UncaughtExceptionHandler {
	private static final String LOG_PATH = "src/javastudy/thread_/uncaughtException.log";

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		Logger logger = Logger.getLogger(this.getClass().getPackageName());
		logger.setUseParentHandlers(false); //不使用顶级日志的控制台处理器
		try {
			FileHandler fileHandler = new FileHandler(LOG_PATH, true);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
			logger.addHandler(fileHandler);
			logger.setLevel(Level.ALL);
			fileHandler.setLevel(Level.ALL);
			// 记录异常
			logger.throwing(t.getClass().getName(), "run", e);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
/*TODO 源码中 uncaughtException方法的实现
public void uncaughtException(Thread t, Throwable e) {
    // 1. 如果该线程组有父线程组，那么调用父线程组的uncaughtException()方法
    if (parent != null) {
        parent.uncaughtException(t, e);
    } else {
        // 2. 如果默认处理器非null，就调用该处理器
        Thread.UncaughtExceptionHandler ueh =
            Thread.getDefaultUncaughtExceptionHandler();
        if (ueh != null) {
            ueh.uncaughtException(t, e);
        }
        // 3. 如果Throwable是ThreadDeath(调用stop()方法会产生这样一个异常)的一个实例，什么也不做，否则输出堆栈轨迹
        else if (!(e instanceof ThreadDeath)) {
            System.err.print("Exception in thread \""
                             + t.getName() + "\" ");
            e.printStackTrace(System.err);
        }
    }
}
 */
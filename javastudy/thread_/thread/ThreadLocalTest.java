package javastudy.thread_.thread;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 线程局部变量类的演示
 */
public class ThreadLocalTest {
	public static void main(String[] args) throws InterruptedException {
        /* TODO 线程局部变量
            1. 在线程间尽量避免共享变量，SimpleDateFormat类不是线程安全的，并且多个线程共用一个Random类都是很低效的，Scanner更不好
            2. 可以使用ThreadLocal辅助类为各个线程提供各自的实例
            3. 源码是维护着一个<Thread,T>的map映射，键就是当前线程对象，值为泛型类型
         */

		MyTime myTime1 = new MyTime();
		MyTime myTime2 = new MyTime();
		MyTime myTime3 = new MyTime();
		Thread thread1 = new Thread(myTime1);
		Thread thread2 = new Thread(myTime2);
		Thread thread3 = new Thread(myTime3);

		thread1.start();
		thread2.start();
		thread3.start();
		// 这里延时一下，让子线程运行完
		Thread.sleep(100);

		System.out.println(myTime1.format == myTime2.format); //false
	}

	private static class MyTime implements Runnable {
		// 构造一个公共ThreadLocal类，并提供初始值(生成的对象)
		public static final ThreadLocal<SimpleDateFormat> dateFormat =
				ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

		// 重写initialValue()方法，为get()提供初始值，不如上面那个简单
		public static final ThreadLocal<Integer> integer = new ThreadLocal<>() {
			private Random random = new Random();

			protected Integer initialValue() {
				return random.nextInt(1000);
			}
		};

		public SimpleDateFormat format;

		@Override
		public void run() {
			// 使用get()得到当前线程的值，如果是第一次调用会调用参数里的lambda表达式，之后get()返回的都是属于当前线程的那个实例
			format = dateFormat.get();
			// 为这个线程设置一个新值
			// dateFormat.set(new SimpleDateFormat("yy-MM-dd"));
			// 删除
			// dateFormat.remove();

			// 如果是获取随机数的话，可以是ThreadLocal<Random>或者更方便的是ThreadLocalRandom.current()，返回特定于当前线程的Random实例
			ThreadLocalRandom current = ThreadLocalRandom.current();
		}
	}
}

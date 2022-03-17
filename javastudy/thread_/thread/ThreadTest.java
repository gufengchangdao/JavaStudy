/*
Thread
    Runnable <--- Thread
    Thread.State   Thread的内部枚举类，表示线程状态
 */
package javastudy.thread_.thread;

import java.util.concurrent.TimeUnit;

public class ThreadTest {
	public static void main(String[] args) {
		Cat cat = new Cat(); //请把这个cat看成Thread对象

		//普通方法
		System.out.println("*************普通方法*************");
		//启动线程、
		cat.start();    //start会间接调用run方法

		//返回此线程的标识符。
		System.out.println("线程标识符" + cat.getId());//15
		//返回此线程的名称。
		System.out.println("线程名" + cat.getName());//Thread-0
		//将此线程的名称更改为等于参数 name
		cat.setName("main_1");
		//返回此线程的优先级。
		System.out.println("优先级" + cat.getPriority());//5
		//更改此线程的优先级
		//优先级分类：MIN_PRIORITY = 1    NORM_PRIORITY = 5    MAX_PRIORITY = 10
		cat.setPriority(10);//如果值超过[1,10],就会抛异常
		//测试这个线程是否活着。
		System.out.println("是否活着" + cat.isAlive());//true
		//将此线程标记为daemon线程或用户线程
//        cat.setDaemon(true);  需要在线程启动前(调用start前)调用该方法
		//测试这个线程是否是守护线程
		System.out.println("是否是守护线程" + cat.isDaemon());//false
		//直接调用run方法就只是在main线程中调用该方法，不会启动新线程
//        cat.run();//main正在运行
		//返回此线程的字符串表示，包括线程的名称，优先级和线程组
		System.out.println("线程信息" + cat.toString());//Thread[main_1,10,main]，因为线程名改为main_1了，优先级改为10了
		//返回此线程的状态
		System.out.println("线程状态" + cat.getState().name());//TIMED_WAITING
		//中断线程，但并没有真正的结束线程，所以一般用于中断正在休眠进程
		//sleep方法一般是try-catch的，interrupt中断休眠会导致对象抛出异常被捕捉
		// cat.interrupt();
		//插队
		// try {
		//     cat.join(); //从这里开始，中断主线程，执行cat线程，直到cat线程结束为止再继续执行主线程，一定会成功，可被中断
		// } catch (InterruptedException e) { //异常被捕获时会清理中断标记
		//     e.printStackTrace();
		// }

		//static方法
		System.out.println("*************静态方法*************");
		//返回当前线程的thread group及其子组中活动线程数的估计。
		System.out.println("当前线程数：" + Thread.activeCount() + "");//***当前线程数：3***
		//currentThread返回对当前正在执行的线程对象的引用。
		System.out.println(Thread.currentThread());//Thread[main,5,main]
		//测试当前线程是否中断
		System.out.println("当前线程是否中断:" + Thread.interrupted());//当前线程是否中断:false
		//对调度程序的一个暗示，即当前线程愿意产生当前使用的处理器
		Thread.yield();//线程的礼让，礼让时间不确定，也不一定礼让成功

		//延时，可被中断
		try {
			Thread.sleep(1000); //延时1s
			TimeUnit.SECONDS.sleep(1); //TimeUnit提供了更方便的休眠方法
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		for (int i = 0; i < 10; i++) {
			if (i == 6)
				cat.loop = false;//可从外部控制线程结束

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("*主线程运行 " + i);
		}
	}
}

class Cat extends Thread {
	public static int counts;   //Thread的子类中有static的属性，也可以达到多个线程共享一个资源的效果

	public boolean loop = true;

	@Override
	//重写run方法，写上自己的业务逻辑
	public void run() {
		while (loop) {
			//currentThread获取当前线程对象
			//getName获取线程名
			System.out.println("*" + Thread.currentThread().getName() + "正在运行");    //Thread-0正在运行

			try {
				Thread.sleep(1000); //延时方法
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Thread thread = new Thread() {  //除了定义类继承Thread以外，还可以创建匿名线程对象
		@Override
		public void run() {
			//...
		}
	};

}
/*
start方法和run方法关系：
    直接调用run方法相当于只是在当前线程中调用run方法，不会启动线程
    start --> start0 --> run
    start0是本地(native)的方法，由于JVM机调用，private无法直接调用，start0才是真正实现多线程的方法
    start调用start0方法后，该线程并不一定会立马执行，只是将线程变成了可运行状态。具体什么时候执行取决于CPU，由CPU统一调度

 */
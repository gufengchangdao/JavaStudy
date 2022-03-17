/*
用户线程和守护线程
    用户线程：也叫工作线程，当线程的任务执行完或通知方式结束
    守护线程：一般是为工作线程服务的，当所有的用户线程(包括子线程)结束，守护线程自动结束。常见的守护线程：垃圾回收装置

守护线程可以用来管理、监控一些信息，利于多线程的管理
 */
package javastudy.thread_.thread;

import java.util.concurrent.TimeUnit;

/**
 * 演示守护线程的使用
 */
public class DaemonDemo {
	/*
	Daemon
	1. 非守护线程全部死亡后，JVM会立即关闭所有的守护线程，finally字句也不会被运行，这点可能不太优雅，可以使用Executor那种抛出中断异常的信息来替代
	*/
	public static void main(String[] args) throws InterruptedException {
		Runnable runnable = () -> {
			System.out.println("臣守护您一生");
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("finally{}");
			}
		};

		Thread thread = new Thread(runnable);
		thread.setDaemon(true); //设置为守护线程

		thread.start();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("主线程的一生过去了");    //所有工作线程结束了守护线程也会被强制结束
	}
}
/*
臣守护您一生
主线程的一生过去了
*/
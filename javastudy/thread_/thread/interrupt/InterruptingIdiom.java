package javastudy.thread_.thread.interrupt;


import java.util.concurrent.TimeUnit;


// 需要清理的资源
class NeedsCleanup {
	private final int id;

	public NeedsCleanup(int ident) {
		id = ident;
		System.out.println("需要清理 " + id);
	}

	public void cleanup() {
		System.out.println("清理完成 " + id);
	}
}

class Blocked implements Runnable {
	private volatile double d = 0.0;

	// TODO: 2022/3/6 通常所有需要清理的对象创建操作的后面都紧跟try-finally字句，从而无论run()方法如何退出都能保证资源的清理
	public void run() {
		try { //捕获休眠操作被中断抛出的异常
			while (!Thread.interrupted()) { //检查中断标记并清理标记
				 NeedsCleanup n1 = new  NeedsCleanup(1);
				try { //保证n1资源的清理，就算抛出中断异常也能完成清理
					System.out.println("sleep");
					TimeUnit.SECONDS.sleep(1);
					 NeedsCleanup n2 = new  NeedsCleanup(2);
					try { //保证n2资源的清理
						System.out.println("开始耗时计算");
						for (int i = 1; i < 2500000; i++)
							d = d + (Math.PI + Math.E) / d;
						System.out.println("耗时计算结束");
					} finally {
						n2.cleanup();
					}
				} finally {
					n1.cleanup();
				}
			}
			System.out.println("while循环结束");
		} catch (InterruptedException e) {
			System.out.println("获取中断异常，程序结束");
		}
	}
}


/**
 * 演示常用中断操作
 */
public class InterruptingIdiom {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new Blocked());
		t.start();
		TimeUnit.MILLISECONDS.sleep(Integer.parseInt("100"));
		t.interrupt();
	}
}

package javastudy.keyword_;

/**
 * volatile关键字演示
 */
public class VolatileDemo {
	private static volatile int count = 0;

	// private static void increase() {
	private static synchronized void increase() {
		count++; //非原子操作
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				for (int j = 0; j < 10000; j++) {
					increase();
				}
			}).start();
		}
		// 所有线程累加完成后输出
		while (Thread.activeCount() > 2) Thread.yield();
		System.out.println(count);
	}
}
// Volatile原理
// 	1. 保证此变量对所有的线程的可见性。当一个线程修改了这个变量的值，volatile 保证了新值能立即同步到主内存，以及每次使用前立即从主内存刷新。但
// 	普通变 量做不到这点，普通变量的值在线程间传递均需要通过主内存来完成。
// 	2. 禁止指令重排序优化。（什么是指令重排序：是指CPU采用了允许将多条指令不按程序规定的顺序分开发送给各相应电路单元处理）。
// 	3. 在访问volatile变量时不会执行加锁操作，因此也就不会使执行线程阻塞，因此volatile变量是一种比synchronized关键字更轻量级的同步机制。
// 	4. volatile 的读性能消耗与普通变量几乎相同，但是写操作稍慢，因为它需要在本地代码中插入许多内存屏障指令来保证处理器不发生乱序执行

// 原子操作示例
class Atomic {
	private volatile boolean done;
	private volatile int count = 0;

	// 当done或count的值改变了，其他线程调用该方法可以立马看到
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done; //直接赋值是原子性操作
	}

	public void flipDone() {
		done = !done; //该操作不是原子性操作，不能保证读取、翻转和写入不被中断
	}

	public void addCount() {
		count++; // 自增不是原子操作
		//1. 从内存读取 count 的值
		//2. 执行 count + 1
		//3. 将 count 的新值写回
	}
}

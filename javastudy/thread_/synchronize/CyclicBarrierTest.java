package javastudy.thread_.synchronize;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ClimbStairs implements Runnable {
	private int floor;
	private String name;
	private CyclicBarrier cb;

	public ClimbStairs(String name, CyclicBarrier cb) {
		this.name = name;
		this.cb = cb;
	}

	@Override
	public void run() {
		try {
			while (floor < 10) {
				Thread.sleep((int) (new Random().nextFloat() * 1000));
				floor++;
				// 返回目前正在等待障碍的各方的数量。
				System.out.println(this + "爬到了第" + floor + "层, 这一层目前有" + cb.getNumberWaiting() + "人");
				// 等待指定数量的成员调用await()
				cb.await();
			}
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return name;
	}
}

/**
 * 使用CyclicBarrier模拟多人一同爬楼梯
 */
public class CyclicBarrierTest {
	/*
	TODO CyclicBarrier
	 1. 允许一组线程全部等待彼此达到共同屏障点的同步辅助。 循环阻塞在涉及固定大小的线程的程序中很有用，这些线程必须偶尔等待彼此。它可以在等待的线
	    程被释放之后重新使用。
	 2. 像CountDownLatch,但是这个类还可以使用reset()将屏障重置为初始状态
	 */
	public static void main(String[] args) {
		// 当给定数量的线程（线程）等待时，它将跳闸，当屏障跳闸时执行给定的屏障动作，由最后一个进入屏障的线程执行。
		CyclicBarrier cb = new CyclicBarrier(7, () -> {
			System.out.println("7个人汇合后又一起爬楼...");
		});
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < 7; i++) {
			es.execute(new ClimbStairs(i + "号", cb));
		}
		es.shutdown();
	}
}

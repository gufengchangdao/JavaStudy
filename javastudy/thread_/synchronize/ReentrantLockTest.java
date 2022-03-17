package javastudy.thread_.synchronize;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock类来实现代码同步
 */
public class ReentrantLockTest {
	public static void main(String[] args) {
		SellTicket2 sellTicket2 = new SellTicket2();
		Thread thread1 = new Thread(sellTicket2);
		Thread thread2 = new Thread(sellTicket2);
		Thread thread3 = new Thread(sellTicket2);

		//3个窗口在卖票
		thread1.start();
		thread2.start();
		thread3.start();
	}
}

class SellTicket2 implements Runnable {
	private int tickets = 100;    //总票数
	// 构造重入锁
	private ReentrantLock sellSock = new ReentrantLock();
	// 构造公平锁，一个公平锁倾向于等待时间最长的线程，并且公平锁要比常规锁慢很多，而且不一定很公平
	// private ReentrantLock sellSock = new ReentrantLock(true);

	@Override
	public void run() {
		while (true) {
            /*TODO ReentrantLock类
                1. 在lock()和unlock()之间的区域叫临界区，线程锁定了锁对象后，其他线程就会卡在lock()处，直到第一个线程是释放这个锁对象
                2. 最好把unlock操作放到finally字句里面，确保即使是抛出一个异常也能把锁释放掉，否则其他线程将永远阻塞
                3. 这个锁称为重入锁，因为线程可以反复获得已拥有的锁，锁有一个持有计数来跟踪对lock方法的嵌套调用,因此被一个锁保护的代码可以调用
                另一个使用相同锁的方法
                4. 显示的调用锁在一些方面比synchronized好，像更加灵活，synchronized中的代码块抛异常后也无法清理工作，但是ReentrantLock
                可以在finally里面处理，后者还能尝试获取锁一段时间然后放弃
            */
			// 1. 获取锁，其他线程会被卡在这个方法里面
			sellSock.lock();
			// 2. 尝试获取锁，不会阻塞
			// boolean b = sellSock.tryLock();
			// 3. 设置等待时长
			// try {
			//     boolean b = sellSock.tryLock(2, TimeUnit.SECONDS);
			// } catch (InterruptedException e) {
			//     e.printStackTrace();
			// }
			try {
				if (tickets <= 0) {
					System.out.println("**********票卖完了**********");
					break;
				}

				// 故意把判断和修改操作中间隔开，进行休眠，使得到的数据不准确
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "卖了一张票，还剩 " + (--tickets));
			} finally {
				sellSock.unlock(); //释放锁
			}
		}
	}
}
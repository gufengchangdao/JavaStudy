package javastudy.thread_.synchronize;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条件对象的使用
 * 模拟卖票和补充票并发执行
 */
public class ConditionTest {
	public static void main(String[] args) {
		// 两个线程卖票，一个线程补充票
		Thread thread1 = new Thread(new SellTicket3(-1, 101));
		Thread thread2 = new Thread(new SellTicket3(-1, 9));
		Thread thread3 = new Thread(new SellTicket3(1, 10));

		thread1.start();
		thread2.start();
		thread3.start();
	}
}

class SellTicket3 implements Runnable {
	// 创建了三个对象，票数、重入锁和条件对象就得是一样的
	private static int tickets = 100;    //总票数
	private static ReentrantLock sellSock = new ReentrantLock();
	// TODO Condition
	//  1. java 1.5中才出现的，它用来替代传统的Object的wait()、notify()实现线程间的协作
	//  2. 一个锁对象可以有一个或多个相关联的条件对象，使用一次newCondition()就创建一个对象
	//  3. 常配合while使用用来满足条件(可能是保持某种状态或是保证资源足够)
	private static Condition ticketEnough = sellSock.newCondition(); //条件对象

	private int step; //对票的操作，为1就是补充票，为-1就是卖票
	private int already = 0; //已完成的票数
	private int target; //目标

	public SellTicket3(int step, int target) {
		this.step = step;
		this.target = target;
	}

	public void run() {
		while (true) {
			sellSock.lock(); //获取锁，其他线程会被卡在这个方法里面
			try {
				// 达成目标就结束线程
				if (already == target) {
					System.out.println("----------- " + Thread.currentThread().getName() + "一共" + (step > 0 ? "补充" : "卖")
							+ "了 " + target + " 张票 -----------");
					return;
				}
				// 卖票操作
				if (step < 0) {
					if (tickets == 0) { //没票了
						System.out.println("票卖完了，" + Thread.currentThread().getName() + "还缺" + (target - already) + "张票，等待补充...");
						// break;
						// TODO 1.await()将该线程放在这个锁相关联的等待集中
						//  2. 调用该方法，线程就会暂停，并放弃锁，进入一个等待集中，此时其他线程就有机会调用该方法
						while (tickets == 0) //没票后，线程被阻塞
							ticketEnough.await();
						// ticketEnough.awaitNanos(1000); //这个方法可以指定等待的时间，直到时间到或被signal唤醒或被中断
					}
					System.out.println(Thread.currentThread().getName() + "卖了一张票，还剩 " + (--tickets));
				}
				// 补充票操作
				else {
					System.out.println(Thread.currentThread().getName() + "补充了一张票，还剩 " + (++tickets));
					// TODO 1. signalAll()解除该条件等待集中所有线程的阻塞状态
					//  2. 方法会激活等待这个条件的所有线程，其中一个线程会从await()调用返回，得到锁后，从暂停的地方继续执行
					//  3. 注意signalAll()方法只是通知等待的线程，不一定满足条件(让这个线程等待的条件)，一般使用while循环中执行await()，
					//  对线程再次进行检查，不满足还让其进入等待集
					//  4. signal()会随机选择等待集中的一个线程解除阻塞，更加高效，但也更危险，如果解除的唯一线程仍不能运行就会再次阻塞(因
					//  为用了while循环)，系统进入死锁，使用该方法的条件是所有的任务等待相同的条件
					if (tickets >= 10) //只有票数>=10了其他线程才能继续卖票
						ticketEnough.signalAll();
				}
				already++; // 运行到这里说明没有被阻塞，就是成功卖或者补充了一票
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				sellSock.unlock(); //释放锁
			}
		}
	}
}
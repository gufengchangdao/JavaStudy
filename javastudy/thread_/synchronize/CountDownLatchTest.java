package javastudy.thread_.synchronize;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Relative implements Runnable {
	private Child child;
	private CountDownLatch cdl;

	public Relative(Child child, CountDownLatch cdl) {
		this.child = child;
		this.cdl = cdl;
	}

	@Override
	public void run() {
		int money = new Random().nextInt(100) + 50;
		System.out.println("亲戚给你了零花钱 " + money);
		child.getMoney(money);
		cdl.countDown();
	}
}

class Child implements Runnable {
	private CountDownLatch cdl;
	private int pocketMoney;

	public Child(CountDownLatch cdl) {
		this.cdl = cdl;
	}

	public void getMoney(int count) {
		pocketMoney += count;
	}

	@Override
	public void run() {
		try {
			cdl.await();
			getMother(); //该给的都给了，妈妈开始收取你的零花钱了
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void getMother() {
		System.out.println("你把零花钱(" + pocketMoney + ")都给你妈妈存起来了，妈妈还奖励你了全套“五三\"");
		pocketMoney = 0;
	}
}

/**
 * 使用CountDownLatch模拟亲戚给零花钱然后被父母收回的情景
 */
public class CountDownLatchTest {
	/*
	TODO CountDownLatch
	 1. 允许一个或多个线程等待直到在其他线程中执行的一组操作完成的同步辅助
	 2. CountDownLatch用给定的计数初始化。 await方法阻塞，直到由于countDown()方法的调用而导致当前计数达到零，之后所有等待线程被释放，并且
	    任何后续的await 调用立即返回。 这是一个一次性的对象-计数无法重置。 如果需要重置计数的版本，使用CyclicBarrier
	 3. 一个CountDownLatch初始化N可以用来做一个线程等待，直到N个线程完成某项操作，或某些动作已经完成N次
	 4. 代用countDown()不会被阻塞，只是减少计数
	 */
	public static void main(String[] args) {
		CountDownLatch cdl = new CountDownLatch(20); //计数20次
		ExecutorService es = Executors.newCachedThreadPool();
		Child child = new Child(cdl); //you
		es.execute(child);
		for (int i = 0; i < 20; i++) { //20个亲戚给你零花钱
			es.execute(new Relative(child, cdl));
		}
		es.shutdown();
	}
}

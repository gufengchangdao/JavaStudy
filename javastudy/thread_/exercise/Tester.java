package javastudy.thread_.exercise;
// Framework to test performance of concurrency containers.

import javastudy.utilities.RandomGenerator;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程安全集合性能的测试框架
 * @param <C> 要测定的集合类型
 */
public abstract class Tester<C> {
	static int testReps = 10; //总测试次数
	static int testCycles = 1000; //单独读取/写入测试次数
	static int containerSize = 1000;

	// 初始化容器
	abstract C containerInitializer();

	// 使用多线程同时开启读取和写入，进行测试，使用线程池并调用内部类的方法
	abstract void startReadersAndWriters();

	C testContainer; //测试容器
	String testId;
	int nReaders;
	int nWriters;
	volatile long readResult = 0;
	volatile long readTime = 0;
	volatile long writeTime = 0;
	CountDownLatch endLatch;
	static ExecutorService exec = Executors.newCachedThreadPool();
	Integer[] writeData;
	Random random = new Random();

	Tester(String testId, int nReaders, int nWriters) {
		this.testId = testId + " " + nReaders + "r " + nWriters + "w";
		this.nReaders = nReaders;
		this.nWriters = nWriters;
		writeData = RandomGenerator.Integer.getIntegerArray(containerSize);
		for (int i = 0; i < testReps; i++) {
			runTest();
			readTime = 0;
			writeTime = 0;
		}
	}

	// 表示一次测试
	void runTest() {
		endLatch = new CountDownLatch(nReaders + nWriters);
		testContainer = containerInitializer();
		startReadersAndWriters();
		try {
			endLatch.await(); //一次测试完成后再打印
		} catch (InterruptedException ex) {
			System.out.println("endLatch interrupted");
		}
		System.out.printf("%-27s %14d %14d\n", testId, readTime, writeTime);
		if (readTime != 0 && writeTime != 0)
			System.out.printf("%-27s %14d\n", "readTime + writeTime =", readTime + writeTime);
	}

	// 测试类，用来测试读和写
	abstract class TestTask implements Runnable {
		abstract void test();

		abstract void putResults();

		long duration;

		public void run() {
			long startTime = System.nanoTime();
			test();
			duration = System.nanoTime() - startTime;
			synchronized (Tester.this) { //使用类对象作为锁的
				putResults(); //计算总时间
			}
			endLatch.countDown();
		}
	}

	public static void initMain(String[] args) {
		if (args.length > 0)
			testReps = new Integer(args[0]);
		if (args.length > 1)
			testCycles = new Integer(args[1]);
		if (args.length > 2)
			containerSize = new Integer(args[2]);
		System.out.printf("%-27s %14s %14s\n", "Type", "Read time", "Write time");
	}
} ///:~

package javastudy.thread_.synchronize;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 演示ReaderWriter的简单使用
 */
public class ReentrantReadWriteLockTest {
	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);

	/*
	TODO ReaderWriterList
	 1. ReadWriteLock维护一对关联的locks，一个用于只读操作，一个用于写入。可以由多个阅读器线程同时进行，只要没有写入。写锁如果被持有的话其他
	 读取者都不能访问
	 2. 如果使用非公平锁，可能会无限期地读或写，但是会比公平锁有更高的吞吐量
	 */
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.execute(new Writer());
		pool.execute(new Reader());
		pool.execute(new Reader());
		pool.execute(new Reader()); //三个读取操作同时完成
		pool.shutdown();
	}

	private static class Reader implements Runnable {

		@Override
		public void run() {
			ReentrantReadWriteLock.ReadLock readLock = rwl.readLock();
			readLock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + " 读取公共资源内容");
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				readLock.unlock();
			}
			System.out.println("读取完毕");
		}
	}

	private static class Writer implements Runnable {

		@Override
		public void run() {
			ReentrantReadWriteLock.WriteLock writeLock = rwl.writeLock();
			writeLock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + " 写入公共资源内容");
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				writeLock.unlock();
			}
			System.out.println("写入完毕");
		}
	}
}

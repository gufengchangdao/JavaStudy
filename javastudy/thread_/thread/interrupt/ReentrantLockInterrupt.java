package javastudy.thread_.thread.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BlockedMutex {
	private Lock lock = new ReentrantLock();
	public Thread thread;

	public BlockedMutex() {
		lock.lock();//获取到锁
	}

	public void f() {
		Runnable runnable = () -> {
			try {
				lock.lockInterruptibly(); //获取锁，这里会阻塞，直到获取到锁或被中断
				System.out.println("lock acquired in f()");
			} catch (InterruptedException e) {
				System.out.println("Interrupted from lock acquisition in f()");
			}
		};
		thread = new Thread(runnable);
		thread.start();
	}
}

/**
 * 演示使用interrupt()中断ReentrantLock获取锁时出现的阻塞
 */
public class ReentrantLockInterrupt {
	public static void main(String[] args) throws InterruptedException {
		BlockedMutex blockedMutex = new BlockedMutex();
		blockedMutex.f();

		TimeUnit.SECONDS.sleep(2);
		blockedMutex.thread.interrupt();
	}
}
/*
Interrupted from lock acquisition in f()
 */
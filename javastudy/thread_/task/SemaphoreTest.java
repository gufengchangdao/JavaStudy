package javastudy.thread_.task;//: concurrency/Pool.java
// Using a Semaphore inside a Pool, to restrict
// the number of tasks that can use a resource.

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
/*
Semaphore
1. 一个计数信号量。 在概念上，信号量维持一组许可证。 如果有必要，每个acquire()都会阻塞，直到许可证可用，然后才能使用它。 每个release()添加许
   可证，潜在地释放阻塞获取方。 但是，没有使用实际的许可证对象; Semaphore只保留可用数量的计数，并相应地执行。
2. 在获得项目之前，每个线程必须从信号量获取许可证，以确保某个项目可用。 当线程完成该项目后，它将返回到池中，并将许可证返回到信号量，允许另一个线
   程获取该项目。 请注意，当调用acquire()时，不会保持同步锁定，因为这将阻止某个项目返回到池中。 信号量封装了限制对池的访问所需的同步，与保持池
   本身一致性所需的任何同步分开。
 */

/**
 * 使用Semaphore做一个对象池
 *
 * @param <T> 对象类型
 */
public class SemaphoreTest<T> {
	private int size;
	private List<T> items = new ArrayList<T>();
	private volatile boolean[] checkedOut; //是否签出
	private Semaphore available; //计数信号量

	public SemaphoreTest(Class<T> classObject, int size) {
		this.size = size;
		checkedOut = new boolean[size];
		available = new Semaphore(size, true);//传入许可证的个数
		for (int i = 0; i < size; ++i)
			try {
				items.add(classObject.getConstructor().newInstance());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}

	// 获取
	public T checkOut() throws InterruptedException {
		// 从此信号量获取许可证，阻塞直到可用
		available.acquire();
		// boolean b = available.tryAcquire();//立即返回的方法
		return getItem();
	}

	public void checkIn(T x) {
		if (releaseItem(x))
			// 释放许可证，将其返回到信号量,将可用许可证的数量增加一个。
			available.release();
	}

	private synchronized T getItem() { //设置了同步
		for (int i = 0; i < size; ++i)
			if (!checkedOut[i]) {
				checkedOut[i] = true;
				return items.get(i);
			}
		return null; // Semaphore prevents reaching here
	}

	private synchronized boolean releaseItem(T item) {
		int index = items.indexOf(item);
		if (index == -1) return false; // Not in the list
		if (checkedOut[index]) {
			checkedOut[index] = false;
			return true;
		}
		return false; // Wasn't checked out
	}

	public static void main(String[] args) throws InterruptedException {
		SemaphoreTest<Date> st = new SemaphoreTest<Date>(Date.class,3);
		Date date1 = st.checkOut();
		Date date2 = st.checkOut();
		new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println(Thread.currentThread()+"线程获取池中对象");
				st.checkOut();
				System.out.println(Thread.currentThread()+"获取成功");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		Date date3 = st.checkOut();
		TimeUnit.SECONDS.sleep(3);
		st.checkIn(date3);
		System.out.println("主线程放回对象");
	}
} ///:~

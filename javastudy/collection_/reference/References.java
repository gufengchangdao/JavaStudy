package javastudy.collection_.reference;
// Demonstrates Reference objects

import java.lang.ref.*;
import java.util.LinkedList;
/*
1. Reference 是一个抽象类，子类分别为
	强引用(StrongReference) 不断开引用的话，抛异常也不会被回收
	软引用(SoftReference) 空间足够就不回收，不够再回收，可用来实现内存敏感的高速缓存，使用get()获取对象，如果对象已经被回收返回null
	弱引用(WeakReference)无论内存是否充足，都会回收被弱引用关联的对象
	虚引用(PhantomReference) 如果一个对象与虚引用关联，则跟没有引用与之关联一样，在任何时候都可能被垃圾回收器回收
	引用队列(ReferenceQueue) 回收前清理工作的工具

2. SoftReference、WeakReference、PhantomReference 引用的对象既可以使用，又可以在内存耗尽前释放掉
3. 当上面对象所引用的对象被回收后，Reference对象还存在，这时需要引用队列来帮忙回收这些对象，Reference和引用队列ReferenceQueue联合使用时，如
   果Reference持有的对象被垃圾回收，Java虚拟机就会把这个引用加入到与之关联的引用队列中。poll会返回存入Reference对象，用以检查哪个Reference
   所软引用的对象已经被回收
4. 使用了上面三种引用就不要使用强引用了(就是直接赋值)，不然不起作用
 */

/**
 * 演示Reference子类的使用
 */
public class References {
	private static ReferenceQueue<VeryBig> rq = new ReferenceQueue<VeryBig>();
	private static int count = 0;

	/**
	 * 检查引用队列中是否有对象被回收
	 */
	public static void checkQueue() {
		Reference<? extends VeryBig> inq = rq.poll(); //弹出一个Reference，如果没null表示队列为空，没有Reference引用的对象被回收
		if (inq != null) //不为null说明inq引用的对象已经被回收了
			System.out.println("回收了" + (++count) + "个被引用对象");
	}

	public static void main(String[] args) {
		int size = 10;
		if (args.length > 0)
			size = Integer.parseInt(args[0]);
		LinkedList<SoftReference<VeryBig>> sa = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			sa.add(new SoftReference<>(new VeryBig("Soft " + i), rq)); //要使用引用队列管理好软引用就需要把队列作为第二个参数
			System.out.println("创建对象 " + sa.getLast().getClass().getSimpleName()
					+ ", 引用的对象为" + sa.getLast().get()); //使用get来获取被引用的对象
			checkQueue();
		}
		LinkedList<WeakReference<VeryBig>> wa = new LinkedList<WeakReference<VeryBig>>();
		for (int i = 0; i < size; i++) {
			wa.add(new WeakReference<>(new VeryBig("Weak " + i), rq));
			System.out.println("创建对象 " + wa.getLast().getClass().getSimpleName() + ", 引用的对象为" + wa.getLast().get());
			checkQueue();
		}
		SoftReference<VeryBig> s = new SoftReference<VeryBig>(new VeryBig("Soft"));
		WeakReference<VeryBig> w = new WeakReference<VeryBig>(new VeryBig("Weak"));
		System.gc();
		LinkedList<PhantomReference<VeryBig>> pa = new LinkedList<PhantomReference<VeryBig>>();
		for (int i = 0; i < size; i++) {
			pa.add(new PhantomReference<>(new VeryBig("Phantom " + i), rq));
			System.out.println("创建对象 " + pa.getLast().getClass().getSimpleName() + ", 引用的对象为" + pa.getLast().get());
			checkQueue();
		}
	}
}

class VeryBig {
	private static final int SIZE = 10000;
	private long[] la = new long[SIZE];
	private String ident;

	public VeryBig(String ident) {
		this.ident = ident;
	}

	public String toString() {
		return ident;
	}

	protected void finalize() {
		System.out.println("回收对象：" + ident);
	}
}
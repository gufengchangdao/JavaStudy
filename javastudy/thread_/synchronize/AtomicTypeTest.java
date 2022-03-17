package javastudy.thread_.synchronize;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * 演示具有原子性操作的java.util.concurrent.atomic下的类，
 * 主要是保证多线程下能获取到正确的值，
 * 这里只演示关于long型数据
 */
public class AtomicTypeTest {
	public static void main(String[] args) {
		// TODO: 2022/1/14 atomicLong
		// 创建对象
		AtomicLong atomicLong = new AtomicLong();
		// AtomicLongArray atomicLongArray = new AtomicLongArray(10);
		// AtomicReference<Object> objectAtomicReference = new AtomicReference<>();

		// 以原子的方式自增和自减
		// 也就是说 获取值、加1和设置不会被中断，保证即使是多线程并发地访问一个实例，也会计算并返回正确的值
		long id = atomicLong.incrementAndGet();
		long id2 = atomicLong.decrementAndGet();
		long andAdd = atomicLong.getAndAdd(2L); //返回以前的值
		long l = atomicLong.addAndGet(2L); //返回更新后的值

        // 设置值
		// atomicLong.set(Math.max(atomicLong.get(),100L)); //这个操作不行，这个操作不是原子的
		atomicLong.updateAndGet(x -> Math.max(x, 100L));
		// atomicLong.accumulateAndGet(100L,Math::max);

		// TODO: 2022/1/14 LongAdder
		// 1. 如果有大量线程要访问相同的原子值，性能会大幅度下降，这时候需要使用LongAdder和LongAccumulator类解决
		// 2. LongAdder包括多个变量(加数)，其总和为当前值。可以有多个线程更新不同的加数，线程个数增加时会自动提供新的加数。特别适合多个线程不
		//    断累加然后求和的情况
		LongAdder longAdder = new LongAdder();
		// 自增
		longAdder.increment();
		// 加上一个数
		longAdder.add(10);
		// 求和
		System.out.println(longAdder.sum()); //11

		// TODO: 2022/1/14 LongAccumulator
		// 1. LongAccumulator将这种思想推广到任意的累加操作，在构造器中可以提供需要进行的操作和它的零元素
		// 2. 可以选择不同的操作，这个操作必须要满足结合律和交换律，即最终结果不依赖于结合的顺序
		LongAccumulator longAccumulator1 = new LongAccumulator(Long::sum, 0); //求和
		LongAccumulator longAccumulator2 = new LongAccumulator(Long::max, Long.MIN_VALUE); //求最大值
		LongAccumulator longAccumulator3 = new LongAccumulator(Math::multiplyExact, 1); //乘法

		// 3. 在内部，这个累加器包含变量a1,a2,a3,...,an，每个变量初始化为零元素
		// 4. 调用accumulate并提供v值，其中一个变量会以原子方式更新为ai=ai op v
		// 5. 最后使用get 得到 a1 op a2 op .... op an ，也就是最终结果
		longAccumulator2.accumulate(10L);
		longAccumulator2.accumulate(100L);
		longAccumulator2.accumulate(1L);

		System.out.println(longAccumulator2.get()); //100
	}
}

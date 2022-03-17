package javastudy.lambda_.function;//: generics/Functional.java

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

// TODO 不同类型的函数对象(函数式接口)

/**
 * 结合操作
 * 函数式接口，任何类都可以组合、继承目标类，然后实现该接口，完成和某一功能的适配
 *
 * @param <T> 数据类型
 */
interface Combiner<T> {
	T combine(T x, T y);
}

// 一元运算
interface UnaryFunction<R, T> {
	R function(T x);
}

// 收集参数
interface Collector<T> extends UnaryFunction<T, T> {
	T result(); // Extract result of collecting parameter
}

// 单参判断式
interface UnaryPredicate<T> {
	boolean test(T x);
}

/**
 * 使用函数式接口编写泛型方法，仿真潜在类型机制
 * 设计模式：策略设计模式和适配器模式
 */
public class Functional {
	// TODO 泛型计算方法

	/**
	 * 组合集合中每一个元素，获取单一结果
	 * 两个参数类型分别为Iterable和Combiner，这种泛型方法是尽可能的”泛型“了，将对类型的约束降到了最低
	 * 通过不同的泛型类型和Combiner的实现类，可以自由地操作各种集合
	 *
	 * @param seq      数据集合
	 * @param combiner 操作的具体实现
	 * @param <T>      数据类型
	 * @return 运算结果
	 */
	public static <T> T reduce(Iterable<T> seq, Combiner<T> combiner) {
		Iterator<T> it = seq.iterator();
		if (it.hasNext()) {
			T result = it.next();
			while (it.hasNext())
				result = combiner.combine(result, it.next());
			return result;
		}
		// 如果链表为空
		return null; // Or throw exception
	}

	// 获取一个函数对象并在列表中的每个对象上调用它，忽略返回值。函数对象可以作为收集参数，所以最后返回。
	public static <T> Collector<T> forEach(Iterable<T> seq, Collector<T> func) {
		for (T t : seq)
			func.function(t);
		return func;
	}

	// 通过为列表中的每个对象调用函数对象来创建结果列表
	public static <R, T> List<R> transform(Iterable<T> seq, UnaryFunction<R, T> func) {
		List<R> result = new ArrayList<R>();
		for (T t : seq)
			result.add(func.function(t));
		return result;
	}

	// 将判断式应用于序列中的每个项目，并返回产生“真”的项目列表
	public static <T> List<T> filter(Iterable<T> seq, UnaryPredicate<T> pred) {
		List<T> result = new ArrayList<T>();
		for (T t : seq)
			if (pred.test(t))
				result.add(t);
		return result;
	}

	// TODO 要使用上述泛型方法，我们需要创建函数对象的实现以适应我们的特定需求
	static class IntegerAdder implements Combiner<Integer> {
		public Integer combine(Integer x, Integer y) {
			return x + y;
		}
	}

	static class IntegerSubtracter implements Combiner<Integer> {
		public Integer combine(Integer x, Integer y) {
			return x - y;
		}
	}

	static class BigDecimalAdder implements Combiner<BigDecimal> {
		public BigDecimal combine(BigDecimal x, BigDecimal y) {
			return x.add(y);
		}
	}

	static class BigIntegerAdder implements Combiner<BigInteger> {
		public BigInteger combine(BigInteger x, BigInteger y) {
			return x.add(y);
		}
	}

	static class AtomicLongAdder implements Combiner<AtomicLong> {
		public AtomicLong combine(AtomicLong x, AtomicLong y) {
			// Not clear whether this is meaningful:
			return new AtomicLong(x.addAndGet(y.get())); //原子操作
		}
	}

	// We can even make a UnaryFunction with an "ulp"
	// (Units in the last place):
	static class BigDecimalUlp implements UnaryFunction<BigDecimal, BigDecimal> {
		public BigDecimal function(BigDecimal x) {
			return x.ulp();
		}
	}

	static class GreaterThan<T extends Comparable<T>> implements UnaryPredicate<T> {
		private T bound;

		public GreaterThan(T bound) {
			this.bound = bound;
		}

		public boolean test(T x) {
			return x.compareTo(bound) > 0;
		}
	}

	static class MultiplyingIntegerCollector implements Collector<Integer> {
		private Integer val = 1;

		public Integer function(Integer x) {
			val *= x;
			return val;
		}

		public Integer result() {
			return val;
		}
	}

	public static void main(String[] args) {
		// Generics, varargs & boxing working together:
		List<Integer> li = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
		Integer result = reduce(li, new IntegerAdder());
		System.out.println(result);

		result = reduce(li, new IntegerSubtracter());
		System.out.println(result);

		System.out.println(filter(li, new GreaterThan<Integer>(4)));

		System.out.println(forEach(li, new MultiplyingIntegerCollector()).result());

		System.out.println(forEach(filter(li, new GreaterThan<Integer>(4)), new MultiplyingIntegerCollector()).result());

		MathContext mc = new MathContext(7);
		List<BigDecimal> lbd = Arrays.asList(
				new BigDecimal(1.1, mc), new BigDecimal(2.2, mc),
				new BigDecimal(3.3, mc), new BigDecimal(4.4, mc));
		BigDecimal rbd = reduce(lbd, new BigDecimalAdder());
		System.out.println(rbd);

		System.out.println(filter(lbd, new GreaterThan<BigDecimal>(new BigDecimal(3))));

		// Use the prime-generation facility of BigInteger:
		List<BigInteger> lbi = new ArrayList<BigInteger>();
		BigInteger bi = BigInteger.valueOf(11);
		for (int i = 0; i < 11; i++) {
			lbi.add(bi);
			bi = bi.nextProbablePrime();
		}
		System.out.println(lbi);

		BigInteger rbi = reduce(lbi, new BigIntegerAdder());
		System.out.println(rbi);
		// The sum of this list of primes is also prime:
		System.out.println(rbi.isProbablePrime(5));

		List<AtomicLong> lal = Arrays.asList(
				new AtomicLong(11), new AtomicLong(47),
				new AtomicLong(74), new AtomicLong(133));
		AtomicLong ral = reduce(lal, new AtomicLongAdder());
		System.out.println(ral);

		System.out.println(transform(lbd, new BigDecimalUlp()));
	}
}
/* Output:
28
-26
[5, 6, 7]
5040
210
11.000000
[3.300000, 4.400000]
[11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47]
311
true
265
[0.000001, 0.000001, 0.000001, 0.000001]
*///:~

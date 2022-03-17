package javastudy.collection_.ListTest;
// Demonstrates performance differences in Lists.
// {Args: 100 500} Small to keep build testing short

import javastudy.utilities.CountingGenerator;
import javastudy.utilities.Generator;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 使用测试框架演示对不同List实现类各种方法的测试
 */
public class ListPerformance {
	static Random rand = new Random();
	static int reps = 1000;
	// 测试对象集合，存有需要测试的数据
	static List<Test<List<Integer>>> tests = new ArrayList<>();
	static List<Test<LinkedList<Integer>>> qTests = new ArrayList<>();

	// 填充测试数据
	static {
		tests.add(new Test<>("add") {
			int test(List<Integer> list, TestParam tp) {
				int loops = tp.loops;
				int listSize = tp.size;
				for (int i = 0; i < loops; i++) {
					list.clear();
					for (int j = 0; j < listSize; j++)
						list.add(j);
				}
				return loops * listSize;
			}
		});
		tests.add(new Test<>("get") {
			int test(List<Integer> list, TestParam tp) {
				int loops = tp.loops * reps; // TODO: 2022/2/28 0028 为什么要乘以reps？
				int listSize = list.size();
				for (int i = 0; i < loops; i++)
					list.get(rand.nextInt(listSize));
				return loops;
			}
		});
		tests.add(new Test<>("set") {
			int test(List<Integer> list, TestParam tp) {
				int loops = tp.loops * reps;
				int listSize = list.size();
				for (int i = 0; i < loops; i++)
					list.set(rand.nextInt(listSize), 47);
				return loops;
			}
		});
		tests.add(new Test<>("iteradd") {
			int test(List<Integer> list, TestParam tp) {
				final int LOOPS = 1000000;
				int half = list.size() / 2;
				ListIterator<Integer> it = list.listIterator(half);
				for (int i = 0; i < LOOPS; i++)
					it.add(47);
				return LOOPS;
			}
		});
		tests.add(new Test<>("insert") {
			int test(List<Integer> list, TestParam tp) {
				int loops = tp.loops;
				for (int i = 0; i < loops; i++)
					list.add(5, 47); // Minimize random-access cost
				return loops;
			}
		});
		tests.add(new Test<>("remove") {
			int test(List<Integer> list, TestParam tp) {
				int loops = tp.loops;
				int size = tp.size;
				for (int i = 0; i < loops; i++) {
					list.clear();
					// TODO: 2022/2/28 0028 待改进，其余的操作放入初始化方法内
					list.addAll(new CountingIntegerList(size));
					// TODO: 使用索引为5是因为LInkedList会对端点进行特殊处理，又要尽量减少随机访问的代价
					while (list.size() > 5)
						list.remove(5); // Minimize random-access cost
				}
				return loops * size;
			}
		});
		// Tests for LinkedList behavior:
		qTests.add(new Test<>("addFirst") {
			int test(LinkedList<Integer> list, TestParam tp) {
				int loops = tp.loops;
				int size = tp.size;
				for (int i = 0; i < loops; i++) {
					list.clear();
					for (int j = 0; j < size; j++)
						list.addFirst(47);
				}
				return loops * size;
			}
		});
		qTests.add(new Test<>("addLast") {
			int test(LinkedList<Integer> list, TestParam tp) {
				int loops = tp.loops;
				int size = tp.size;
				for (int i = 0; i < loops; i++) {
					list.clear();
					for (int j = 0; j < size; j++)
						list.addLast(47);
				}
				return loops * size;
			}
		});
		qTests.add(new Test<>("rmFirst") {
			int test(LinkedList<Integer> list, TestParam tp) {
				int loops = tp.loops;
				int size = tp.size;
				for (int i = 0; i < loops; i++) {
					list.clear();
					list.addAll(new CountingIntegerList(size));
					while (list.size() > 0)
						list.removeFirst();
				}
				return loops * size;
			}
		});
		qTests.add(new Test<>("rmLast") {
			int test(LinkedList<Integer> list, TestParam tp) {
				int loops = tp.loops;
				int size = tp.size;
				for (int i = 0; i < loops; i++) {
					list.clear();
					list.addAll(new CountingIntegerList(size));
					while (list.size() > 0)
						list.removeLast();
				}
				return loops * size;
			}
		});
	}

	/**
	 * 创建指定类型的数组并填充
	 *
	 * @param a         数组中元素类对象
	 * @param size      数组大小
	 * @param generator 数据生成器
	 * @param <T>       数据类型
	 * @return 填充后的数组
	 */
	@SuppressWarnings("unchecked")
	private static <T> T[] getArray(Class<T> a, int size, Generator<T> generator) {
		T[] t = (T[]) Array.newInstance(a, size);
		for (int i = 0; i < size; i++) {
			t[i] = generator.next();
		}
		return t;
	}

	/**
	 * 列表测试类，用来测试List<Integer>
	 */
	static class ListTester extends Tester<List<Integer>> {
		public ListTester(List<Integer> container, List<Test<List<Integer>>> tests) {
			super(container, tests);
		}

		// 保证容器元素个数不变
		@Override
		protected List<Integer> initialize(int size) {
			container.clear();
			container.addAll(new CountingIntegerList(size));
			return container;
		}

		/**
		 * 便捷的测试方法
		 *
		 * @param list  要测试的集合对象
		 * @param tests 测试信息
		 */
		public static void run(List<Integer> list, List<Test<List<Integer>>> tests) {
			new ListTester(list, tests).timedTest();
		}
	}

	public static void main(String[] args) {
		if (args.length > 0)
			Tester.defaultParams = TestParam.array(args);
		// 测试ArrayList的get和set
		Tester<List<Integer>> arrayTest = new Tester<>(null, tests.subList(1, 3)) {
			// 每次测试前生成固定长度的List
			@Override
			protected List<Integer> initialize(int size) {
				Integer[] ia = getArray(Integer.class, size, (Generator) new CountingGenerator.Integer());
				return Arrays.asList(ia);
			}
		};
		arrayTest.setHeadline("Array as List");
		arrayTest.timedTest();

		// 测试其他实现类的各方法
		Tester.defaultParams = TestParam.array(10, 5000, 100, 5000, 1000, 1000, 10000, 200);
		if (args.length > 0)
			Tester.defaultParams = TestParam.array(args);
		ListTester.run(new ArrayList<>(), tests);
		ListTester.run(new LinkedList<>(), tests);
		ListTester.run(new Vector<>(), tests);
		Tester.fieldWidth = 12;
		Tester<LinkedList<Integer>> qTest = new Tester<>(new LinkedList<>(), qTests);
		qTest.setHeadline("LinkedList 队列方法测试");
		qTest.timedTest();
	}
}
/*
--- Array as List ---
 size     get     set
   10       9      12
  100      10      12
 1000       9      10
10000      10      11
--------------------- ArrayList ---------------------
 size     add     get     set iteradd  insert  remove
   10      49      10      12      48     299      94
  100      17       9      15      40     229      44
 1000      32       8      15      62     144      78
10000      10       9      15     290     705     323
--------------------- LinkedList ---------------------
 size     add     get     set iteradd  insert  remove
   10      79      20      22      17     101      63
  100      11      30      31       9      53      14
 1000      16     276     272       4      46       9
10000       8    2919    2932       4      49      10
----------------------- Vector -----------------------
 size     add     get     set iteradd  insert  remove
   10      52      21      23      58     212      55
  100      20      20      27      58     193      58
 1000      19      21      27      81     141      84
10000      19      20      26     308     691     343
----------------- LinkedList 队列方法测试 -----------------
 size    addFirst     addLast     rmFirst      rmLast
   10          45          43          52          63
  100          11          11          14          13
 1000           8           8          10           9
10000           5           5           7           8
*/

package javastudy.collection_.ListTest;

import java.util.List;

/**
 *
 * @param <C>
 */
public class Tester<C> {
	public static int fieldWidth = 8; //格式化的标准宽度
	public static TestParam[] defaultParams = TestParam.array(10, 5000, 100, 5000, 1000, 5000, 10000, 500);

	protected C container; //要测试的容器
	private List<Test<C>> tests; //要测试的数据
	private String headline = ""; //测试的标题
	private static int sizeWidth = 5;
	// 格式化说明符
	private static String sizeField = "%" + sizeWidth + "s";
	private TestParam[] paramList = defaultParams;

	/**
	 * 覆盖它以修改测试的初始化
	 *
	 * @param size 容器中应该满足的元素数量
	 * @return 初始化后的容器
	 */
	protected C initialize(int size) {
		return container;
	}

	// 用来打印测试数据的格式化字符串
	private static String stringField() {
		return "%" + fieldWidth + "s";
	}

	private static String numberField() {
		return "%" + fieldWidth + "d";
	}

	public Tester(C container, List<Test<C>> tests) {
		this.container = container;
		this.tests = tests;
		if (container != null)
			headline = container.getClass().getSimpleName();
	}

	public Tester(C container, List<Test<C>> tests, TestParam[] paramList) {
		this(container, tests);
		this.paramList = paramList;
	}

	public void setHeadline(String newHeadline) {
		headline = newHeadline;
	}

	// 便捷的泛型方法
	public static <C> void run(C cntnr, List<Test<C>> tests) {
		new Tester<C>(cntnr, tests).timedTest();
	}

	public static <C> void run(C cntnr, List<Test<C>> tests, TestParam[] paramList) {
		new Tester<C>(cntnr, tests, paramList).timedTest();
	}

	/**
	 * 打印测试标题和列标题
	 */
	private void displayHeader() {
		// 打印标题
		int width = fieldWidth * tests.size() + sizeWidth;
		int dashLength = width - headline.length() - 1;
		StringBuilder head = new StringBuilder(width);
		head.append("-".repeat(Math.max(0, dashLength / 2)));
		head.append(' ');
		head.append(headline);
		head.append(' ');
		head.append("-".repeat(Math.max(0, dashLength / 2)));
		System.out.println(head);
		// 打印列标题
		System.out.format(sizeField, "size");
		for (Test<?> test : tests) //打印所有测试名
			System.out.format(stringField(), test.name);
		System.out.println();
	}

	/**
	 * 执行容器列表中的每一个测试并打印
	 */
	public void timedTest() {
		displayHeader();
		for (TestParam param : paramList) {
			System.out.format(sizeField, param.size); //打印测试大小
			for (Test<C> test : tests) { //遍历所有测试
				C kontainer = initialize(param.size); //初始化
				long start = System.nanoTime();
				int reps = test.test(kontainer, param);
				long duration = System.nanoTime() - start;
				long timePerRep = duration / reps; // 计算平均数
				System.out.format(numberField(), timePerRep); //打印平均每次花费的时间
			}
			System.out.println();
		}
	}
} ///:~

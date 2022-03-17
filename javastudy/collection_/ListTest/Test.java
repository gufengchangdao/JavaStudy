package javastudy.collection_.ListTest;

/**
 * 测试类，用于测试方法
 * @param <C>
 */
public abstract class Test<C> {
	String name;// 测试名

	public Test(String name) {
		this.name = name;
	}

	/**
	 * 为不同的测试覆盖此方法。返回方法的实际执行次数(也是操作的元素个数)
	 * @param container 容器
	 * @param tp 需要测试的数据
	 * @return 方法的实际执行次数(也是操作的元素个数)
	 */
	abstract int test(C container, TestParam tp);
} ///:~

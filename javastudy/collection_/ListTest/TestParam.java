package javastudy.collection_.ListTest;//: containers/TestParam.java

/**
 * 存放测试的数据
 */
public class TestParam {
	public final int size; //容器内元素数量
	public final int loops; //测试迭代次数

	public TestParam(int size, int loops) {
		this.size = size;
		this.loops = loops;
	}

	/**
	 * 从可变参数序列创建一个 TestParam 数组
	 * @param values 元素个数和迭代次数交替出现的数组
	 * @return 测试数据对象
	 */
	public static TestParam[] array(int... values) {
		int size = values.length / 2;
		TestParam[] result = new TestParam[size];
		int n = 0;
		for (int i = 0; i < size; i++)
			result[i] = new TestParam(values[n++], values[n++]); //有趣的写法
		return result;
	}

	// 将 String 数组转换为 TestParam 数组
	public static TestParam[] array(String[] values) {
		int[] vals = new int[values.length];
		for (int i = 0; i < vals.length; i++)
			vals[i] = Integer.decode(values[i]);
		return array(vals);
	}
} ///:~

package javastudy.reflection_.exercise;

import java.lang.reflect.Array;

/**
 * 使用反射编写泛型数组的拷贝代码
 * 不使用泛型而使用反射来获取数组中元素对象
 */
public class ArrayCopy {
	/**
	 * 扩展数组
	 * 没有使用泛型和反射，数组中元素的类型为Object类型，跟原数组不是同一个类型
	 *
	 * @param a         待扩展的数组
	 * @param newLength 新长度
	 * @return 新的数组，元素类型为Object类型
	 */
	public static Object[] badCopyOf(Object[] a, int newLength) {
		Object[] newArray = new Object[newLength];
		System.arraycopy(a, 0, newArray, 0, Math.min(a.length, newLength));
		return newArray;
	}

	/**
	 * 扩展数组
	 * 使用了泛型来获取数组中元素的类对象，创建的是和原数组同一个类型的
	 * 注意基本数据类型的数组也可以扩展
	 *
	 * @param a         原数组
	 * @param newLength 新长度
	 * @return 与原数组类型保持一致的新数组
	 */
	public static Object goodCopyOf(Object a, int newLength) {
		Class<?> cl = a.getClass();
		if (!cl.isArray()) return null;
		Class<?> componentType = cl.getComponentType(); //获取数组中元素的实例的类对象
		int length = Array.getLength(a); //a是一个Object对象，使用该方法获取数组长度
		Object newArray = Array.newInstance(componentType, newLength); //创建元素为指定类对象的数组
		System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));
		return newArray;
	}

	public static void main(String[] args) {
		String[] strs = {"123", "456", "789"};
		int[] ints = {1, 2, 3, 4, 5, 6};
		String[] newStrs = (String[]) goodCopyOf(strs, 10);
		int[] newints = (int[]) goodCopyOf(ints, 10);
		assert newStrs != null && newints != null;
		System.out.println(newStrs.length + " " + newints.length); //10
	}
}

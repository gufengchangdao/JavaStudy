package javastudy.method_;

/**
 * 自动装箱、new Integer()、Integer.valueOf()和基本类型的关系
 */
public class Equivalence {
	static void show(String desc, Integer n1, Integer n2) {
		System.out.println(desc + ":");
		System.out.printf(
				"%d==%d %b %b%n", n1, n2, n1 == n2, n1.equals(n2));
	}

	@SuppressWarnings("deprecation")
	public static void test(int value) {
		Integer i1 = value;                             // [1]
		Integer i2 = value;
		show("Automatic", i1, i2);
		// Old way, deprecated in Java 9 and on:
		Integer r1 = new Integer(value);                // [2]
		Integer r2 = new Integer(value);
		show("new Integer()", r1, r2);
		// Preferred in Java 9 and on:
		Integer v1 = Integer.valueOf(value);            // [3]
		Integer v2 = Integer.valueOf(value);
		show("Integer.valueOf()", v1, v2);
		// Primitives can't use equals():
		int x = value;                                  // [4]
		int y = value;
		// x.equals(y); // Doesn't compile
		System.out.println("Primitive int:");
		System.out.printf("%d==%d %b%n", x, y, x == y);
	}

	public static void main(String[] args) {
		test(127);
		test(128);
	}
}
/* Output:
Automatic:
127==127 true true
128==128 false true
new Integer():
127==127 false true
128==128 false true
Integer.valueOf():
127==127 true true
128==128 false true
Primitive int:
127==127 true
128==128 true

new Integer() 一定创建对象，不推荐使用

自动装箱 ---> 替换为Integer.valueOf()
-128~127    常量池，为同一个对象
 */


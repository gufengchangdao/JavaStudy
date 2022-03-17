package javastudy.polymorphic;

/**
 * 多态缺陷：域与静态方法
 */
public class FieldAccess {
	public static void main(String[] args) {
		Sub sub = new Sub();
		Super sup = new Sub();
		System.out.println("子类引用：field: " + sub.field + ",  getField(): " + sub.getField());
		System.out.println("父类引用：field: " + sup.field + ",  getField(): " + sup.getField());
		sub.staticMethod();
		sup.staticMethod();
		/*
		输出为：
			子类引用：field: 1,  getField(): 1
			父类引用：field: 0,  getField(): 1
			子类静态方法
			父类静态方法
		 TODO 向上转型后调用字段获取到的是父类的字段
		  1. 只有普通方法调用是多态的，并且任何域访问都将由编译器解析，如果你直接访问某个域，这个访问就将在编译期进行解析，因此不是多态的。
		  在这个例子中，为Super.field和Sub.field分配了不同的存储空间，这时，sub实际上拥有两个称为field的域。
		  2. 这个问题实际上基本不会发生，因为：
		    (1) 通常会将所有的域设为private，无法直接访问，调用的getXXX()方法是保持多态
		    (2) 一般不会让子类和父类有同名的字段
		  3. 可以看到，静态方法也不具有多态性，引用是什么就调用什么的静态方法
		 */
	}
}

class Super {
	public int field = 0;

	public int getField() {
		return field;
	}

	public static void staticMethod() {
		System.out.println("父类静态方法");
	}
}

class Sub extends Super {
	public int field = 1;

	@Override
	public int getField() {
		return field;
	}

	public int getSuperField() {
		return super.field;
	}

	// @Override //这个会报错
	public static void staticMethod() {
		System.out.println("子类静态方法");
	}
}


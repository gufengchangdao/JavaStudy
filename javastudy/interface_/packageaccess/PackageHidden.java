package javastudy.interface_.packageaccess;

interface A {
	void f();
}

// 实现类
class B implements A {
	public void f() {
		System.out.println("public C.f()");
	}

	private void g() {
		System.out.println("private C.w()");
	}
}

/**
 * 使用包隐藏实现类
 */
public class PackageHidden {
	public static A makeA() {
		return new B();
	}
}
/*
TODO 接口中存在的问题
 问题：程序员可能会将获得的接口向下，调用开发者不希望被调用的方法，增加了这些方法维护的成本
 三个解决：
    1.直接声明，不该转型并且自己对自己负责
	2.包隐藏实现类，只透露一个工厂类(或是用匿名的方式实现接口，再或者是私有的成员内部类)，但是这些方法对付不了反射
	3.使用SecurityManager来禁止反射
*/
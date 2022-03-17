package javastudy.polymorphic.distribute;
/*
TODO 多路分发
1. 问题：当你要处理交互类型时，方法参数为this表示的类，比如 a.f(A b), a和b的引用都为A，实际类型不知，如果你想要调用的确实 a.f(B b)该怎么办呢？
2. java只支持单路分发，分发机制会在a的实际类型的方法起到分发的作用，存在f(A a)方法就会调用该方法，而不是根据b的实例类型调用方法
3. 解决：用代码实现多路分发，利用多态可以锁定到对象方法的特点，在方法内部的this引用已经是对象的实际类型了。要完成多路分发需要有两个方法，一个方
   法决定第一个未知类型，第二个方法决定第二个未知类型，第二次分发后就能知道两个对象的实际类型了
4. 多路分发目的：摸索对象的实际类型，获取其引用调用合适方法
4. 多路分发避免了写多个instanceOf来判定类型，更优雅(不是我说的)
 */

/**
 * 用于交互操作的接口，
 * 想要使用f(B a)和f(C a)，但是类型只知道是A，就得提供f(A a)来第一次分发
 */
interface A {
	void f(A a);

	void f(B a);

	void f(C a);
}

class B implements A {

	@Override
	public void f(A a) {
		System.out.println("B.f(A a)");
		// 如果注释调下面这个，改用 this(a); 会无限递归
		a.f(this);//注意这个this的引用类型已经是B了，会调用A.f(B a)
	}

	@Override
	public void f(B a) {
		System.out.println("B.f(B a)");
	}

	@Override
	public void f(C a) {
		System.out.println("B.f(C a)");
	}
}

class C implements A {

	@Override
	public void f(A a) {
		System.out.println("C.f(A a)");
		a.f(this);
	}

	@Override
	public void f(B a) {
		System.out.println("C.f(B a)");
	}

	@Override
	public void f(C a) {
		System.out.println("C.f(C a)");
	}
}

/**
 * 使用接口实现多路分发
 */
public class InterfaceDistribute {
	public static void main(String[] args) {
		A b = new B();
		A c = new C();
		b.f(c);
		/*
		分发前：        A(b)   A(c)
		第一次分发：     B(b)   A(c)
		第二次分发：     B(b)   C(c)
		 */
	}
}

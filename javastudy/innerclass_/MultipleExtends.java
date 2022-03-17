package javastudy.innerclass_;

class class1 {
	public void method1(){
		System.out.println("你好");
	}
}

class class2 {
}

/**
 * 使用内部类实现“多重继承”
 */
public class MultipleExtends extends class1 {
	public static class MyClass extends class2 {
		// 1. 由于内部类提供了进入外围类的窗口，因此可以利用内部类实现“多重继承”
		// 2. 还想再继承可以无限套娃，不断嵌套
		// 3. 如果是两个接口，那创建一个新类也可以，但是如果是两个抽象的类或抽象类就只能使用内部类来实现多重继承
	}
}

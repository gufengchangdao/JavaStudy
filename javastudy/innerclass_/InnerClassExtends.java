package javastudy.innerclass_;

/**
 * 演示内部类的继承方式
 */
public class InnerClassExtends extends OuterClass.InnerClass {
	// TODO: 因为内部类的构造器必须连接到指向外围类对象的引用，所以继承(非静态)内部类的时候有些复杂，需要使用特殊语法
	//  内部类中指向外围类对象的“秘密的”引用必须被初始化，在子类构造器中必须要传入父类构造器并且调用其构造器
	//       关键：创建内部类必须有其外围类对象；内部类指向外围类的引用必须被初始化
	public InnerClassExtends(OuterClass outer) {
		outer.super();
	}

	public static void main(String[] args) {
		OuterClass outer = new OuterClass();
		InnerClassExtends c = new InnerClassExtends(outer);
	}
}

class OuterClass {
	public class InnerClass {
	}
}

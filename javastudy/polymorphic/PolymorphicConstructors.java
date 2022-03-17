package javastudy.polymorphic;

/**
 * 构造器内部的多态方法行为
 */
public class PolymorphicConstructors {
	private static class Parent {
		public Parent() {
			System.out.println("Parent() 调用method之前");
			method();
			System.out.println("Parent() 调用method之后");
		}

		public void method() {
			System.out.println("Parent() 父类方法");
		}
	}

	private static class Child extends Parent {
		public int id;

		public Child(int id) {
			this.id = id;
			System.out.println("id: " + id);
		}

		@Override
		public void method() {
			System.out.println("id: " + id);
		}
	}

	public static void main(String[] args) {
		new Child(5);
		/*
		输出：
			Parent() 调用method之前
			id: 0
			Parent() 调用method之后
			id: 5
		TODO
		 1. 在子类的构造器中，如果没有明确指定调用父类哪个构造器，就会调用其默认构造器，如果不存在默认构造器的话编译器就会报错
		 2. 父类构造器中调用被重写的方法也会有多态性，但是此时子类构造器的代码还没执行，重写后方法的操纵的成员变量可能还没有初始化。或者你压根不
		 想父类构造器调用重写的方法
		 3. 在实例中初始化的过程为：
		    (1) 首先将分配给对象的存储空间初始化成二进制的零(或是特殊类型中与零等价的值)
		    (2) 调用父类构造器，调用覆盖后的方法，此时 id==0
		    (3) 按照声明顺序调用成员初始化方法，调用完父类的默认构造器后才调用子类构造器
		 4. 构造器中多态的体现可能有时候出现一些bug，像上面的id为0而不是5，建议在父类构造器中少调用重写的方法或者少调用方法
	    */
	}
}

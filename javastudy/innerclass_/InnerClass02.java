/*
TODO 四种内部类
    定义在外部类局部位置上(比如方法内):
        局部内部类(有类名)
        匿名内部类(没有类名)
    定义在外部类的成员位置:
        成员内部类(没用static修饰)
        静态内部类(使用static修饰)
 */
package javastudy.innerclass_;

public class InnerClass02 {
	public static void main(String[] args) {
		ALlInner inner = new ALlInner();

		//局部内部类是在方法中的，无法直接创建，只能在方法中操作
		inner.method();

		//利用外部类对象new到一个成员内部类
		ALlInner.Inner3 inner2 = inner.new Inner3();

		//利用外部类对象new到一个静态成员内部类
		ALlInner.Inner4 inner4 = new ALlInner.Inner4();

	}
}

class ALlInner {
	private String name = "白痴";

	public void method() {
		int age = 99;
		// age=10; //局部变量再更改就不能调用了

		//TODO 局部内部类
		//1. 作为一个"局部变量"，可以调用类的属性、方法，不能添加修饰符，但是可以用final修饰
		//2. 作用域：仅仅在定义它的方法或者代码块中
		//3. 局部内部类访问外部类成员 ---> 直接访问
		//4. 外部类成员访问局部内部类 ---> 创建对象再访问
		//5. 局部内部类有一个很大的优势，那就是对外部世界完全隐藏，即便是外围类中的其他方法也不知道他的存在
		//6. 局部内部类可以使用局部变量，但是局部变量必须为事实最终变量
		final class Inner1 {
			//如果外部类和局部内部类和局部内部类重名时，默认遵守就近原则，如果想访问外部类的成员，可以用 外部类名.this.成名名
			//其中 外部类名.this 就是调用该方法的对象,而 this.name 就代表内部类的成员了
			private String name = "天才";

			public void cry() {
				System.out.println(ALlInner.this.name);
				System.out.println(age); //调用局部变量
			}
		}

		Inner1 inner1 = new Inner1();
		inner1.cry();

		if (false) {
			// 放在if语句中并不是说该类的创建是有条件的，是不是true该类都会被编译
			class Inner2 {
				private String name = "天才";
			}
		}
	}

	//TODO 成员内部类
	//1. 作为ALlInner的一个成员，该类享有作为一个成员的所有地位：修饰符、调用外部类的成员...
	//2. 同名时访问外部类的的成员方法是一样的
	//3. 因为内部类会有一个隐式的指针，可以访问外部对象的全部状态，而静态内部类就没有这个指针
	//4. 在接口中声明的类自动是public和static的
	public class Inner3 {
		// 成员内部类的静态属性必须是final的，但是静态内部类可以不是
		// public final static String name="明日香";

		// 成员内部类不能有static方法，但是静态内部类可以有
		// public static void setName(String name){}

		public String getName() {
			// return name; //外围类对象的引用
			return ALlInner.this.name;
		}
	}

	//TODO 静态内部类
	//作为一个静态成员，不能直接访问非静态的，一般的类都不能用static修饰呢
	public static class Inner4 {
	}

}



/*
匿名内部类
    特点：现写现用
    匿名内部类定义在外部类的局部位置
    该类没有名字，意思是类名是由系统分配的，不是自定义的
    只能使用一次，是类只能使用一次，不是对象，对象的引用还在就可以一直调用其成员
    编译类型：前面引用的类型
    运行类型：匿名内部类(用getClass查看)

 */

package javastudy.innerclass_;

/**
 * 匿名内部类演示
 */
public class AnonymousInnerClassTest {
	public static void main(String[] args) {
		//todo 创建匿名内部类继承该类
		//这个匿名内部类就是创建在了main方法内，为外部类的局部位置
		//new的时候底层就是创建了一个匿名内部类的对象
		None1 none1 = new None1() {
			@Override
			public void game() {    //重写其方法
				System.out.println("STUDY");
			}
		};
		//编译类型：None1
		//运行类型：AnonymousInnerClass$1
		System.out.println(none1.getClass());//AnonymousInnerClass$1

		//todo 创建匿名内部类后添加的字段和方法都不能直接被调用，可以重写原类的方法调用添加的字段的方法
		None1 none = new None1() {
			public int count = 9;

			@Override
			public String toString() {
				return "$classname{" +
						"count=" + count +
						'}';
			}
		};
		// System.out.println(none.count); //报错
		System.out.println(none); //$classname{count=9}

		//todo 创建匿名内部类并实现其接口，匿名内部类实现了该接口
		None2 none2 = new None2() {
			@Override
			public void game() {
				System.out.println("STUDY");
			}
		};
		System.out.println(none2.getClass());//AnonymousInnerClass$2

		//todo 创建匿名内部类继承该抽象类
		None3 none3 = new None3() {
			@Override
			public void game() {
				super.game();
			}
		};
	}
}

class None1 {
	public void game() {
		System.out.println("PLAY");
	}
}

interface None2 {
	public void game();
}

abstract class None3 {
	public void game() {
		System.out.println("PLAY");
	}
}
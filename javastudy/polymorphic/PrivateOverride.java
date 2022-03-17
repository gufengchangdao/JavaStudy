package javastudy.polymorphic;

/**
 * 多态缺陷："覆盖"私有方法
 */
public class PrivateOverride {
	private void f() {
		System.out.println("父类私有方法被调用");
	}

	public static void main(String[] args) {
		/*TODO 在父类中创建子类对象并用父类引用，对象调用父类的私有方法
		    使用父类作为引用类型时，我们期望调用的是子类的方法，但是由于private方法自动认为是final方法，而且是对外是屏蔽的，子类无法重写，编
		   译器不会报错，但是却调用的是父类方法
		    建议：不要有重名方法
		 */
		PrivateOverride po = new Child(); //父类私有方法被调用
		// Child po = new Child(); //子类私有方法被调用
		po.f();
	}
}

class Child extends PrivateOverride {
	public void f() {
		System.out.println("子类私有方法被调用");
	}

	public static void main(String[] args) {
		// PrivateOverride po = new Child(); //报错
		// po.f();
	}
}

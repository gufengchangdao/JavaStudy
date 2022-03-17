package javastudy.innerclass_;

/**
 * 内部类的 "覆盖"
 */
public class OverInnerClass extends Egg {
	protected class Yolk {
		public Yolk() {
			System.out.println("覆盖后构造器");
		}
	}

	public static void main(String[] args) {
		OverInnerClass aClass = new OverInnerClass(); //原构造器
		aClass.new Yolk(); //覆盖后构造器
		((Egg) aClass).new Yolk(); //原构造器
		/*
		todo
			1. 可以看到，内部类也不具有多态性，在创建OverInnerClass对象时使用的还是原本的Yolk而不是"覆盖"后的Yolk
			2. 两个内部类是完全独立的两个实体，转型后调用的Yolk也发生了改变
		 */
	}
}

class Egg {
	protected class Yolk {
		public Yolk() {
			System.out.println("原构造器");
		}
	}

	public Egg() {
		new Yolk();
	}
}

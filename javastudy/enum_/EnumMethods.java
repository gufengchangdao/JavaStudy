package javastudy.enum_;

/**
 * 演示枚举类与常量相关方法的使用
 */
public class EnumMethods {
	/*
	1. 枚举类可以实现抽象方法，创建与常量(每一个枚举实例)有关的方法，每个枚举实例都想一个独特的类，有自己独有的方法实现
	2. 枚举类可以重写方法，注意方法都不能设为private
	 */
	private enum MyEnum{
		KAIWEN("凯文"){
			// TODO 实现抽象方法
			@Override
			protected String speck() {
				return "朋友还是敌人，选吧";
			}
		},FUHUA("浮华"){
			@Override
			protected String speck() {
				return "寸劲开天，这个招式名听起来怎么样？";
			}
			// TODO 重写方法
			@Override
			public void interest() {
				super.interest();
			}
		};
		private String name;

		MyEnum(String name) {
			this.name = name;
		}

		// private abstract String speck();
		protected abstract String speck();

		public void interest(){
			System.out.println("大橘为重");
		}
	}
	public static void main(String[] args) {
		System.out.println(MyEnum.KAIWEN.name +": "+ MyEnum.KAIWEN.speck());
	}
}

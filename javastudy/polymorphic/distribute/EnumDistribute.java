package javastudy.polymorphic.distribute;

import java.util.function.Consumer;

// 石头剪刀布的结果
enum Outcome {WIN, LOSE, DRAW}

// 比较接口
interface Competitor<T extends Competitor<T>> {
	Outcome compete(T competitor);
}

/**
 * 演示使用枚举、泛型来完成多路分发，
 * 模拟石头剪刀布
 */
public enum EnumDistribute implements Competitor<EnumDistribute> {
	PAPER(Outcome.DRAW, Outcome.LOSE, Outcome.WIN), //布对应布、剪刀、石头的三种结果
	SCISSORS(Outcome.WIN, Outcome.DRAW, Outcome.LOSE),
	ROCK(Outcome.LOSE, Outcome.WIN, Outcome.DRAW);
	// 表示当前出法对相应出法的三种结果
	private Outcome vsPAPER, vsSCISSORS, vsROCK;

	EnumDistribute(Outcome vsPAPER, Outcome vsSCISSORS, Outcome vsROCK) {
		this.vsPAPER = vsPAPER;
		this.vsSCISSORS = vsSCISSORS;
		this.vsROCK = vsROCK;
	}

	@Override
	public Outcome compete(EnumDistribute competitor) {
		// 使用switch进行第二次分发
		return switch (competitor) {
			case PAPER -> vsPAPER;
			case SCISSORS -> vsSCISSORS;
			case ROCK -> vsROCK;
		};
	}

	/* TODO 注意：
	    两个对象向上引用为接口类型，compete()参数只要是Competitor及其子类编译器就不会报错，调用方法后追踪到EnumDistribute里面调用compete()
	    方法，编译器会自动转型。很方便，但也容易抛类型转换的异常
	 */
	public static void main(String[] args) {
		Competitor paper = PAPER;
		Competitor rock = ROCK;
		// 实际的方法调用进行第一次分发
		System.out.println(paper.compete(rock)); //布对石头
	}
}

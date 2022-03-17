package javastudy.polymorphic.distribute;

import java.util.EnumMap;

import static javastudy.polymorphic.distribute.Outcome.*;

/**
 * 使用EnumMap分发
 */
public enum EnumMapDistribute implements Competitor<EnumMapDistribute> {
	PAPER, SCISSORS, ROCK;
	/**
	 * EnumMap<A,EnumMap<B,C>> 其中A和B都是出法，C是A对上B的结果:输赢还是平局
	 */
	static EnumMap<EnumMapDistribute, EnumMap<EnumMapDistribute, Outcome>>
			table = new EnumMap<>(EnumMapDistribute.class);

	static {
		for (EnumMapDistribute it : EnumMapDistribute.values())
			table.put(it, new EnumMap<>(EnumMapDistribute.class));
		initRow(PAPER, DRAW, LOSE, WIN);
		initRow(SCISSORS, WIN, DRAW, LOSE);
		initRow(ROCK, LOSE, WIN, DRAW);
	}

	// 初始化每种出法对应每种出法的结果
	static void initRow(EnumMapDistribute it, Outcome vPAPER, Outcome vSCISSORS, Outcome vROCK) {
		EnumMap<EnumMapDistribute, Outcome> row = table.get(it);
		row.put(PAPER, vPAPER);
		row.put(SCISSORS, vSCISSORS);
		row.put(ROCK, vROCK);
	}

	@Override
	public Outcome compete(EnumMapDistribute it) {
		// TODO 这里实现了两次分发
		return table.get(this).get(it);
	}

	public static void main(String[] args) {
		System.out.println(ROCK.compete(SCISSORS));
	}
}

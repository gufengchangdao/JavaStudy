package javastudy.polymorphic.distribute;

import static javastudy.polymorphic.distribute.Outcome.*;

/**
 * 使用二维数组，
 * 清晰易懂，但是比较僵硬
 */
public enum ArrayDistribute {
	PAPER, SCISSORS, ROCK;
	private static Outcome[][] table = {
			{DRAW, LOSE, WIN}, // PAPER
			{WIN, DRAW, LOSE}, // SCISSORS
			{LOSE, WIN, DRAW}, // ROCK
	};

	public Outcome compete(ArrayDistribute other) {
		return table[this.ordinal()][other.ordinal()];
	}

	public static void main(String[] args) {
		System.out.println(PAPER.compete(ROCK));
	}
}

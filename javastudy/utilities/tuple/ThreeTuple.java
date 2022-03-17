package javastudy.utilities.tuple;

public class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
	public final C three;

	public ThreeTuple(A first, B second, C three) {
		super(first, second);
		this.three = three;
	}

	@Override
	public String toString() {
		return "ThreeTuple{" +
				"first=" + first +
				", second=" + second +
				", three=" + three +
				'}';
	}
}

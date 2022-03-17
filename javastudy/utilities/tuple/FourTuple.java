package javastudy.utilities.tuple;

public class FourTuple<A, B, C, D> extends ThreeTuple<A, B, C> {
	public final D four;

	public FourTuple(A first, B second, C three, D four) {
		super(first, second, three);
		this.four = four;
	}

	@Override
	public String toString() {
		return "FourTuple{" +
				"first=" + first +
				", second=" + second +
				", three=" + three +
				", four=" + four +
				'}';
	}
}

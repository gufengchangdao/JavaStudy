package javastudy.utilities.tuple;

public class FiveTuple<A, B, C, D, E> extends FourTuple<A, B, C, D> {
	public final E five;

	public FiveTuple(A first, B second, C three, D four, E five) {
		super(first, second, three, four);
		this.five = five;
	}

	@Override
	public String toString() {
		return "FiveTuple{" +
				"first=" + first +
				", second=" + second +
				", three=" + three +
				", four=" + four +
				", five=" + five +
				'}';
	}
}

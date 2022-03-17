package javastudy.utilities.tuple;

// 二元组，用来打包多个对象来返回
public class TwoTuple<A, B> {
	//final成员，只能创建不能修改
	public final A first;
	public final B second;

	public TwoTuple(A first, B second) {
		this.first = first;
		this.second = second;
	}

	public String toString() {
		return "(" + first + ", " + second + ")";
	}
}

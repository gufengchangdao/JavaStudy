package javastudy.enum_;

import java.util.EnumSet;

/**
 * 演示枚举集的使用
 */
public class EnumSetTest {
	/*
	EnumSet枚举集
	1. 枚举集在内部表示为位向量。 这种表示非常紧凑和高效，EnumSet的基础是long，内部使用的大量的位运算
	2. 一个long有64位，而一个enum实例只需要一位bit表示其是否存在，在创建EnumSet时，实例<=64时创建RegularEnumSet，使用一个long值表示，而
	大于64时使用JumboEnumSet表示，维护的是一个long数组
	 */

	/**
	 * 76个实例，创建EnumSet时，会生成JumboEnumSet实例
	 */
	private enum Big {
		A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10,
		A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21,
		A22, A23, A24, A25, A26, A27, A28, A29, A30, A31, A32,
		A33, A34, A35, A36, A37, A38, A39, A40, A41, A42, A43,
		A44, A45, A46, A47, A48, A49, A50, A51, A52, A53, A54,
		A55, A56, A57, A58, A59, A60, A61, A62, A63, A64, A65,
		A66, A67, A68, A69, A70, A71, A72, A73, A74, A75
	}

	public static void main(String[] args) {
		// 创建对象
		EnumSet<Big> bigs = EnumSet.noneOf(Big.class); //空的
		// EnumSet对of做了好几个重载，而不是只使用一个可变参数的of()，这是为了节省创建数组的时间，对性能要求要严格
		EnumSet<Big> bigs1 = EnumSet.of(Big.A0, Big.A1, Big.A2); //指定元素
		EnumSet<Big> bigs2 = EnumSet.allOf(Big.class); //所有
		System.out.println(bigs1.getClass().getName()); //java.util.JumboEnumSet

		// bigs.add(Big.A0);
	}
}


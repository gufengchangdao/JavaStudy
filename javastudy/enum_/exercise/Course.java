package javastudy.enum_.exercise;

import java.util.Random;

/**
 * 枚举的枚举，更清晰的枚举分组
 */
public enum Course {
	TIANMING("天命", Member.TianMing.class),
	NISANG("逆熵", Member.NiSang.class),
	XUEYUAN("圣芙蕾雅学院", Member.XueYuan.class),
	OTHER("其他", Member.Other.class);
	private Member[] values;
	private String introduce;

	// TODO 将创建的每个枚举类作为参数传入，使用getEnumConstants()来获取枚举实例数组
	Course(String introduce, Class<? extends Member> kind) {
		this.introduce = introduce;
		values = kind.getEnumConstants();
	}

	private static Random random = new Random();

	public Member randomSelection() {
		return values[random.nextInt(values.length)];
	}
}

class CourseTest {
	public static void main(String[] args) {
		// 随机输出四次天命的成员
		for (int i = 0; i < 4; i++) {
			System.out.println(Course.TIANMING.randomSelection());
		}
	}
}
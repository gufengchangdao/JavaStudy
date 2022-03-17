package javastudy.utilities;

import java.util.Random;

public class Enums {
	private static Random rand = new Random();

	public static <T extends Enum<T>> T random(Class<T> ec) {
		return random(ec.getEnumConstants()); //返回枚举类的所有枚举实例
	}

	public static <T> T random(T[] values) {
		return values[rand.nextInt(values.length)];
	}
} ///:~

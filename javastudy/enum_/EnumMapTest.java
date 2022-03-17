package javastudy.enum_;

import java.util.EnumMap;
import java.util.Map;

interface Action {
	String act();
}

enum Mood {
	HAPPY, BAD, SILENCE, ANGER
}

/**
 * 使用EnumMap演示命令设计模式的用法
 */
public class EnumMapTest {
	/*
	EnumMap
	1. EnumMap要求键是一个Enum，EnumMap内部由数组实现，速度较快
	2. 其他跟Map差不多
	3. 命令设计模式需要一个函数式接口，然后接口实现具有各种不同行为的多个子类
	 */
	public static void main(String[] args) {
		EnumMap<Mood, Action> enumMap = new EnumMap<>(Mood.class);
		enumMap.put(Mood.HAPPY, () -> "打游戏");
		enumMap.put(Mood.BAD, () -> "写作业");
		enumMap.put(Mood.SILENCE, () -> "懦弱");

		for (Map.Entry<Mood, Action> entry : enumMap.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue().act());
		}
	}
}

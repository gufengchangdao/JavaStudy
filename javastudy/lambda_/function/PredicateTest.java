package javastudy.lambda_.function;

import java.util.function.Predicate;

/**
 * 演示谓词的使用和复合
 */
public class PredicateTest {
	// 谓词：传入一个参数并返回boolean值的函数
	public static void main(String[] args) {
		// TODO 一般使用
		Predicate<Apple> predicate = (Apple apple) -> apple.getWeight() > 150;

		// TODO 复合使用，好处是利用Predicate接口提供的默认方法，将多个函数复合起来，作为参数传递给方法，灵活且简洁
		//  注意优先级是从左到右确定，例如，a and b ，如果 a 的结果为false，那么 b 的函数就不会执行了
		// negate()返回现有Predicate对象的非
		Predicate<Apple> negate = predicate.negate();

		// and()组合，既什么又什么
		Predicate<Apple> predicate1 = predicate.and(apple -> apple.getColor().equals(Apple.Color.RED));

		// or()
		Predicate<Apple> predicate2 = predicate.or(apple -> apple.getWeight()<50);

	}
}

class Apple {
	public static enum Color {
		RED, GREEN
	}

	private Color color = Color.RED;
	private int weight;

	public Apple(Color color, int weight) {
		this.color = color;
		this.weight = weight;
	}

	public Color getColor() {
		return color;
	}

	public int getWeight() {
		return weight;
	}
}
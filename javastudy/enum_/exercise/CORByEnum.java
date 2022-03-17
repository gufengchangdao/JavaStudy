package javastudy.enum_.exercise;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 用Enum是使用职责链设计模式，
 * 浮华做的饭菜依次给琪亚娜、奥托和凯文吃，看最后谁能吃掉
 */
public class CORByEnum {
	// TODO 职责链模式：用多种不同的方式来解决一个问题，然后将他们连接起来，当一个请求到来时遍历这个链，直到链中某个解决方案能够处理该请求
	private static void handle(Meal meal) {
		for (Person person : Person.values()) { //让每个人都尝试吃掉meal
			if (person.eat(meal))
				return;
		}
		if (meal.getExponent() > 0.99) System.out.println("没人吃，只能喂舰长了"); //舰长还得拯救世界呢
		else System.out.println("没人吃，还是给芽衣吃吧");
	}

	public static void main(String[] args) {
		handle(new Meal("浮华做的鸡汤", 1));
	}
}

/**
 * 某人做的饭菜
 */
class Meal {
	private String description; //饭菜的描述
	private double exponent; //难吃指数

	public Meal(String description, double skill) {
		this.description = description;
		this.exponent = new Random().nextDouble() * skill;
	}

	public double getExponent() {
		return exponent;
	}

	public String getDescription() {
		return description;
	}
}

/**
 * 四位实验体
 */
enum Person {
	QIYANA("琪亚娜", 0.7) {
		@Override
		String speck() {
			return "还得看本小姐";
		}
	},
	AOTUO("奥托", 0.5) {
		@Override
		String speck() {
			return "这很难吃";
		}
	},
	SHIBAO("识宝", 0.3) {
		@Override
		String speck() {
			return "识宝修改了你的意识，你不会再做饭了";
		}
	},
	KAIWEN("凯文", 0.2) {
		@Override
		String speck() {
			return "这比律者还难对付";
		}
	};
	private String name;
	private double accept; //能接受的指数

	// 吃食物
	boolean eat(Meal meal) {
		if (meal.getExponent() > accept) {
			System.out.println(name + "吃下了" + meal.getDescription() + "，说了一句：“" + speck() + "。“");
			return true;
		}
		return false;
	}

	abstract String speck(); //三人吃下食物后的表现

	Person(String name, double accept) {
		this.name = name;
		this.accept = accept;
	}
}


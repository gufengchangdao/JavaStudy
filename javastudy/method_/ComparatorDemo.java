package javastudy.method_;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 演示Comparator提供的众多默认方法，且多返回Comparator对象，因此可以形成比较器链
 */
public class ComparatorDemo {
	public static void main(String[] args) {
		Person[] persons = new Person[3];
		persons[0] = new Person("abc", 34);
		persons[1] = new Person("cba", 44);
		persons[2] = new Person("aaaa", 44); //使用String的排序会抛异常
		// persons[2] = new Person(null, 44); //使用String的排序会抛异常

		// 普通方法，实现Compare()方法
		Arrays.sort(persons, (p1, p2) -> {
			return p1.getAge() - p2.getAge();
		});

		// TODO 使用默认比较器
		Arrays.sort(persons, Comparator.comparing(Person::getAge));

		// TODO 使用键提取器提取对象中要比较的字段，交给键比较器进行排序
		Arrays.sort(persons, Comparator.comparing(Person::getName, Comparator.comparingInt(String::length)));

		// TODO 变体形式，可以避免基本数据类型的装箱
		Arrays.sort(persons, Comparator.comparingInt(p -> p.getName().length()));

		// TODO 如果第一个比较两者相同，就会使用第二个比较器
		Arrays.sort(persons, Comparator.comparing(Person::getName).thenComparing(Person::getAge));

        /*
        TODO Comparator.naturalOrder()
            该方法可以为任何一个实现了Comparable的类建立一个比较器(就是使用该的comparable方法，虽然该方法不是静态的)，并且该方法的类型可以
            推导出来，所以不要手动填写String类，但是String比较器对于null不友好
        */
		Arrays.sort(persons, Comparator.comparing(Person::getName, Comparator.naturalOrder()));
		Arrays.sort(persons, Comparator.comparing(Person::getName, String::compareTo)); //等效，不过这个得自己指定

        /*
        TODO Comparator.nullsFirst()
            返回一个对 null 友好的比较器，它认为null小于非 null。 当两者都为null ，它们被认为是相等的。 如果两者都不为空，则使用指定的Comparator来
            确定顺序。 如果传入的比较器为null ，则返回的比较器将所有非空值视为相等
        */
		Arrays.sort(persons, Comparator.comparing(Person::getName, Comparator.nullsFirst(Comparator.naturalOrder())));

		// TODO 自然顺序的逆序
		Arrays.sort(persons, Comparator.comparing(Person::getAge, Comparator.reverseOrder()));
		Arrays.sort(persons, Comparator.comparing(Person::getAge).reversed());

		for (Person p : persons) {
			System.out.println(p);
		}
	}
}

class Person {
	private String name;
	private int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
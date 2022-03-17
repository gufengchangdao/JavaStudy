package javastudy.genericity_.excrcise;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * 函数式接口，放有需要运行的方法
 */
interface Addable<T> {
	void add(T t);
}

class Dog {
	private static int inc = 0;
	private int id = ++inc;

	@Override
	public String toString() {
		return "Dog" + id + "号";
	}
}

// 原始类
class MyCollection<T> {
	private Object[] elements;
	private int index = 0;

	public MyCollection(int size) {
		elements = new Object[size];
	}

	public void add(T t) {
		elements[index++] = t;
	}

	@Override
	public String toString() {
		return Arrays.toString(elements);
	}
}

/**
 * 适配器一，继承加实现，使得原始类可以使用add方法(虽然原本就有这个方法)
 */
class AddableCollectionAdapter<T> extends MyCollection<T> implements Addable<T> {
	public AddableCollectionAdapter(int size) {
		super(size);
	}
}

/**
 * 适配器二，组合加实现
 */
class AddableCollectionAdapter2<T> implements Addable<T> {
	private MyCollection<T> collection;

	public AddableCollectionAdapter2(MyCollection<T> collection) {
		this.collection = collection;
	}

	@Override
	public void add(T t) {
		collection.add(t);
	}

	@Override
	public String toString() {
		return collection.toString();
	}
}

/**
 * 用适配器仿真潜在类型机制
 * 适配器模式的思想是：把一个类的接口变换成客户端所期待的另一种接口，从而使原本因接口不匹配而无法在一起工作的两个类能够在一起工作。
 * 适配器模式的两种实现：组合加实现、继承加实现
 */
public class Apply {
	private static <T> void fill(Addable<T> addable, Class<? extends T> c, int size) throws Exception {
		Constructor<? extends T> constructor = c.getDeclaredConstructor();
		for (int i = 0; i < size; i++) {
			addable.add(constructor.newInstance());
		}
	}

	public static void main(String[] args) throws Exception {
		AddableCollectionAdapter<Dog> adapter = new AddableCollectionAdapter<>(15);
		fill(adapter, Dog.class, 10);

		AddableCollectionAdapter<Dog> adapter2 = new AddableCollectionAdapter<>(15);
		fill(adapter2, Dog.class, 10);

		System.out.println(adapter);
		System.out.println(adapter2);
	}
}
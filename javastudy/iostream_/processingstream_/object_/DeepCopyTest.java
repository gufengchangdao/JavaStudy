package javastudy.iostream_.processingstream_.object_;

import java.io.*;

/**
 * 有关序列化实现深度拷贝的测试
 */
public class DeepCopyTest {
	/*
		问题一：将同一对象进行序列化后再反序列化，会发生什么？
		测试一：cat写入缓冲区一两次，写入缓冲区二一次，最后拿出来都和原对象进行比较
		结果：反序列化后的对象都和原对象不同，是新创建的对象，写入单一缓冲区两次的对象反序列化后地址还是一样的
		应用：复制对象，再加上对象网的效果 ---> 用于深度拷贝

		问题二：同一缓冲区写入两次的同一对象，如果其中引用的对象不同，反序列化后会发生什么？
		测试二：对写入缓冲区两次的同一个对象，序列化一次后，重新为Fish赋值，再把cat序列化，反序列化后比较
		结果：两个引用为同一对象，修改无效。猜测为检测到与已序列化对象地址相同，就不会进行对象网了。源码不想看o(￣ヘ￣o＃).
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Cat cat = new Cat("大黄", new Fish()); //唯一对象
		ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
		ObjectOutputStream out1 = new ObjectOutputStream(baos1);
		ObjectOutputStream out2 = new ObjectOutputStream(baos2);
		// out1.writeObject(cat);
		// TODO setFish()注释掉进行测试一，打开注释进行测试二
		cat.setFish(new Fish());
		out1.writeObject(cat);
		out2.writeObject(cat);

		ObjectInputStream in1 = new ObjectInputStream(new ByteArrayInputStream(baos1.toByteArray()));
		ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(baos2.toByteArray()));
		Cat cat1 = (Cat) in1.readObject();
		Cat cat2 = (Cat) in1.readObject();
		Cat cat3 = (Cat) in2.readObject();
		System.out.println("原本对象：");
		System.out.println(cat);
		System.out.println("缓冲区一的两个对象：");
		System.out.println(cat1);
		System.out.println(cat2);
		System.out.println("缓冲区二的两个对象：");
		System.out.println(cat3);
		out1.close();
		out2.close();
		in1.close();
		in2.close();
	}
}

class Cat implements Serializable {
	private String name;
	private Fish fish;

	public Cat(String name, Fish fish) {
		this.name = name;
		this.fish = fish;
	}

	public String getName() {
		return name;
	}

	public void setFish(Fish fish) {
		this.fish = fish;
	}

	@Override
	public String toString() {
		return "{" +
				"name='" + name + '\'' +
				"} Cat：" + hashCode() + ", Fish: " + fish.hashCode();
	}
}

class Fish implements Serializable {
}
/*
测试一：
原本对象：
{name='大黄'} Cat：1604839423, Fish: 670576685
缓冲区一的两个对象：
{name='大黄'} Cat：1031980531, Fish: 721748895
{name='大黄'} Cat：1031980531, Fish: 721748895
缓冲区二的两个对象：
{name='大黄'} Cat：1642534850, Fish: 1724731843

测试二：
原本对象：
{name='大黄'} Cat：1604839423, Fish: 186276003
缓冲区一的两个对象：
{name='大黄'} Cat：721748895, Fish: 1642534850
{name='大黄'} Cat：721748895, Fish: 1642534850
缓冲区二的两个对象：
{name='大黄'} Cat：1724731843, Fish: 1305193908
 */
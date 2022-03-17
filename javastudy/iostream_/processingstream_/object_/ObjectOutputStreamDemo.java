package javastudy.iostream_.processingstream_.object_;

import javastudy.iostream_.OpeTarget;

import java.io.*;

/**
 * 演示ObjectOutputStream的使用
 */
public class ObjectOutputStreamDemo {
	/*
	TODO ObjectOutputStream和ObjectInPutStream
		1.读写顺序要一致
		2.要求序列化或反序列化对象，需要实现Serializable(标记接口)
		3.序列化的类中建议添加 private static final long serialVersionUID=11; 来提高版本的兼容性
		4.序列化对象时，默认将里面所有属性都序列化，但是static和transient修饰的成员不会被序列化，但是可以直接作为参数传给writeObject()
		5.序列化对象时，要求里面属性的类型也需要实现序列化接口，像String、Integer这些类型都实现了Serializable，这是因为在序列化是有一个对象网，即
		追踪对象的所有引用，并保存那些对象，所以所有属性都需要继承接口(一定为null成员属性的就不用了)
		6.序列化具备可继承性，如果某个类实现了序列化，其子类不需要再实现了
		7.ObjectInputStream 死锁问题:
			(1) 在网络通讯中，主机与客户端若使用ObjectInputStream与ObjectOutputStream建立对象通讯，必须注重声明此两个对象的顺序
			(2) 主机端先建立ObjectInputStream后建立ObjectOutputStream，则对应地客户端要先建立ObjectOutputStream后建立
				ObjectInputStream，否则会造成两方互相等待数据而导致死锁(我写的有的代码死锁了，有的代码没死锁)
			(3) 原因:建立ObjectInputStream对象是需要先接收一定的header数据，接收到这些数据之前会处于阻塞状态
			(4) 解决方法，如果同时创建，那就要两端顺序反一下。最好是用的时候再创建(强迫症差评)。
	*/
	public static void main(String[] args) {
		//这里是读取文件的字节，文件后缀就是一个摆设，怎么写都可以
		ObjectOutputStream oos = null;

		try {
			oos = new ObjectOutputStream(new FileOutputStream(OpeTarget.TARGET));

			//包装类都实现了Serializable接口
			oos.writeInt(100);
			oos.writeChar('a');
			oos.writeBoolean(true);
			oos.writeDouble(15.2);
			oos.writeUTF("从入门到精通");

			//如果类不实现Serializable接口就好抛出异常
			//文件中记录了对象对应的类所处的位置和相关属性信息，在反序列化时依次得到对象
			oos.writeObject(new Dog("萌王", 20, "CHINA", "BLOCK"));
			System.out.println("对象序列化成功");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

//2.要求序列化或反序列化对象，需要实现Serializable
class Dog implements Serializable {
	@Serial
	private static final long serialVersionUID = 11;

	String name;
	int age;
	// 不会被序列化
	static String nation;
	transient String color;
	//5.序列化对象时，要求里面属性的类型也需要实现序列化接口，master类如果实现Serializable的话在序列化的时候会抛异常
	// Master master =new Master();

	public Dog(String name, int age, String nation, String color) {
		this.name = name;
		this.age = age;
		Dog.nation = nation;
		this.color = color;
	}

	@Override
	public String toString() {
		return "Dog{" +
				"name='" + name + '\'' +
				", age=" + age +
				", color='" + color + '\'' +
				", nation='" + nation + '\'' +
				'}';
	}
}

class Master {
}
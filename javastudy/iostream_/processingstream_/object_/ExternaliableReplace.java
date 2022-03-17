package javastudy.iostream_.processingstream_.object_;

import java.io.*;

/**
 * 演示Externaliable接口的替代方法
 */
public class ExternaliableReplace implements Serializable {
	private String a;
	private transient String b;

	public ExternaliableReplace(String aa, String bb) {
		a = "Not Transient: " + aa;
		b = "Transient: " + bb;
	}

	public String toString() {
		return a + " " + b;
	}

	/*
	TODO 替换Externaliable接口
	 1. 添加writeObject()和readObject()方法后，当对象被序列化或反序列化还原时都会自动调用这两个方法，而不是默认的序列化机制
	 2. 在调用ObjectOutputStream.writeObject()时会检查传递的对象是否实现了它的writeObject()，如果是就会跳过正常的序列化过程而调用它的
	 writeObject()，另一个同理。(当然是用反射来检查的)
	 3. 两个方法必须为private修饰的，否则不会被调用。相当于两对同名的方法，大佬看了都想骂人，我也感觉这样写莫名其妙
	 4. 可以用这个方法给继承接口的对象在序列化操作时添加额外操作
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException {
		System.out.println("ExternaliableReplace.writeObject");
		// 执行默认的writeObject()
		stream.defaultWriteObject();
		// b字段是transient修饰的，需要手动输出
		stream.writeObject(b);
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		System.out.println("ExternaliableReplace.readObject");
		stream.defaultReadObject();
		// 需要手动输入
		b = (String) stream.readObject();
	}

	public static void main(String[] args) {
		ExternaliableReplace e = new ExternaliableReplace("a", "b");
		System.out.println(e);
		// 这次没有选择文件，而是选择写入到缓冲区了
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     ObjectOutputStream out = new ObjectOutputStream(baos)) {
			out.writeObject(e);
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
			e = (ExternaliableReplace) in.readObject();
			System.out.println(e);
		} catch (Exception se) {
			se.printStackTrace();
		}
	}
}

package javastudy.iostream_.processingstream_.object_;

import javastudy.iostream_.OpeTarget;

import java.io.*;

/**
 * 实现Externalizable接口精细控制序列化
 */
public class ExternalizableTest {
	/*
	Externalizable接口
	1. 该接口可以实现部分序列化或反序列化时对成员进行一些处理
	1. 该接口继承了Serializable，并新增了两个方法writeExternal()和readExternal()，分别在序列化前和反序列化之前调用，通过实现这两个方法来
	对序列化进行精细控制
	2. 使用Serializable对象，对象完全以它存储的二进制为基础来构建的，不会调用构造器，而Externalizable对象会在反序列化前调用对象本身的默认构
	造器，之后再调用readExternal()
	3. Externalliable反序列化不会有对象网，必须手动在上面两个方法里面对对象进行保存和恢复
	 */
	public static void main(String[] args) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(OpeTarget.TARGET));
		     ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(OpeTarget.TARGET))) {
			Blip blip = new Blip("字符串", 10);
			System.out.println(blip);
			outputStream.writeObject(blip);
			outputStream.close();
			blip = (Blip) inputStream.readObject();
			System.out.println(blip);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
/*
Blip{s='字符串', i=10}
Blip.writeExternal
Blip.Blip
Blip.readExternal
Blip{s='字符串', i=10}
 */

class Blip implements Externalizable {
	private String s;
	private int i;

	// 如果把构造器私有化，就会在反序列化的地方报错
	public Blip() {
		System.out.println("Blip.Blip");
	}

	public Blip(String s, int i) {
		this.s = s;
		this.i = i;
	}

	/**
	 * 序列化之前调用
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		System.out.println("Blip.writeExternal");
		// 如果不保存数据和恢复数据，那么反序列化后两个字段也是null和0
		out.writeUTF(s);
		out.writeInt(i);
	}

	/**
	 * 反序列化时调用默认构造器之后调用
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		System.out.println("Blip.readExternal");
		s = in.readUTF();
		i = in.readInt();
	}

	@Override
	public String toString() {
		return "Blip{" +
				"s='" + s + '\'' +
				", i=" + i +
				'}';
	}
}
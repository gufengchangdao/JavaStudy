package javastudy.iostream_.nio_;//: io/GetData.java
// Getting different representations from a ByteBuffer

import java.nio.ByteBuffer;
/**
 * 演示ByteBuffer的类型转换
 */
public class GetData {
	private static final int BSIZE = 1024;

	public static void main(String[] args) {
		// TODO ByteBuffer提供了一系列的asXXXBuffer()，获取缓冲器上的这些视图后再获取数据和传入数据时都不用类型转换,会自动帮你转型好
		ByteBuffer bb = ByteBuffer.allocate(BSIZE);
		// 创建ByteBuffer时，所有值自动设为0
		int i = 0;
		while (i++ < bb.limit()) //既可以使用bb.hasRemaining()来检测又可以使用limit()来检测
			if (bb.get() != 0)
				System.out.println("非零");
		System.out.println("i: "+i);
		bb.rewind();

		// Store and read a char array:
		bb.asCharBuffer().put("生日快乐！"); //既可以传char类型，又可以传String类型
		char c;
		while ((c = bb.getChar()) != 0)
			System.out.print(c + " ");
		System.out.println();
		bb.rewind();

		// Store and read a short:
		bb.asShortBuffer().put((short) 471142); //向下转型需要手动
		System.out.println(bb.getShort());
		bb.rewind();

		// Store and read an int:
		bb.asIntBuffer().put(99471142);
		System.out.println(bb.getInt());
		bb.rewind();

        // Store and read a long:
		bb.asLongBuffer().put(99471142);
		System.out.println(bb.getLong());
		bb.rewind();

        // Store and read a float:
		bb.asFloatBuffer().put(99471142);
		System.out.println(bb.getFloat());
		bb.rewind();

        // Store and read a double:
		bb.asDoubleBuffer().put(99471142);
		System.out.println(bb.getDouble());
		bb.rewind();
	}
}
/* Output:
i: 1025
生 日 快 乐 ！
12390
99471142
99471142
9.9471144E7
9.9471142E7
*///:~

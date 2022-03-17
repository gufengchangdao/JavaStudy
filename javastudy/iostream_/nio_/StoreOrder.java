package javastudy.iostream_.nio_;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * 修改ByteBuffer中字节的存放次序
 */
public class StoreOrder {
	public static void main(String[] args) {
		/*
		TODO 字节存放次序
		 1. 不同机器可能采用不同方法来存储数据，高位优先是吧重要的字节放在地址最低的存储器单元，而地位优先是吧重要的字节放在地址最高的存储器单元
		 2. ByteBuffer默认是以高位优先的形式存储的，可以使用order()改变存储形式
		    比如两个字节时高位优先是 00000000 01100001,改为地位优先就会成为 01100001 00000000
		 */
		ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
		bb.asCharBuffer().put("abcdef");
		System.out.println(Arrays.toString(bb.array()));
		bb.rewind();

		bb.order(ByteOrder.BIG_ENDIAN); //改为高位优先
		bb.asCharBuffer().put("abcdef"); //修改了存储形式要重新写入数据
		System.out.println(Arrays.toString(bb.array()));
		bb.rewind();

		bb.order(ByteOrder.LITTLE_ENDIAN); //改为地位优先
		bb.asCharBuffer().put("abcdef");
		System.out.println(Arrays.toString(bb.array()));
	}
}
/*
两个字节表示一个char，所以可以看到相邻两个翻转说明高位变地位时，重要的字节由右边跑到了左边
[0, 97, 0, 98, 0, 99, 0, 100, 0, 101, 0, 102]
[0, 97, 0, 98, 0, 99, 0, 100, 0, 101, 0, 102]
[97, 0, 98, 0, 99, 0, 100, 0, 101, 0, 102, 0]
*/
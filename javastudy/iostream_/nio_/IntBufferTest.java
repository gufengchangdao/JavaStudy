package javastudy.iostream_.nio_;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * 演示ByteBuffer的视图之一的IntBuffer
 */
public class IntBufferTest {
	public static void main(String[] args) {
		// 获取到视图缓冲器，视图缓冲器的修改会影响到对应的ByteBuffer
		IntBuffer buffer = ByteBuffer.allocate(1024).asIntBuffer();
		// 批量存入数据
		buffer.put(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		// 与ByteBuffer的get()获取字节不同，这个是获取相应类型数据
		System.out.println(buffer.get(3));
		// 替换对应位置的数据
		buffer.put(3, 99);
		// 刷新
		buffer.flip();

		while (buffer.hasRemaining()) {
			System.out.print(buffer.get() + " ");
		}
	}
}
/*
4
1 2 3 99 5 6 7 8 9 10
*/
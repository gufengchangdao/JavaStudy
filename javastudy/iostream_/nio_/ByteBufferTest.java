package javastudy.iostream_.nio_;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.ByteBuffer;

/**
 * 演示缓冲器ByteBuffer的一些方法
 */
public class ByteBufferTest {
	/*
	TODO ByteBuffer 重要字段
	 1. mark: 用来保存position字段值的字段，调用mark()方法，可将position字段值赋值给mark字段，调用reset()方法，则将mark值再复制给
	 position字段，可以给position字段起到一个保存恢复的作用。
	 2. position：位置，表示缓冲区中下一个要被读或者写的元素索引。
	 3. Limit：上限，缓冲区中的最大操作位置，可以这样理解，假设缓冲区的capacity是512，但是Limit被设置为了256，那么该缓冲区的实际可用大小就
	 是256，256后的元素不可读也不可写。
	 4. capacity：缓冲区可以容纳的最大数据量。
	 */
	public static void main(String[] args) {
		// TODO 获取ByteBuffer对象
		//分配一个新的字节缓冲区，参数就是缓冲区的容量
		ByteBuffer buffer = ByteBuffer.allocate(12);
		// 使用allocateDirect来更快的移动数据，但是这种直接缓冲分配开支更大，并且具体操作随操作系统不同而不同，还得看实际效果
		ByteBuffer buffer1 = ByteBuffer.allocateDirect(12);
		ByteBuffer buffer2 = ByteBuffer.wrap(new byte[]{0, 0, 0, 'a'}); //数组大小的缓冲器

		// TODO ByteBuffer的方法一般都返回对象本身，因此可以链式调用，挺像C语言里操作指针的那个
		// 缓冲区的容量
		System.out.println(buffer.capacity());
		// 设置或返回此缓冲区的限制
		System.out.println(buffer.limit());
		buffer.limit(0);
		// 设置或返回这个缓冲区的位置
		System.out.println(buffer.position());
		buffer.position(buffer.capacity());
		// 清除缓冲区
		buffer.clear();
		// 重定向到开头，mark重新赋为-1
		buffer.rewind();
		// 将mark设置为此时的position
		buffer.mark();
		// 设置position回到mark标记处
		buffer.reset();
		// 用于准备从缓冲区读取已经写入的数据，内部是将limit设为position，position设为0，所以要注意，调用该方法后，再添加元素会从头开始，并且
		// 超过limit后还会报错
		buffer.flip();
		// 返回limit - position
		int remaining = buffer.remaining();
		// 判断position是否小于limit，使用flip()或limit()设置limit后就可以用这个方法循环读取缓冲区的信息了
		boolean b = buffer.hasRemaining();

		// 获取和添加数据，会改变position，但是如果指定索引，使用绝对的方式就不会改变position
		byte b1 = buffer.get();
		ByteBuffer put = buffer.put(b1);
		byte b2 = buffer.get(1);
	}
}

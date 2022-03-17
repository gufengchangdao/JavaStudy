package javastudy.iostream_.nio_;

import javastudy.iostream_.OpeTarget;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 演示MappedByteBuffer内存映射文件的使用
 */
public class MappedByteBufferTest {
	private static File file = new File(OpeTarget.TARGET);

	/*
	TODO MappedByteBuffer内存映射文件
	1. MappedByteBuffer继承自ByteBuffer，ByteBuffer的所有方法都能用，是一个抽象类，调用FileChannel.map()方法来获取对象
	2. 获取的对象实际上是其子类DirectByteBuffer实例的引用，实际上是DirectBuffer类型的缓冲区，使用该对象并不会消耗Java虚拟机内存堆。
	3. 一个映射一旦建立之后将保持有效，直到MappedByteBuffer对象被施以垃圾收集动作为止。关闭相关联的FileChannel不会破坏映射，只有丢弃缓冲区
	对象本身才会破坏该映射。
	4. MappedByteBuffer主要用在对大文件的读写或对实时性要求比较高的程序当中
	5. 所用到的原理有些复杂
	 */
	public static void main(String[] args) throws IOException {
		FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
		// 指定文件映射可以是可写的或只读的，还要指定文件映射位置和映射区域长度，这样就能映射文件的一个子范围
		MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, file.length());
		for (int i = 0; i < file.length(); i++)
			map.put((byte) 'x'); //将数据全替换为'x'
		System.out.println("输出完成");
		for (int i = 0; i < file.length(); i++) {
			System.out.print(map.get(i));
		}
	}
}

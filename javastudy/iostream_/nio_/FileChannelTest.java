package javastudy.iostream_.nio_;//: io/GetChannel.java

import javastudy.iostream_.OpeTarget;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/*
FileChannel
1. FileChannel是一个用读写，映射和操作一个文件的通道
2. 为了进一步提高速度，提供了通道和缓冲器，我们需要创建缓冲器(ByteBuffer)，将其提供给通道(FileChannel)，通道会从缓冲器中读取数据或是将数据发
   送给通道，我们只能操纵缓冲器移动数据，而这个缓冲器就是连接我们与文件的桥梁
3. 读取和写入速度比之前使用的更高
4. ByteBuffer只能存储字节，IO包下的FileInputStream和FileOutputStream、RandomAccessFile修改过，现在也持有FileChannel对象
*/

/**
 * 演示FileChannel的使用
 * 更高效的IO操作
 */
public class FileChannelTest {
	private static final int BSIZE = 1024;
	private static String TARGET = OpeTarget.TARGET;
	private static String SOURCE = OpeTarget.SOURCE;

	@Test
	public void add() throws IOException {
		// Add to the end of the file:
		try (FileChannel fc = new RandomAccessFile(TARGET, "rw").getChannel()) {
			fc.position(fc.size()); // 移动指针到末尾
			fc.write(ByteBuffer.wrap("Some more".getBytes()));
		}
	}

	@Test
	public void write() throws Exception {
		// 写文件，可以使用wrap()或put()，不用指定缓冲器大小
		// try (FileChannel fc = new FileOutputStream(TARGET).getChannel()) {
		// 	fc.write(ByteBuffer.wrap("Some text ".getBytes()));
		// }

		try (FileChannel fc = new FileOutputStream(TARGET).getChannel()) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(BSIZE);
			byteBuffer.put("需要写入的数据".getBytes());
			byteBuffer.flip();
			while (byteBuffer.hasRemaining())
				fc.write(byteBuffer);
			// byteBuffer.clear(); //清除缓冲
			// fc.truncate(BSIZE/2); //将此通道的文件截断为给定大小
			// fc.force(true); //强制数据刷新到硬盘中
		}
	}

	@Test
	public void copy01() throws IOException {
		try (FileChannel fic = new FileInputStream(SOURCE).getChannel();
		     FileChannel foc = new FileOutputStream(TARGET).getChannel()) {
			ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
			// 从此通道中读取字节到缓冲器中。从该通道的当前文件位置开始读取字节，然后使用实际读取的字节数更新文件位置。
			while (fic.read(buffer) != -1) {
				buffer.flip(); //准备缓冲器以便信息可以被write()提取
				foc.write(buffer); //提取信息

				//设置指针到buffer的开头，顺便把信息打印出来
				buffer.position(0);
				// 检查是否还有数据未写入
				while (buffer.hasRemaining())
					System.out.print(buffer.get());

				buffer.clear(); //清空信息
			}
		}
		System.out.println("信息拷贝完成");
	}

	@Test
	public void copy02() throws IOException {
		// 可以直接使用transferTo()和transferFrom()连接两个通道，代码更简洁，效率更高一点
		try (FileChannel fic = new FileInputStream(SOURCE).getChannel();
		     FileChannel foc = new FileOutputStream(TARGET).getChannel()) {
			fic.transferTo(0, fic.size(), foc);
			foc.transferFrom(fic, 0, fic.size());
		}
		System.out.println("信息拷贝完成");
	}
}


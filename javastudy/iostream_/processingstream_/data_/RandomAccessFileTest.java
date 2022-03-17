package javastudy.iostream_.processingstream_.data_;

import javastudy.iostream_.OpeTarget;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 演示使用RandomAccessFile操作文件
 * RandomAccessFile提供只读模式、读写模式，允许读、写、随机访问、插入等操作
 */
public class RandomAccessFileTest {

	static void display() throws IOException {
		// 只读模式
		try (RandomAccessFile rf = new RandomAccessFile(OpeTarget.TARGET, "r")) {
			for (int i = 0; i < 7; i++)
				System.out.println("Value " + i + ": " + rf.readDouble());
			System.out.println(rf.readUTF());
		}
	}

	public static void main(String[] args) throws IOException {
		// 读写模式
		try (RandomAccessFile rf = new RandomAccessFile(OpeTarget.TARGET, "rw");) {
			for (int i = 0; i < 7; i++)
				rf.writeDouble(i * 1.414);
			rf.writeUTF("The end of the file");
		}
		display();

		try (RandomAccessFile rf = new RandomAccessFile(OpeTarget.TARGET, "rw");) {
			// 设置文件指针偏移，从该文件的开头测量，发生下一次读取或写入。使用seek()可以在文件中到处移动
			rf.seek(5 * 8); //因为double总是8字节长
			rf.writeDouble(47.0001);
		}
		display();
	}
}
/*
Value 0: 0.0
Value 1: 1.414
Value 2: 2.828
Value 3: 4.242
Value 4: 5.656
Value 5: 7.069999999999999
Value 6: 8.484
The end of the file
Value 0: 0.0
Value 1: 1.414
Value 2: 2.828
Value 3: 4.242
Value 4: 5.656
Value 5: 47.0001
Value 6: 8.484
The end of the file
*/
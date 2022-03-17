package javastudy.iostream_.nio_;

import javastudy.iostream_.OpeTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 演示文件加锁
 */
public class FileLockTest {
	/*
	文件加锁是直接映射到本地操作系统，对其他的操作系统是可见的
	1. 拿到文件通道
	2. 对通道加锁
	3. 释放锁
	 */
	public static void main(String[] args) {
		try (FileChannel channel = new FileOutputStream(OpeTarget.TARGET).getChannel();
		     FileLock fileLock = channel.tryLock()) { //非阻塞式，独占锁，获取不到返回null
			//阻塞式，直到获取锁，或线程关闭，或通道关闭
			// FileLock fileLock1 = channel.lock();
			//对文件的一部分上锁，文件变大变小加锁区域大小也不会变
			// FileLock fileLock1 = channel.tryLock(0, new File(OpeTarget.TARGET).length(), false);
			if (fileLock != null) {
				boolean shared = fileLock.isShared();//判断是否是共享锁
				fileLock.release();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
class A{
	public static void main(String[] args) {

	}
}
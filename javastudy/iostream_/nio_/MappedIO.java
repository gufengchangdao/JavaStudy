package javastudy.iostream_.nio_;//: io/MappedIO.java

import javastudy.iostream_.OpeTarget;

import java.io.*;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 比较IO和NIO的读写效率
 */
public class MappedIO {
	private static int numOfInts = 4000000; //测试次数
	private static int numOfUbuffInts = 200000;

	/**
	 * 测试框架
	 */
	private abstract static class Tester {
		private String name;

		public Tester(String name) {
			this.name = name;
		}

		/**
		 * 进行测试，并打印测试花费的秒数
		 */
		public void runTest() {
			System.out.print(name + ": ");
			try {
				long start = System.nanoTime();
				test();
				double duration = System.nanoTime() - start;
				System.out.format("%.2f\n", duration / 1.0e9);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// 测试方法
		public abstract void test() throws IOException;
	}

	/**
	 * 测试内容，包括初始化IO对象的时间，比较的是总体
	 */
	private static final Tester[] tests = {
			new Tester("Stream int Write") {
				public void test() throws IOException {
					DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(OpeTarget.TARGET)));
					for (int i = 0; i < numOfInts; i++)
						dos.writeInt(i);
					dos.close();
				}
			},
			new Tester("Mapped int Write") {
				public void test() throws IOException {
					FileChannel fc = new RandomAccessFile(OpeTarget.TARGET, "rw").getChannel();
					IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
					for (int i = 0; i < numOfInts; i++)
						ib.put(i);
					fc.close();
				}
			},
			new Tester("Stream int Read") {
				public void test() throws IOException {
					DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(OpeTarget.TARGET)));
					for (int i = 0; i < numOfInts; i++)
						dis.readInt();
					dis.close();
				}
			},
			new Tester("Mapped int Read") {
				public void test() throws IOException {
					FileChannel fc = new FileInputStream(OpeTarget.TARGET).getChannel();
					IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asIntBuffer();
					while (ib.hasRemaining())
						ib.get();
					fc.close();
				}
			},
			new Tester("Stream int Read/Write") {
				public void test() throws IOException {
					RandomAccessFile raf = new RandomAccessFile(new File(OpeTarget.TARGET), "rw");
					raf.writeInt(1);
					for (int i = 0; i < numOfUbuffInts; i++) {
						raf.seek(raf.length() - 4);
						raf.writeInt(raf.readInt());
					}
					raf.close();
				}
			},
			new Tester("Mapped int Read/Write") {
				public void test() throws IOException {
					FileChannel fc = new RandomAccessFile(new File(OpeTarget.TARGET), "rw").getChannel();
					IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
					ib.put(0);
					for (int i = 1; i < numOfUbuffInts; i++)
						ib.put(ib.get(i - 1));
					fc.close();
				}
			},
	};

	public static void main(String[] args) {
		for (Tester test : tests)
			test.runTest();
	}
}
/* Output: (90% match)
Stream int Write: 0.16
Mapped int Write: 0.01
Stream int Read: 0.14
Mapped int Read: 0.01
Stream int Read/Write: 4.87
Mapped int Read/Write: 0.01
TODO 可以看到，使用MappedByteBuffer的读写都比Stream更快，并且如果不是转为IntBuffer而是直接操作byte的话，速度会更快(测试过)
*///:~

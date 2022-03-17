package javastudy.thread_.thread.interrupt;//: concurrency/NIOInterruption.java
// Interrupting a blocked NIO channel.

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


class NIOBlocked implements Runnable {
	private final SocketChannel sc;

	public NIOBlocked(SocketChannel sc) {
		this.sc = sc;
	}

	public void run() {
		try {
			System.out.println("Waiting for read() in " + this.hashCode());
			sc.read(ByteBuffer.allocate(1));
		} catch (ClosedByInterruptException e) {
			System.out.println("ClosedByInterruptException " + this.hashCode());
		} catch (AsynchronousCloseException e) {
			System.out.println("AsynchronousCloseException " + this.hashCode());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Exiting NIOBlocked.run() " + this.hashCode());
	}
}

/**
 * 演示nio包下的类的中断效果
 */
public class NIOInterruption {
	/*
	1. io包下的类在read()后，如果关闭流，往往都是直接关闭，不会抛异常，并且在下面的情况还会出现问题
		System.in.read();
		System.in.close();
		System.out.println("sout"); //System.in虽然关闭了，但是程序会在这里死锁，只有向控制台内容后才会执行打印操作，其他流没见这种情况
	2. nio类提供了更人性化的I/O中断，可以中断抛出异常，可以关闭资源抛出异常
	 */
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		ServerSocket server = new ServerSocket(8080);
		InetSocketAddress isa = new InetSocketAddress("localhost", 8080);
		SocketChannel sc1 = SocketChannel.open(isa);
		SocketChannel sc2 = SocketChannel.open(isa);
		Future<?> f = exec.submit(new NIOBlocked(sc1));
		exec.execute(new NIOBlocked(sc2));
		exec.shutdown();
		TimeUnit.SECONDS.sleep(1);

		// 使用shutdownNow()直接让所有线程抛出中断异常
		// exec.shutdownNow();

		// 指定某一个线程中断，这种单个中断很好用
		// f.cancel(true);
		// TimeUnit.SECONDS.sleep(1);

		// 通过关闭通道释放块
		// sc2.close();
	}
} /* Output: (Sample)
Waiting for read() in 761499941
Waiting for read() in 983013189
ClosedByInterruptException 761499941
Exiting NIOBlocked.run() 761499941
AsynchronousCloseException 983013189
Exiting NIOBlocked.run() 983013189
*///:~

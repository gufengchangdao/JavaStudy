package javastudy.thread_.synchronize;

import javastudy.iostream_.OpeTarget;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Sender implements Runnable {
	private BufferedReader br;
	private PipedWriter pw;

	public Sender() throws FileNotFoundException {
		br = new BufferedReader(new FileReader(OpeTarget.SOURCE));
		pw = new PipedWriter();
	}

	public PipedWriter getPw() {
		return pw;
	}

	@Override
	public void run() {
		try {
			int s;
			while ((s = br.read()) != -1) {
				pw.write(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class Receiver implements Runnable {
	private PipedReader pr;

	public Receiver(Sender sender) throws IOException {
		pr = new PipedReader(sender.getPw());
	}

	@Override
	public void run() {
		try {
			int cha;
			while ((cha = pr.read()) != -1) {
				System.out.print((char) cha);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/**
 * 演示PipeWriter和PipedReader的使用，
 * 两个线程间使用管道进行输入/输出
 */
public class PipeIOTest {
	public static void main(String[] args) throws IOException {
		ExecutorService es = Executors.newCachedThreadPool();
		Sender sender = new Sender();
		Receiver receiver = new Receiver(sender);
		// 必须等管道连通后才能执行任务，连通前不能调用write()
		es.execute(sender);
		es.execute(receiver);

		es.shutdown();
	}
}

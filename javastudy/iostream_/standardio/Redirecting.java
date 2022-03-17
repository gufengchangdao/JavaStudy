package javastudy.iostream_.standardio;//: io/Redirecting.java
// Demonstrates standard I/O redirection.

import javastudy.iostream_.OpeTarget;

import java.io.*;

/**
 * 演示标准输入输出流的重定向功能
 */
public class Redirecting {
	public static void main(String[] args) throws IOException {
		PrintStream console = System.out;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(OpeTarget.SOURCE));
		PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(OpeTarget.TARGET)));
		// 重定向只能使用字节流
		System.setIn(in);
		System.setOut(out);
		System.setErr(out);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //重定向后的
		String s;
		while ((s = br.readLine()) != null)
			System.out.println(s); //重定向后的
		out.close(); // 关闭前会刷新缓冲区的
		System.setOut(console);
		System.out.println("打印完成，请查看 "+OpeTarget.TARGET);
	}
} ///:~

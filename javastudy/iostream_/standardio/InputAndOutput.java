package javastudy.iostream_.standardio;

import java.io.*;
import java.util.Scanner;

/**
 * 标准I/O
 */
public class InputAndOutput {
	public static void main(String[] args) {
		InputStream in = System.in;
		//编译类型:InputStream
		//运行类型:BufferedInputStream(BufferedInputStream-->InputStream)
		//表示的是标志输入  键盘
		System.out.println(System.in.getClass());

		PrintStream out = System.out;
		//编译类型:PrintStream
		//运行类型:PrintStream(PrintStream-->filterOutStream-->OutStream)
		//表示的是标志输出  显示器
		System.out.println(System.out.getClass());

		// 可以对InputStream进行不同的包装
		Scanner scanner = new Scanner(System.in);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String next = scanner.nextLine();
		// bufferedReader.readLine();
		if (next.equals(""))
			System.out.println("你直接回车了");
		else
			System.out.println("你输出的是 " + next);

		// 对PrintStream进行包装
		PrintWriter printWriter = new PrintWriter(System.out,true); //第二个参数设为true，开启自动flush
		printWriter.println("不是使用的PrintStream，而是使用的是PrintWriter");

	}
}

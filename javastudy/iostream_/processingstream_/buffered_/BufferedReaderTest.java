package javastudy.iostream_.processingstream_.buffered_;

import javastudy.iostream_.OpeTarget;
import javastudy.utilities.FileUtils;

import java.io.*;

/**
 * 演示Reader和Writer的一些类用法
 */
public class BufferedReaderTest {

	public static void main(String[] args) {
		try (BufferedReader in = new BufferedReader(new StringReader(FileUtils.read(OpeTarget.SOURCE)));
		     PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(OpeTarget.TARGET)))) {
			int lineCount = 1;
			String s;
			while ((s = in.readLine()) != null)
				out.println((lineCount++) + ": " + s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("拷贝完成");
	}
}

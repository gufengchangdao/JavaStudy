package javastudy.iostream_.compress;
// {Args: GZIPcompress.java}

import javastudy.iostream_.OpeTarget;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 演示GZIPOutputStream和GZIPInputStream，创建压缩包并读取压缩包信息
 */
public class GZIPCompress {
	private static String TARGET = "src/javastudy/iostream_/compress/temp.gz";

	public static void main(String[] args) {
		try (BufferedReader reader = new BufferedReader(new FileReader(OpeTarget.SOURCE));
		     BufferedOutputStream outputStream = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(TARGET)))) {
			String s;
			while ((s = reader.readLine()) != null) {
				outputStream.write((s + "\n").getBytes(StandardCharsets.UTF_8));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("创建完成，读取并打印内容: ");

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(TARGET))))) {
			String s;
			while ((s = reader.readLine()) != null)
				System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
} /* (Execute to see output) *///:~

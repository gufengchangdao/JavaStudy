package javastudy.utilities;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * 工具类，文件列表
 */
public class FileList extends ArrayList<String> {
	/**
	 * 按照分割符分割文件内容，并存入集合内
	 *
	 * @param fileName 文件全路径
	 * @param splitter 分割符
	 */
	public FileList(String fileName, String splitter) {
		super(Arrays.asList(FileUtils.read(fileName).split(splitter)));
		if (get(0).equals("")) remove(0);
	}

	/**
	 * 以'\n'为分割符分割文件内容，并存入集合内
	 *
	 * @param fileName 文件全路径
	 */
	public FileList(String fileName) {
		this(fileName, "\n");
	}

	/**
	 * 将集合中的内容输出到指定位置
	 *
	 * @param fileName 文件名
	 */
	public void write(String fileName) throws RuntimeException {
		try (PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile())) {
			for (String item : this)
				out.println(item);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		String file = FileUtils.read("TextFile.java");
		FileUtils.write("test.txt", file);
		FileList text = new FileList("test.txt");
		text.write("test2.txt");
		// Break into unique sorted list of words:
		TreeSet<String> words = new TreeSet<String>(new FileList("TextFile.java", "\\W+"));
		// Display the capitalized words:
		System.out.println(words.headSet("a"));
	}
}

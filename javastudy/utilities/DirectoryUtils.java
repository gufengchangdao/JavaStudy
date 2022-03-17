package javastudy.utilities;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 工具类，用来查询目录下的文件
 */
public final class DirectoryUtils {
	/**
	 * 查询并筛选指定目录下的文件
	 *
	 * @param dir   要查询的目录
	 * @param regex 正则表达式
	 * @return 筛选后的文件数组
	 */
	public static File[] local(File dir, final String regex) {
		if (!dir.isDirectory()) { //必须得是目录
			new NotDirectoryException(dir.getName()).printStackTrace();
			System.exit(-1);
		}
		return dir.listFiles(new FilenameFilter() {
			private Pattern pattern = Pattern.compile(regex);

			public boolean accept(File dir, String name) {
				return pattern.matcher(new File(name).getName()).matches(); //匹配文件名
			}
		});
	}

	/**
	 * 查询并筛选指定目录下的文件
	 *
	 * @param path  要查询的目录
	 * @param regex 正则表达式
	 * @return 筛选后的文件数组
	 */
	public static File[] local(String path, final String regex) { // Overloaded
		return local(new File(path), regex);
	}

	/**
	 * 用来保存文件集信息的类
	 */
	public static class TreeInfo implements Iterable<File> {
		public List<File> files = new ArrayList<File>();
		public List<File> dirs = new ArrayList<File>();

		// The default iterable element is the file list:
		public Iterator<File> iterator() {
			return files.iterator();
		}

		void addAll(TreeInfo other) {
			files.addAll(other.files);
			dirs.addAll(other.dirs);
		}

		public String getFileInfo() {
			StringBuilder builder = new StringBuilder();
			builder.append("files:[\n");
			for (File file : files) {
				builder.append("   [").append(file.getName()).append("]( ").append(file.getAbsolutePath()).append(" )\n");
			}
			builder.append("]\n");
			return builder.toString();
		}

		public String getDireInfo() {
			StringBuilder builder = new StringBuilder();
			builder.append("dirs:[\n");
			for (File dir : dirs) {
				builder.append("   ( ").append(dir.getAbsolutePath()).append(" )\n");
			}
			builder.append("]\n");
			return builder.toString();
		}

		public String toString() {
			return getDireInfo() + getFileInfo();
		}
	}

	public static TreeInfo walk(String start, String regex) { // Begin recursion
		return recurseDirs(new File(start), regex);
	}

	public static TreeInfo walk(File start, String regex) { // Overloaded
		return recurseDirs(start, regex);
	}

	public static TreeInfo walk(File start) { // Everything
		return recurseDirs(start, ".*");
	}

	public static TreeInfo walk(String start) {
		return recurseDirs(new File(start), ".*");
	}

	/**
	 * 查询并筛选指定目录下的所有文件和目录
	 *
	 * @param startDir 指定目录
	 * @param regex    正则表达式
	 * @return 存有文件集信息的对象
	 */
	static TreeInfo recurseDirs(File startDir, String regex) {
		TreeInfo result = new TreeInfo();
		if (!startDir.isDirectory()) { //必须得是目录
			new NotDirectoryException(startDir.getName()).printStackTrace();
			System.exit(-1);
		}
		for (File item : startDir.listFiles()) {
			if (item.isDirectory()) {
				result.dirs.add(item);
				result.addAll(recurseDirs(item, regex));
			} else // Regular file
				if (item.getName().matches(regex))
					result.files.add(item);
		}
		return result;
	}

	// Simple validation test:
	public static void main(String[] args) {
		if (args.length == 0)
			System.out.println(walk("src", ".+\\.java$").getFileInfo());
		else
			for (String arg : args)
				System.out.println(walk(arg));
	}
} ///:~

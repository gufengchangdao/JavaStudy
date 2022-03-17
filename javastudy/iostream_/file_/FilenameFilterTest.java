package javastudy.iostream_.file_;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 演示文件名过滤器的使用
 */
public class FilenameFilterTest implements FilenameFilter {
	private Pattern pattern; //编译后的正则表达式

	public FilenameFilterTest(String regex) {
		pattern = Pattern.compile(regex);
	}

	@Override
	public boolean accept(File dir, String name) {
		return pattern.matcher(name).matches();
	}

	public static void main(String[] args) {
		File path = new File("src/javastudy/iostream_/file_");
		String[] list = path.list(new FilenameFilterTest(".+\\.java$"));
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for (String dirItem : list)
			System.out.println(dirItem);
	}
}

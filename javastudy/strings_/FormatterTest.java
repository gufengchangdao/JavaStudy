package javastudy.strings_;

import java.io.FileNotFoundException;
import java.util.Formatter;

/**
 * 演示Formatter类
 */
public class FormatterTest {
	public static void main(String[] args) throws FileNotFoundException {
		// Formatter提供格式化功能并把字符串输出到指定位置
		// 输出到控制台
		Formatter formatter1 = new Formatter(System.out);
		formatter1.format("%s，难度为%c，级别为%d", "忍者神龟", 'A', 99);
		// 输出到文件
		Formatter formatter2 = new Formatter("src/javastudy/strings_/temp");
		formatter2.format("%s，难度为%c，级别为%d", "忍者神龟", 'A', 99);
		formatter2.flush(); //必要
	}
}

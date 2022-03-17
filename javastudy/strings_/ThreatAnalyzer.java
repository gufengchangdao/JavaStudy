package javastudy.strings_;//: strings/ThreatAnalyzer.java

import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * 演示Scanner中正则表达式的使用
 */
public class ThreatAnalyzer {
	static String threatData =
			"58.27.82.161@02/10/2005\n" +
					"204.45.234.40@02/11/2005\n" +
					"58.27.82.161@02/11/2005\n" +
					"58.27.82.161@02/12/2005\n" +
					"58.27.82.161@02/12/2005\n" +
					"[Next log section with different data format]";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(threatData);
		String pattern = "(\\d+[.]\\d+[.]\\d+[.]\\d+)@(\\d{2}/\\d{2}/\\d{4})"; //不能含有定界符，像"\n"
		while (scanner.hasNext(pattern)) { //检测
			scanner.next(pattern);
			MatchResult match = scanner.match(); //获得匹配的结果
			String ip = match.group(1);
			String date = match.group(2);
			System.out.format("Threat on %s from %s\n", date, ip);
		}
	}
} /* Output:
Threat on 02/10/2005 from 58.27.82.161
Threat on 02/11/2005 from 204.45.234.40
Threat on 02/11/2005 from 58.27.82.161
Threat on 02/12/2005 from 58.27.82.161
Threat on 02/12/2005 from 58.27.82.161
*///:~

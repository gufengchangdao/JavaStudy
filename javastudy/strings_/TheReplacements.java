package javastudy.strings_;//: strings/TheReplacements.java


import javastudy.utilities.FileUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*! Here's a block of text to use as input to
    the regular expression matcher. Note that we'll
    first extract the block of text by looking for
    the special delimiters, then process the
    extracted block. !*/

/**
 * 演示String和Matcher类中replace方法的使用
 */
public class TheReplacements {
	public static void main(String[] args) throws Exception {
		String s = FileUtils.read("src/javastudy/strings_/TheReplacements.java");
		// 匹配指定的文本块
		Matcher mInput = Pattern.compile("/\\*!(.*)!\\*/", Pattern.DOTALL).matcher(s); //表达式匹配终结符
		if (mInput.find()) s = mInput.group(1);
		// 用一个空格替代连续的多个空格
		s = s.replaceAll(" {2,}", " ");
		// 去掉每一行的空格，Must enable MULTILINE mode:
		s = s.replaceAll("(?m)^ +", "");// TODO: 2022/2/25 0025 (?m)没看懂是什么
		System.out.println(s);

		s = s.replaceFirst("[aeiou]", "(VOWEL1)");
		StringBuffer sbuf = new StringBuffer();
		Matcher m = Pattern.compile("[aeiou]").matcher(s);
		// 执行渐进式的替换，替换后附加到StringBuffer缓存中
		while (m.find())
			m.appendReplacement(sbuf, m.group().toUpperCase());
		// 把剩下的内容也放进缓冲区
		m.appendTail(sbuf);
		System.out.println(sbuf);
	}
} /* Output:
Here's a block of text to use as input to
the regular expression matcher. Note that we'll
first extract the block of text by looking for
the special delimiters, then process the
extracted block.
H(VOWEL1)rE's A blOck Of tExt tO UsE As InpUt tO
thE rEgUlAr ExprEssIOn mAtchEr. NOtE thAt wE'll
fIrst ExtrAct thE blOck Of tExt by lOOkIng fOr
thE spEcIAl dElImItErs, thEn prOcEss thE
ExtrActEd blOck.
*///:~

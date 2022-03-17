package javastudy.strings_;//: strings/Groups.java

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式：演示Matcher类的使用
 */
public class MatcherTest {
	static public final String POEM =
			"Twas brillig, and the slithy toves\n" +
					"Did gyre and gimble in the wabe.\n" +
					"All mimsy were the borogoves,\n" +
					"And the mome raths outgrabe.\n\n" +
					"Beware the Jabberwock, my son,\n" +
					"The jaws that bite, the claws that catch.\n" +
					"Beware the Jubjub bird, and shun\n" +
					"The frumious Bandersnatch.";

	public static void main(String[] args) {
		// TODO Pattern
		Pattern pattern = Pattern.compile("(?m)(\\S+)\\s+((\\S+)\\s+(\\S+))$"); //Pattern相当于编译后的正则表达式
		// Pattern pattern = Pattern.compile("(?m)(\\S+)\\s+((\\S+)\\s+(\\S+))$",Pattern.CASE_INSENSITIVE); //不区分大小写
		// String[] split = pattern.split(POEM); //断开
		// TODO Matcher
		Matcher m = pattern.matcher(POEM); //Matcher可获取与组相关的信息，不包括第0组
		while (m.find()) { //查找符合表达式的字串
			System.out.println("该组从第" + m.start() + "个字符到" + m.end() + "个字符");//前一次匹配寻找到的组的起始和结束索引
			for (int j = 0; j <= m.groupCount(); j++) //遍历一个字串内的所有组
				System.out.print("[" + m.group(j) + "]"); //获取第j组的字符串
			System.out.println();
		}

		m.reset(); //将Matcher重新设置为当前字符序列的起始位置
		m.reset("reset()方法会将现有的Matcher对象应用于一个新的字符串上");
	}
}
/* Output:
该组从第18个字符到34个字符
[the slithy toves][the][slithy toves][slithy][toves]
该组从第55个字符到67个字符
[in the wabe.][in][the wabe.][the][wabe.]
该组从第78个字符到97个字符
[were the borogoves,][were][the borogoves,][the][borogoves,]
该组从第106个字符到126个字符
[mome raths outgrabe.][mome][raths outgrabe.][raths][outgrabe.]
该组从第139个字符到158个字符
[Jabberwock, my son,][Jabberwock,][my son,][my][son,]
该组从第183个字符到200个字符
[claws that catch.][claws][that catch.][that][catch.]
该组从第219个字符到233个字符
[bird, and shun][bird,][and shun][and][shun]
该组从第234个字符到260个字符
[The frumious Bandersnatch.][The][frumious Bandersnatch.][frumious][Bandersnatch.]
*///:~

package javastudy.utilities;

import java.io.CharArrayWriter;

public final class StringUtils {
	/**
	 * 大驼峰命名转匈牙利命名，例如 AbcDe -> abc_de
	 *
	 * @param uc 大驼峰命名格式的字符串
	 * @return 匈牙利命名格式的字符串
	 */
	public static String uppercaseToUnderline(String uc) {
		char[] chars = uc.toCharArray();
		try (CharArrayWriter writer = new CharArrayWriter((int) (chars.length * 1.3))) {
			writer.append(Character.toLowerCase(chars[0]));
			for (int i = 1; i < chars.length; i++) {
				if (chars[i] >= 'A' && chars[i] <= 'Z') {
					writer.append('_');
					chars[i] = Character.toLowerCase(chars[i]);
				}
				writer.append(chars[i]);
			}
			return writer.toString();
		}
	}

	public static void main(String[] args) {
	}
}

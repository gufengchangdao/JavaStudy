package javastudy.errorProcess.exception_.skill;

import java.io.*;

/**
 * 使用嵌套try字句创建资源
 * 对于在构造阶段可能会抛异常并且需要清理的类可以使用嵌套try字句
 */
public class Cleanup {
	public Cleanup() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/Test.java"));
			try {
				String s;
				while ((s = reader.readLine()) != null) ;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				reader.close();
			}
		} catch (IOException e) { //如果reader对象创建失败是不需要close()的，所以close()方法写在内部try语句中
			e.printStackTrace();
		}
	}
}

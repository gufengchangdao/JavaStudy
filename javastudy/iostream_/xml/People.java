package javastudy.iostream_.xml;//: xml/People.java
// {Requires: nu.xom.Node; You must install
// the XOM library from http://www.xom.nu }
// {RunFirst: Person}

import javastudy.iostream_.OpeTarget;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

import java.util.ArrayList;

/**
 * 使用XOM反序列化
 */
public class People extends ArrayList<Person> {
	public People(String fileName) throws Exception {
		Document doc = new Builder().build(fileName);
		Elements elements = doc.getRootElement().getChildElements();
		for (Element element : elements) { //这里是假设element没有嵌套的element了
			add(new Person(element));
		}
	}

	public static void main(String[] args) throws Exception {
		People p = new People(OpeTarget.TARGET);
		System.out.println(p);
	}
} /* Output:
[Dr. Bunsen Honeydew, Gonzo The Great, Phillip J. Fry]
*///:~

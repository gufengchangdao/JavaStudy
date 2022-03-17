package javastudy.iostream_.xml;//: xml/Person.java
// Use the XOM library to write and read XML
// {Requires: nu.xom.Node; You must install
// the XOM library from http://www.xom.nu }

import javastudy.iostream_.OpeTarget;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 初识XOM工具
 */
public class Person {
	private String first, last;
	private String address;

	public Person(String first, String last,String address) {
		this.first = first;
		this.last = last;
		this.address = address;
	}

	// 通过person对象生成Element对象
	public Element getXML() {
		Element person = new Element("person");
		Element firstName = new Element("姓");
		firstName.appendChild(first);
		Element lastName = new Element("名");
		lastName.appendChild(last);
		Element ad = new Element("地址");
		ad.appendChild(address);
		person.appendChild(firstName);
		person.appendChild(lastName);
		person.appendChild(ad);
		return person;
	}

	// 通过element对象构造Person对象
	public Person(Element person) {
		first = person.getFirstChildElement("first").getValue();
		last = person.getFirstChildElement("last").getValue();
	}

	public String toString() {
		return first + " " + last;
	}

	// 输出数据
	public static void format(OutputStream os, Document doc) throws Exception {
		Serializer serializer = new Serializer(os, "UTF-8");
		serializer.setIndent(4);
		serializer.setMaxLength(60);
		serializer.write(doc);
		serializer.flush();

		// os.write(doc.toXML().getBytes(StandardCharsets.UTF_8)); //这个也能打印，但是只是一行，可读性差
	}

	public static void main(String[] args) throws Exception {
		List<Person> people = Arrays.asList(new Person("汤姆逊", "冲锋枪","瀛洲"),
				new Person("熔岩", "加特林","巨人城"),
				new Person("埃菲尔", "巨人","幻岛"));
		System.out.println(people);
		Element root = new Element("people"); //根元素
		for (Person p : people) //把每一个Person都加入根元素
			root.appendChild(p.getXML());
		Document doc = new Document(root);
		format(System.out, doc);
		format(new BufferedOutputStream(new FileOutputStream(OpeTarget.TARGET)), doc);
	}
} /* Output:
[Dr. Bunsen Honeydew, Gonzo The Great, Phillip J. Fry]
<?xml version="1.0" encoding="ISO-8859-1"?>
<people>
    <person>
        <first>Dr. Bunsen</first>
        <last>Honeydew</last>
    </person>
    <person>
        <first>Gonzo</first>
        <last>The Great</last>
    </person>
    <person>
        <first>Phillip J.</first>
        <last>Fry</last>
    </person>
</people>
*///:~

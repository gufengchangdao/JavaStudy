package javastudy.collection_.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 检查型视图
 * 用于解决问题：将错误类型的元素混入泛型集合中的情况
 */
public class CheckView {
	public static void main(String[] args) {
		// TODO 问题: list转型为list1后，类的泛型类型不是String了，所以编译器不会报错(检查不了)，虚拟机也不行(虚拟机没有泛型类型)
		ArrayList<String> list = new ArrayList<>();
		ArrayList list1 = list;
		list1.add(new Date());
		System.out.println(list); //[Mon Jan 10 16:33:15 CST 2022]
		Object obj = list1.get(0); //泛型类型为Object类型，强转就会抛异常

		// TODO 解决
		list.clear();
		List<String> stringList = Collections.checkedList(list, String.class); //将ArrayList包装一下
		List list2 = stringList;
		// 原理：add()方法会先进行检测，是否和存储的类型(这里是String类对象)一致，不一致就会抛出ClassCastException异常
		list2.add(new Date());

	}
}

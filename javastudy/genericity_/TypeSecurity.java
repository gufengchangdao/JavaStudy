package javastudy.genericity_;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 泛型的动态类型安全
 */
public class TypeSecurity {
	public static void main(String[] args) {
		// TODO 问题：丢失了泛型类型,add()方法也就检测不出来了
		// ArrayList<String> list = new ArrayList<>();
		List list = new ArrayList<>();
		list.add(1);

		// TODO 解决：使用Collections提供的一系列checked方法
		//  原理：重新包装
		//      Class对象是泛型的，对集合重新包装，集合中泛型类型就是采用Class对象的泛型类型
		List list2 = new ArrayList<>();
		list2.add(1);
		list2 = Collections.checkedList(list2, String.class);
		System.out.println(list2); //[1] ，不会对已有的元素进行检查
	}
}

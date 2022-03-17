/*
TreeSet
    底层为二叉树
    不传入比较器时会调用默认的比较器，会先调用Objects.requireNonNull(key)方法判断key是否为null，为null时就会抛异常
    而传入比较器就会在比较的时候使用传入的比较器，只要在比较器中考虑了Object为null的情况也是可以传入null的
 */
package javastudy.collection_.set_;

import java.util.TreeSet;

/**
 * 演示树集的使用
 */
public class TreeSetDemo {
	public static void main(String[] args) {
		TreeSet treeSet = new TreeSet((o1, o2) -> {
//                return (Integer) o1 - (Integer) o2; //Integer型由小到大
			return 1;
		});

		treeSet.add(10);
		treeSet.add(null);  //传入的比较器可以有null
		treeSet.add(13);
		treeSet.add(20);

		System.out.println(treeSet);
	}
}

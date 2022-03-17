package javastudy.collection_.ListTest;

import java.util.AbstractList;

/**
 * 测试用列表
 */
public class CountingIntegerList extends AbstractList<Integer> {
	private int size;

	public CountingIntegerList(int size) {
		this.size = Math.max(size, 0);
	}

	public Integer get(int index) {
		return index;
	}

	public int size() {
		return size;
	}

	public static void main(String[] args) {
		System.out.println(new CountingIntegerList(30));
	}
}
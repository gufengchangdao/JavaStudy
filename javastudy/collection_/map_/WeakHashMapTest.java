package javastudy.collection_.map_;

import java.util.WeakHashMap;

/**
 * 弱散列映射
 */
public class WeakHashMapTest {
	public static void main(String[] args) {
        /*
        TODO WeakHashMap
            WeakHashMap使用弱引用来保存键，简化了WeakReference的使用
            当对键的唯一引用来自散列表映射条目时，WeakHashMap将会与垃圾回收机制协调工作一起删除键值对
         */
		Key[] keys = new Key[10];
		WeakHashMap<Key, Value> map = new WeakHashMap<>();
		for (int i = 0; i < 10; i++) {
			keys[i] = new Key();
			map.put(keys[i], new Value());
			map.put(new Key(), new Value());
		}
		System.gc();
		map.forEach((k, v) -> System.out.println(k)); //调用gc()后只输出奇数，也就是说没有在在keys[]里的键所在键值对都被清理了
	}
}

class Num {
	protected int id;

	@Override
	public String toString() {
		return id + "号" + this.getClass().getSimpleName();
	}
}

class Key extends Num {
	private static int count = 0;

	public Key() {
		this.id = ++count;
	}
}

class Value extends Num {
	private static int count = 0;

	public Value() {
		this.id = ++count;
	}
}

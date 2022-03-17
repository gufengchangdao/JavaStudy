package javastudy.utilities;

/**
 * 简单的用来存放键值对的类
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public class Pair<K, V> {
	public final K key;
	public final V value;

	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Pair{" +
				"key=" + key +
				", value=" + value +
				'}';
	}
}

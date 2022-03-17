```java
import java.util.Hashtable;

@SuppressWarnings({"all"})
public class HashTableDemoExplain {
    /**
     * put添加操作解读:
     * (1) 底层为数组 + 链表，没有红黑树
     * (2) HashTable$Entry[]，初始化为 11
     * (3) 临界值为threshold 8 = 11 * 0.75
     * (4) 扩容: 原容量 * 2 + 1
     * @param key 键
     * @param value 值
     * @return 存在重复元素时返回旧元素，无重复返回null
     */
    public synchronized V put(K key, V value) {
        //确保value不为null
        if (value == null) {
            throw new NullPointerException();
        }

        // 直接就是在索引处遍历链表查看是否有重复元素，有就替换并返回旧元素
        Hashtable.Entry<?, ?> tab[] = table;
        //这里key如果为null也会抛出异常
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;   //由哈希值确定索引
        @SuppressWarnings("unchecked")
        Hashtable.Entry<K, V> entry = (Hashtable.Entry<K, V>) tab[index];
        for (; entry != null; entry = entry.next) {
            if ((entry.hash == hash) && entry.key.equals(key)) {
                V old = entry.value;
                entry.value = value;
                return old;
            }
        }

        //无重复元素，添加新元素
        addEntry(hash, key, value, index);
        return null;
    }

    private void addEntry(int hash, K key, V value, int index) {
        Entry<?, ?> tab[] = table;
        //添加前先判断是否需要扩容
        if (count >= threshold) {
            rehash();   //扩容操作

            //扩容以后，添加元素的索引值也得重新计算了
            tab = table;
            hash = key.hashCode();
            index = (hash & 0x7FFFFFFF) % tab.length;
        }

        // 调用构造器添加新节点，找到链尾并添加也在构造器中
        @SuppressWarnings("unchecked")
        Entry<K, V> e = (Entry<K, V>) tab[index];
        tab[index] = new Entry<>(hash, key, value, e);
        count++;
        modCount++;
    }

    // 再散列操作
    protected void rehash() {
        int oldCapacity = table.length;
        Entry<?, ?>[] oldMap = table;

        // 新容量为: 旧容量 * 2 + 1
        int newCapacity = (oldCapacity << 1) + 1;
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            if (oldCapacity == MAX_ARRAY_SIZE)
                // Keep running with MAX_ARRAY_SIZE buckets
                return;
            newCapacity = MAX_ARRAY_SIZE;
        }
        Entry<?, ?>[] newMap = new Entry<?, ?>[newCapacity];

        modCount++;
        //临界值为 新容量 * 负载因子
        threshold = (int) Math.min(newCapacity * loadFactor, MAX_ARRAY_SIZE + 1);
        table = newMap;

        //数据引用赋给新table
        for (int i = oldCapacity; i-- > 0; ) {
            for (Entry<K, V> old = (Entry<K, V>) oldMap[i]; old != null; ) {
                Entry<K, V> e = old; //e 和 old 为一前一后节点
                old = old.next;

                int index = (e.hash & 0x7FFFFFFF) % newCapacity;    //重新计算索引
                e.next = (Entry<K, V>) newMap[index];
                newMap[index] = e;
            }
        }
    }

}
```
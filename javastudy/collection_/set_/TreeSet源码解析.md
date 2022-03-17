```java
public class TreeMap<K, V> extends AbstractMap<K, V> implements NavigableMap<K, V>, Cloneable, java.io.Serializable {
    // TreeSet的add()会调用TreeMap的put方法，replaceOld是true
    private V put(K key, V value, boolean replaceOld) {
        Entry<K, V> t = root;
        if (t == null) {
            addEntryToEmptyMap(key, value);
            return null;
        }
        int cmp;
        Entry<K, V> parent;
        // split comparator and comparable paths
        Comparator<? super K> cpr = comparator;
        //如果传入了比较器
        if (cpr != null) {
            do {
                parent = t;
                cmp = cpr.compare(key, t.key);  //调用比较器进行比较
                if (cmp < 0)
                    t = t.left;
                else if (cmp > 0)
                    t = t.right;
                else {      //如果比较器返回0，表示这个两个元素相同，不进行添加操作，退出(共用的一个value，还是那个对象)
                    V oldValue = t.value;
                    if (replaceOld || oldValue == null) {
                        t.value = value;
                    }
                    return oldValue;
                }
            } while (t != null);
        }
        //使用默认比较器
        else {
            Objects.requireNonNull(key);    //默认比较器下，key不能为null
            @SuppressWarnings("unchecked")
            Comparable<? super K> k = (Comparable<? super K>) key;    //默认比较器
            do {
                parent = t;
                cmp = k.compareTo(t.key);
                if (cmp < 0)
                    t = t.left;
                else if (cmp > 0)
                    t = t.right;
                else {
                    V oldValue = t.value;
                    if (replaceOld || oldValue == null) {
                        t.value = value;
                    }
                    return oldValue;
                }
            } while (t != null);
        }
        addEntry(key, value, parent, cmp < 0);    //添加节点，传入cmp < 0也是判断放在parent的左子树上还是右子树上
        return null;
    }
}
```
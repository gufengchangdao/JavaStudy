```java
/**
 * LinkedHashSet
 * 是HashSet的子类,LinkedHashSet不允许有重复元素
 * 底层维护了一个数组 + 双向链表
 * LinkedHashSet根据元素的HashCode决定元素的存储位置，同时使用链表维护元素的次序，使得元素遍历时可以以插入顺序进行遍历
 * LinkedHashSet底层维护的是一个LinkedHashMap(是HashMap的子类)
 * LinkedHashSet底层结构是(数组+table+双向链表)
 * 扩容机制和HashMap差不多，HashMap$Node[] table数组存放的结点类型为LinkedHashMap$Entry
 */
class LinkedHashMap {
    //LinkedHashMap$Entry为静态内部类，增添了before、after，根据数据添加的顺序来创建链表
    static class Entry<K, V> extends HashMap.Node<K, V> {
        Entry<K, V> before, after;

        Entry(int hash, K key, V value, Node<K, V> next) {
            super(hash, key, value, next);
        }
    }

    //LinkedHashMap重写了newNode方法，在putVal方法添加节点时调用此方法也会修改上一个数据的after，新添数据的before
    Node<K, V> newNode(int hash, K key, V value, Node<K, V> e) {
        LinkedHashMap.Entry<K, V> p =
                new LinkedHashMap.Entry<>(hash, key, value, e);
        linkNodeLast(p);    //这个方法来更新before、after
        return p;
    }

}
```
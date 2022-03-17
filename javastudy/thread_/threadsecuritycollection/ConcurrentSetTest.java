package javastudy.thread_.threadsecuritycollection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 并发集视图
 */
public class ConcurrentSetTest {
    public static void main(String[] args) {
        // 1. 没有ConcurrentSet这样一个很大的线程安全的集，但是可以使用ConcurrentHashMap来模拟一个
        // 2. 静态newKeySet()会生成一个Set<K>集，实际上是值全为Boolean.TRUE的包装器
        ConcurrentHashMap.KeySetView<String, Boolean> setView = ConcurrentHashMap.<String>newKeySet();
        setView.add("牢狱之灾");
        System.out.println(setView);

        // 3. 如果之前有一个ConcurrentHashMap，还可以通过这个方法获得键视图，键视图删除元素映射也会相应变化
        ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<>();
        hashMap.put("小白", 800);
        ConcurrentHashMap.KeySetView<String, Integer> setView2 = hashMap.keySet();
        // setView2.add("鬼王"); //没有设置默认值添加元素就会抛异常

        // 4.还可以为方法添加一个默认值，当为视图添加元素时，hashMap就会多一个该键值对，值就是默认值
        ConcurrentHashMap.KeySetView<String, Integer> setView3 = hashMap.keySet(99);
        setView3.add("小黑");
        System.out.println(hashMap.get("小黑"));
    }
}

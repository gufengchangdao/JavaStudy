package javastudy.collection_.map_;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LinkedHashMap使用演示
 */
public class LinkedHashMapTest {
    public static void main(String[] args) {
        // TODO LinkedHashMap和LinkedHashSet会记住元素项的插入，因为在向表中插入元素项时会并入到双向链表中
        //  第三个参数为true就是访问顺序，false就是插入顺序，默认false，设为true后没有被访问过的元素就会出现在队列的前面，遍历时先遍历到
        LinkedHashMap<Integer, String> hashMap1 = new LinkedHashMap<>(10, 0.75f, false);

        LinkedHashMap<Integer, String> hashMap = new LinkedHashMap<>() {
            // TODO 在每次调用put()和putAll()时都会调用removeEldestEntry()方法，这是个空方法，用于给子类实现
            //  eldest是最旧最少使用的键值对，如果返回true，就会删除这个键值对，通过重写这个方法可以完成释放内存的工作
            //  这个方法是在添加新元素之后调用的，可以这样使用：return size() > MAX_ENTRIES;
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                System.out.println("最旧最少用的元素是 " + eldest.getKey() + ": " + eldest.getValue());
                return false; //返回true就会删除eldest元素
            }
        };

        hashMap.put(1, "艾希");
        hashMap.put(2, "将军之刃");
        hashMap.put(3, "鬼灭之刃");
        hashMap.put(4, "尼尔机械纪元");

        hashMap.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}

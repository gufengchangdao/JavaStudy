package javastudy.collection_.view;

import java.util.*;

/**
 * 一些小集合的使用
 */
public class SmallCollection {
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        Set<String> set = Set.of("123", "456", "789");
        // 这个of的实现多累，竟然是static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5)
        Map<Integer, String> map = Map.of(1, "王五", 2, "王六", 3, "老王");

        // 创建一个键值对
        Map.Entry<Integer, String> entry = Map.entry(1, "王五");
        // 比较麻烦的创建方法
        AbstractMap.SimpleImmutableEntry<Integer, String> entry1 = new AbstractMap.SimpleImmutableEntry<>(1, "王五");

        // TODO 这些集合对象是不可以修改的，否则会抛UnsupportedOperationException异常，需要修改可以放到构造器里面创建新的集合，这也是小集合的常
        //  用方式
        ArrayList<Integer> arrayList = new ArrayList<>(list);
        System.out.println(list.getClass().getName()); //java.util.ImmutableCollections$ListN，可以看出是这个类的匿名类
    }
}

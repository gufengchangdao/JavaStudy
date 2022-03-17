package javastudy.collection_.set_;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * NavigableSet接口常用方法的使用
 */
public class NavigableSetTest {
    public static void main(String[] args) {
        // TreeSet实现了NavigableSet接口
        TreeSet<Integer> set = new TreeSet<>();
        set.add(2);
        set.add(1);
        set.add(3);
        set.add(4);
        set.add(5);

        // TODO higher()、lower()返回大于value的最小元素或小于value的最大元素，没有就返回null
        System.out.println(set.higher(3)); //4
        System.out.println(set.lower(3)); // 2

        // TODO ceiling()、floor() 用来大于等于或小于等于，没有就返回null
        System.out.println(set.ceiling(3));
        System.out.println(set.floor(3));

        // TODO pollFirst()、pollLast()删除并返回最大或最小值
        System.out.println(set.pollFirst());
        System.out.println(set.pollLast());

        // TODO descendingIterator()获取按照递减顺序遍历集合元素的迭代器
        Iterator<Integer> iterator = set.descendingIterator();
    }
}

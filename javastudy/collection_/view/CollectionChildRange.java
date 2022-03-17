package javastudy.collection_.view;

import java.util.*;

/**
 * 集合的子范围
 */
public class CollectionChildRange {
    public static void main(String[] args) {
        // TODO 可以为很多集合创建子范围视图，可以对子范围进行任何操作，并且操作会反应到整个集合

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        // TODO 可以对list使用元素位置来建立子范围
        List<Integer> childList = list.subList(0, 3); //左开右闭
        childList.clear(); //删除整个子范围，list也会相应变化
        System.out.println(list);

        TreeSet<Integer> set = new TreeSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);

        // TODO 可以对SortedSet和SortedMap的子类使用这些方法，用排序顺序来建立子范围
        SortedSet<Integer> childSet = set.subSet(2, 4); //大于等于2，小于4
        NavigableSet<Integer> childSet1 = set.headSet(3, true); //从头开始并且包括边界
        NavigableSet<Integer> childSet2 = set.tailSet(3, true); //从指定位置到尾巴

    }
}

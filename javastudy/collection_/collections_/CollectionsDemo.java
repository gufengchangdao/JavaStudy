package javastudy.collection_.collections_;

import java.util.*;

/**
 * Collections常用方法演示
 */
public class CollectionsDemo {
    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();

        arrayList.add(4);
        arrayList.add(15);
        arrayList.add(13);
        arrayList.add(20);

        //TODO 反转数组
        //遍历数组交换数据 或者 使用迭代器完成反转
        Collections.reverse(arrayList);

        //TODO 对元素进行随机排序
        //遍历一遍，每个下标元素和小于该下标的随机一个下标数据交换
        Collections.shuffle(arrayList);

        //注意，使用默认构造器时，如果元素的类型不一致就会抛异常，比如add一个double1元素就无法进行比较，抛异常
        // 默认比较器
        Collections.sort(arrayList);
        // 传入比较器
        Collections.sort(arrayList, (o1, o2) -> 0);
        // 传入集合的比较器的逆序进行比较
        Collections.sort(arrayList,Collections.reverseOrder());

        // TODO 交换，需要是有序的list
        Collections.swap(arrayList,0,1);

        //TODO 返回集合中的最大元素
        //遍历，利用list元素的 compareTo 方法不断和当前最大元素进行比较
        Comparable max = Collections.max(arrayList);

        Object max1 = Collections.max(arrayList, (o1, o2) -> 0);

        Comparable min = Collections.min(arrayList);

        //TODO 指定元素出现次数
        System.out.println(Collections.frequency(arrayList,20));

        // TODO 拷贝,目标列表的长度至少与原列表的长度一样
        // void copy(List<? super T> dest, List<? extends T> src) 将src内的元素赋给dest
        //  if (srcSize > dest.size()) throw new IndexOutOfBoundsException("Source does not fit in dest");
        // 注意：dest的元素个数如果小于src的元素个数就好抛出异常
        LinkedList linkedList = new LinkedList();
        for (int i = 0; i < arrayList.size(); i++) {
            linkedList.add(null);
        }
        Collections.copy(linkedList,arrayList);

        //TODO 使用新值替换list对象指定的所有旧值
        Collections.replaceAll(arrayList,20,30);
        Collections.fill(arrayList,30); //直接替换所有值

        // TODO 如果两个集合没有共同元素就返回true
        Collections.disjoint(linkedList,arrayList);


    }
}

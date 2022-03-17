/*
 * ArrayList
 * 底层是由数组来实现的
 * ArrayList基本等同于Vector，除了ArrayList线程不安全(换而之效率高)，在多线程下不建议使用ArrayList
 *  而且Vector的增长速度是2倍，ArrayList的增长速度为1.5倍
 *
 * */
package javastudy.collection_.list_;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.RandomAccess;

/**
 * ArrayList方法演示
 */
public class ArrayListDemo {
    @SuppressWarnings({"all"})
    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();

        arrayList.add(15.2D);   //基本数据类型在存入时先进行装箱，变成包装类
        //在此列表中的指定位置插入指定的元素,指定下标如果有数据，会把该数据和之后的数据往后推移
        arrayList.add(0, "FIRST");
        arrayList.add("REMAIN");
        //boolean addAll(Collection<? extends E> c)
        //boolean addAll(int index, Collection<? extends E> c)

        arrayList.remove(2);
        arrayList.remove("REMAIN");

        //boolean removeAll(Collection<?> c)

        //用指定的元素替换此列表中指定位置的元素
        arrayList.set(0, "SECOND");

        //获取指定下标元素
        System.out.println(arrayList.get(1));//15.2

        //返回此列表中指定元素的第一次出现的索引，如果此列表不包含元素，则返回-1。
        System.out.println(arrayList.indexOf(null));//-1

        System.out.println(arrayList.lastIndexOf(15.2D));//1

        System.out.println(arrayList.size());

        System.out.println(arrayList.isEmpty());

        System.out.println(arrayList.contains(15.2D) + " " + arrayList.contains(15.2F));//true false

        Iterator iterator = arrayList.iterator();

        //以正确的顺序（从第一个到最后一个元素）返回一个包含此列表中所有元素的数组
        Object[] objects = arrayList.toArray();

        //修改这个 ArrayList实例的容量是列表的当前大小
        arrayList.trimToSize();

        //排序，传入比较器
        //调用的是 Arrays.sort
        //返回值 < 0 是一种
        //返回值 >= 0 是另一种
        arrayList.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {  //按照返回值的正负进行排序
                return 0;
            }
        });

        System.out.println(arrayList);

        arrayList.clear();

        // 提前分配指定大小的数组，减少频繁分配空间的开销
        arrayList.ensureCapacity(100);
        // 确定数组列表大小不变后就使用该方法将数组截取空余的部分
        arrayList.trimToSize();

        // TODO list表示有顺序的集合,ArrayList/LinkedList等带list的都表示可以随机访问
        // RandomAccess标记接口不含任何方法，但是实现了这个接口就表示可以随意访问元素
        System.out.println(arrayList instanceof RandomAccess); //true
    }
}

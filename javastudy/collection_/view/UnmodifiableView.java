package javastudy.collection_.view;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 不可变视图的演示
 * 使用了Collections伴生类
 */
public class UnmodifiableView {
    public static void main(String[] args) {
        /*
        TODO 不可修改的视图
         1. Collections类里面有一堆像 UnmodifiableCollection 、 UnmodifiableList 等等静态成员类，修改的操作都会抛
         出UnsupportedOperationException异常，可以调用一些方法生成这些不可修改的视图
         2. 比如
            unmodifiableCollection
            unmodifiableList
            unmodifiableSet
            unmodifiableSortedSet
            unmodifiableNavigableSet
            unmodifiableSortedMap
            unmodifiableNavigableMap
         3. 需要注意的是，unmodifiableCollection的equals()调用的不是底层集合的equals()，而是Object的equals()，所以只能检测两个对象是
         不是同一个对象，hashCode也一样。不过其他类还是会调用集合的方法
         4. 不可更改视图可以为一些方法加上限制，同时在多线程中还可以保证集合是线程安全的
         */
        LinkedList<String> list = new LinkedList<>();
        list.add("你好");
        list.add("我饿了");
        list.add("想杀人");

        //返回一个不可变的List对象，已经不是LinkedList类型了，而是unmodifiableList的一个匿名类
        List<String> list2 = Collections.unmodifiableList(list);
        for (String s : list2) { //适合把集合给其他方法时加一些不可更改权限
            System.out.println(s);
        }
    }
}

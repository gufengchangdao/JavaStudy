package javastudy.collection_.iterator_;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * ListIterator迭代器的测试
 * listIterator()比Iterator更高级，可以操作集合元素，倒序遍历，获取元素索引值
 * 迭代器图解： |1234 --> 1|234 --> 12|34 --> 123|4 --> 1234|
 */
public class ListIteratorTest {
    public static void main(String[] args) {
        // ArrayList和LinkedList都实现了ListIterator接口(其实是AbstractLinked实现的)
        ArrayList<String> arrayList = new ArrayList<>();
        ListIterator<String> listIterator = arrayList.listIterator();
        LinkedList<String> list = new LinkedList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        ListIterator<String> iterator = list.listIterator();

        // TODO listIterator可以泛型遍历链表，但是得先移动指针再说
        while (iterator.hasNext())
            iterator.next();

        System.out.println("倒叙遍历：");
        while (iterator.hasPrevious())
            System.out.print(iterator.previous());

        // TODO add()在迭代器位置之前添加一个新对象
        iterator.add("0"); //这里是在最前面添加一个元素0作为链首

        iterator.previous();
        System.out.println("\n添加元素后正序遍历：");
        while (iterator.hasNext())
            System.out.print(iterator.next());

        // TODO remove()用于删除遍历的上一个元素，使用next()就是删除左侧的元素，使用previous()就是删除右侧的元素
        iterator.remove(); //这里是删除掉最后一个元素

        System.out.println("\n使用remove()删除掉最后一个元素后：");
        while (iterator.hasPrevious())
            System.out.print(iterator.previous());

        // TODO 并发问题：如果当前迭代器正在遍历集合，集合却被其他方法或迭代器修改了，那这个迭代器就不用再使用了，会产生混乱的
        //  1. 链表迭代器设计为可以检测到这种修改，如果一个迭代器发现它的集合被修改了(被迭代器或自身集合的某个方法)，就会抛出一个
        //  ConcurrentModificationException异常
        //  2. 简单的检测修改的方法：集合维护一个数据记录操作次数(modCount)，迭代器在创建时记录当时的操作数(expectedModCount),并且每个迭
        //  代器方法的开始处都会检查自己的操作数是否和集合的操作数一致(checkForComodification())，不一致就抛出并发修改异常。
        //  3. 按照那个方法，调用add()和remove()方法都会使得modCount和expectedModCount加一
        //  4. set()方法可以替换遍历的上一个元素，并且不被视为结构性修改(就是说expectedModCount不会加一)。因此可以使用多个迭代器的同时还能
        //  修改链表内容
        // list.add("5"); //modCount已经加1，iterator中除了has...()，其他方法已经不能再用了，set也不行

        ListIterator<String> iterator1 = list.listIterator();
        iterator.set("我"); //修改链首元素
        System.out.println("\n修改集合后，迭代器正常运行：");
        while (iterator1.hasNext())
            System.out.print(iterator1.next());

        // TODO nextIndex()、previousIndex()可以获取元素所在的索引，两个方法的值只是相差一
        ListIterator<String> iterator2 = list.listIterator(1); //设置的指针的初始化位置，相当于已经调用了一个next一样

        System.out.println("\n正序遍历元素索引值：");
        while (iterator.hasNext()) {
            iterator.next();
            // 虽说是元素索引值，但是更像是指针的索引值，在next前调用就是从0开始，在next后面调用就是从1开始
            System.out.println(iterator.nextIndex());
        }
    }
}

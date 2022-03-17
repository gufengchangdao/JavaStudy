package javastudy.collection_.set_;

import java.util.LinkedHashSet;

/**
 * LinkedHashSet类的测试
 */
public class LinkedHashSetDemo {
    public static void main(String[] args) {
        /*
        LinkedHashSet
            是HashSet的子类,LinkedHashSet不允许有重复元素
            底层维护了一个数组 + 双向链表
            LinkedHashSet根据元素的HashCode决定元素的存储位置，同时使用链表维护元素的次序，使得元素遍历时可以以插入顺序进行遍历
            LinkedHashSet底层维护的是一个LinkedHashMap(是HashMap的子类)
            LinkedHashSet底层结构是(数组+table+双向链表)
            扩容机制和HashMap差不多，HashMap$Node[] table数组存放的结点类型为LinkedHashMap$Entry
        */
        LinkedHashSet linkedHashSet = new LinkedHashSet();

        linkedHashSet.add("天才");
        linkedHashSet.add("那肯定是");
        linkedHashSet.add("我啦");

        for (Object x : linkedHashSet) {
            System.out.print(x);
        }

    }
}

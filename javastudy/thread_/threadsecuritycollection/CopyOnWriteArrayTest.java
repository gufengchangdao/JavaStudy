package javastudy.thread_.threadsecuritycollection;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * CopyOnWriteArrayList和CopyOnWriteArraySet集合的演示
 */
public class CopyOnWriteArrayTest {
    public static void main(String[] args) {
        // 这两个集合所有更改器会建立底层数组的一个副本，当构造一个迭代器时，如果这个数组后来被修改了，迭代器仍然使用旧数组，但是集合的数组已
        // 经替换。
        // TODO 迭代器使用旧的数据，不存在同步开销
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<>();

    }
}

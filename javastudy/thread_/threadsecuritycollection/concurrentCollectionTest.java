package javastudy.thread_.threadsecuritycollection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 高效的映射、集和队列演示
 */
public class concurrentCollectionTest {
    public static void main(String[] args) {
        /* TODO java.util.concurrent包下的类
            1. 这些集合使用了复杂的算法，通过允许并发地访问数据结构的不同部分尽可能的减少竞争
            2. 迭代器不一定能反映出他们构造之后的所有更改，但是他们不会将同一个值返回两次，也不会抛出并发修改异常
         */
        // 可以被多线程访问，无上限非阻塞队列
        ConcurrentLinkedQueue<Integer> linkedQueue = new ConcurrentLinkedQueue<>();
        // 可以被多线程访问的有序集
        ConcurrentSkipListSet<Object> skipListSet = new ConcurrentSkipListSet<>();
        // 可以被多线程访问的散列映射表
        ConcurrentHashMap<Integer, Integer> hashMap = new ConcurrentHashMap<>();
        // 可以被多线程访问的有序映射
        ConcurrentSkipListMap<Integer, Integer> skipListMap = new ConcurrentSkipListMap<>();

        // TODO: 2022/1/15 ConcurrentHashMap中执行原子操作的方法
        // compute()，可以传入一个一个函数，如果没有k，则k为null
        hashMap.compute(1, (k, v) -> v == null ? 1 : v + 1); //自增操作
        // 如果没有指定键时的操作
        hashMap.putIfAbsent(2, 3);
        hashMap.computeIfAbsent(2, v -> 3);
        // 如果有指定键时的操作
        hashMap.computeIfPresent(2, (k, v) -> ++v);
        // hashMap.computeIfPresent(2,(k,v)->v++);//TODO 注意，这里 ++v 和 v++ 的结果是不一样的， v++ 是无效的
        // 如果有指定键，就调用函数更新值(两个参数分别原值和第二个参数)，如果没有指定键值对就设置这一对键值对
        hashMap.merge(2, 3, Integer::sum);

        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }

    }
}

package javastudy.collection_.queue;

import java.util.*;
import java.time.*;

/**
 * 演示优先队列的使用
 *
 * @author Cay Horstmann
 * @version 1.02 2015-06-20
 */
public class PriorityQueueTest {
    public static void main(String[] args) {
        /* TODO PriorityQueue 使用堆实现
            典型用法是任务调度，每次都将优先级最高的任务从队列中删除
        */
        var pq = new PriorityQueue<LocalDate>();
        pq.add(LocalDate.of(1906, 12, 9));
        pq.add(LocalDate.of(1815, 12, 10));
        pq.add(LocalDate.of(1903, 12, 3));
        pq.add(LocalDate.of(1910, 6, 22));

        // TODO 与TreeSet的迭代不同，优先的队列的迭代不是有序的，不过删除操作是有序的
        System.out.println("元素迭代 . . .");
        for (LocalDate date : pq)
            System.out.println(date);
        System.out.println("元素移除 . . .");
        while (!pq.isEmpty())
            System.out.println(pq.remove());
    }
}

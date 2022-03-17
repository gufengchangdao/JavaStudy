package javastudy.thread_.task;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.DoublePredicate;

/**
 * 演示 fork-join 框架
 * 统计数组中满足条件的元素个数
 *
 * @author Cay Horstmann
 * @version 1.01 2015-06-21
 */
public class ForkJoinTest {
    /* TODO fork-join
        1. fork-join是基于"分治"的算法： 分解任务 + 合并结果
        2. 任务类必须继承自RecursiveTask/RecursiveAction类，前者有一个返回值，后者无返回值，就跟Callable和Runnable一样
        3. fork-join框架使用了一种有效的智能方法来平衡可用线程的工作负载，这种方法称为工作密取，提高效率
        4. 使用fork-join可以进行并行计算提高效率，比如 Arrays.parallelSort() 就使用了fork-join，并行计算来排序
     */
    public static void main(String[] args) {
        final int SIZE = 10000000;
        var numbers = new double[SIZE];
        for (int i = 0; i < SIZE; i++) numbers[i] = Math.random();
        var counter = new Counter(numbers, 0, numbers.length, x -> x > 0.5);
        // 创建fork-join线程池对象
        var pool = new ForkJoinPool();
        // 执行任务，线程在此阻塞
        pool.invoke(counter);
        // joi()获取结果，get()也可以获取结果，但是get()可能会抛异常，比较少用
        System.out.println(counter.join());
    }
}

class Counter extends RecursiveTask<Integer> {
    // 阈值
    public static final int THRESHOLD = 1000;
    private double[] values;
    private int from;
    private int to;
    // 过滤用的条件
    private DoublePredicate filter;

    public Counter(double[] values, int from, int to, DoublePredicate filter) {
        this.values = values;
        this.from = from;
        this.to = to;
        this.filter = filter;
    }

    protected Integer compute() {
        if (to - from < THRESHOLD) { // 分治的最小单位
            int count = 0;
            for (int i = from; i < to; i++) {
                if (filter.test(values[i])) count++;
            }
            return count;
        } else {
            int mid = (from + to) / 2;
            // TODO: 2022/1/16 重点：并行处理小任务
            var first = new Counter(values, from, mid, filter);
            var second = new Counter(values, mid, to, filter);
            // 分叉给定的任务，当每个任务的完成(isDone()为true)或遇到（未经检查的）异常时返回，在这种情况下会重新抛出异常。
            invokeAll(first, second); //计算完成前线程阻塞在这里
            return first.join() + second.join(); //返回计算结果
        }
    }
}

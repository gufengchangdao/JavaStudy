package javastudy.thread_.threadsecuritycollection.blockingqueue;

import java.util.concurrent.*;

/**
 * 演示阻塞队列的使用
 */
public class BlockingQueueTest {
    /* TODO BlockingQueue
        1. 使用阻塞队列，可以安全的从一个线程向另一个线程传递数据，可以用阻塞队列代替显示的线程同步
        2. 有以下常用方法：
            add()/element()/remove() 队列异常(为空或满)就会抛异常，在多线程中不用这个
            offer()/peek()/poll() 队列异常返回null或false，不阻塞，但是可以设置超时时间
            put()/take() 队列异常会阻塞

     */
    // TODO BlockingQueue是一个继承Queue的接口，以下是它的几个实现类
    // ArrayBlockingQueue在构建时需要指定容量
    private static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(5);
    // 设置里公平参数，那么等待最长时间的线程会优先得到处理，但是公平性会降低性能
    private static BlockingQueue<Integer> blockingQueue2 = new ArrayBlockingQueue<>(5, true);
    // LinkedBlockingQueue默认容量没有上限，也可以指定最大容量
    private static BlockingQueue<Integer> blockingQueue3 = new LinkedBlockingQueue<>();
    // LinkedBlockingDeque是一个双端队列
    private static BlockingQueue<Integer> blockingQueue4 = new LinkedBlockingDeque<>();
    // PriorityBlockingQueue是一个优先队列
    private static BlockingQueue<Integer> blockingQueue5 = new PriorityBlockingQueue<>();
    // DelayQueue实现了Delayed接口，该接口又实现了Comparable接口，如果创建该对象就需要实现两个方法，compareTo()和getDelay()，前者用于对
    // 元素进行排序，后者用于获取元素时根据对象的剩余延迟时间是否允许元素出列
    // private static BlockingQueue<Integer> blockingQueue6 = new DelayQueue<>();


    public static void main(String[] args) throws Exception {
        // 队列满/为空的时候线程会阻塞在这里
        blockingQueue.put(1);
        // blockingQueue.take();

        // 不会进行阻塞，直接返回false或null
        boolean result1 = blockingQueue.offer(2);
        // Integer result2 = blockingQueue.peek();
        // Integer result3 = blockingQueue.poll();

        // 可以指定超时时间，就是说程序会阻塞一会儿，第三个参数是表示第二个参数的计量单位，毫秒、天、小时等等
        boolean result4 = blockingQueue.offer(3, 100, TimeUnit.MILLISECONDS); //尝试100毫秒内插入

    }
}

package javastudy.thread_.threadsecuritycollection.blockingqueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列演示
 */
public class DelayQueueTest {
    private static DelayQueue<MyDelayed> delayQueue = new DelayQueue<>();

    public static void main(String[] args) throws InterruptedException {
        delayQueue.add(new MyDelayed(1, 1000, TimeUnit.MILLISECONDS));
        delayQueue.add(new MyDelayed(2, 3000, TimeUnit.MILLISECONDS));
        delayQueue.add(new MyDelayed(3, 2000, TimeUnit.MILLISECONDS));
        for (int i = 0; i < 3; i++) {
            System.out.println(delayQueue.take().value);
            // 在take()、poll()等移除队首元素的方法内部会调用getDelay()来判定该元素是否可以出队
            // 运行结果是1s后输出1，3s后输出2和3，因为出队列顺序是123，而2要等3秒才会过期，getDelay()才会小于等于0，等输出2的时候，3早就可以输出了
        }
    }
}

/**
 * DelayQueue的泛型类型必须实现Delayed接口，因为Delayed接口继承了Comparable接口，实现该接口就需要实现两个方法
 * compareTo() --> 决定元素出队的顺序
 * getDelay() --> 决定队首元素是否可以出队
 */
class MyDelayed implements Delayed {
    public int value;  //值
    private long time; //触发时间

    /**
     * 传入该元素的值和可以出队列的时间和时间单位
     *
     * @param value 元素值
     * @param time  满足出队列的剩余延迟时间
     * @param unit  时间单位
     */
    public MyDelayed(int value, long time, TimeUnit unit) {
        this.value = value;
        this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis(); //小于等于0就表示可以出队列了
    }

    @Override
    public int compareTo(Delayed o) {
        if (o instanceof MyDelayed)
            return this.value - ((MyDelayed) o).value;
        else
            throw new ClassCastException();
    }
}

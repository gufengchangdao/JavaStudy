package javastudy.thread_.synchronize;

/**
 * 使用synchronized关键字代替ReentrantLock
 * Object类的wait()代替await()方法
 * notifyAll()代替signalAll()方法
 */
public class ConditionTest02 {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new SellTicket4(-1, 101));
        Thread thread2 = new Thread(new SellTicket4(-1, 9));
        Thread thread3 = new Thread(new SellTicket4(1, 10));

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class SellTicket4 implements Runnable {
    private static int tickets = 100;    //总票数

    private int step; //对票的操作，为1就是补充票，为-1就是卖票
    private int already = 0; //已完成的票数
    private int target; //目标

    public SellTicket4(int step, int target) {
        this.step = step;
        this.target = target;
    }

    public void run() {
        while (true) {
            try {
                // TODO 同步代码块
                synchronized (this) {
                    // 达成目标就结束线程
                    if (already == target) {
                        System.out.println("----------- " + Thread.currentThread().getName() + "一共" + (step > 0 ? "补充" : "卖")
                                + "了 " + target + " 张票 -----------");
                        return;
                    }
                    // 卖票操作
                    if (step < 0) {
                        if (tickets == 0) { //没票了
                            System.out.println("票卖完了，" + Thread.currentThread().getName() + "还缺" + (target - already) + "张票，等待补充...");
                            while (tickets == 0) //没票后，线程被阻塞
                                wait();
                            // TODO wait()是Object的方法，让一个线程进入等待状态，该方法只能在同步方法和同步代码块中调用(在调用该方法的对象
                            //  为锁的代码块中)，否则会抛异常
                        }
                        System.out.println(Thread.currentThread().getName() + "卖了一张票，还剩 " + (--tickets));
                    }
                    // 补充票操作
                    else {
                        System.out.println(Thread.currentThread().getName() + "补充了一张票，还剩 " + (++tickets));
                        if (tickets >= 10) //只有票数>=10了其他线程才能继续卖票
                            notifyAll();
                        // TODO  notifyAll()是Object的方法，也只能在同步方法和同步代码块中调用，还有一个方法叫notify
                    }
                    already++; // 运行到这里说明没有被阻塞，就是成功卖或者补充了一票
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
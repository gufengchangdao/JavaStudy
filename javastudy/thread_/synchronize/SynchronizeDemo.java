/*
synchronized
    在多线程编程，一些敏感数据不允许被多个线程同时访问，此时就使用同步访问技术，保证数据在任何同一时刻最多有一个线程访问，以保证数据的完整性
        即同一时刻只有一个线程可以对指定的内存地址进行操作

    具体方法：
        (1) 同步代码块
            synchronized(对象){}
        (2) synchronized放在方法声明中，表示整个方法


 */
package javastudy.thread_.synchronize;

/**
 * 多线程模拟售票系统
 *  问题：多线程共享同一资源时(票)，由于判断和卖票无法同步(不是同时发生)，会产生超卖情况
 *  解决：将判断和卖票的代码块同步
 */
public class SynchronizeDemo {
    public static void main(String[] args) {
        SellTicket sellTicket = new SellTicket();
        Thread thread1 = new Thread(sellTicket);
        Thread thread2 = new Thread(sellTicket);
        Thread thread3 = new Thread(sellTicket);

        //3个窗口在卖票
        thread1.start();
        thread2.start();
        thread3.start();

    }
}

class SellTicket implements Runnable{
    private int tickets=100;    //总票数
    private boolean loop=true;
    @Override
    public void run() {
        while (loop) {
            //卖票和判断同步
            synchronized (this) {
                System.out.println(Thread.currentThread().getName()+"卖了一张票，还剩 "+(--tickets));
                if (tickets == 0) {
                    System.out.println("**********票卖完了**********");
                    loop = false;
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}


package javastudy.thread_.thread.interrupt;

import org.junit.Test;

/**
 * 线程的中断
 */
public class InterruptMethod {
    /*
    1. 无法中断试图获取synchronized锁或者试图执行I/O操作的线程，但是ReentrantLock可以使用可以被中断的获取锁的方法lockInterruptibly()，并
       且nio包下流的读取操作也可以因中断和资源关闭而抛异常
    2. 中断是一个boolean标记，在休眠前中断，进入休眠也会抛异常的
     */
    /**
     * 关于Thread的三个interrupt方法演示
     */
    @Test
    public void test01(){
        Runnable runnable = ()->{
            System.out.println("好久");
            Thread thread = Thread.currentThread();
            // TODO 向线程发送中断请求，如果线程正处在sleep调用阻塞时会抛出一个InterruptedException异常。
            //  注意，中断不是终止，是一个标记，是一个提醒，程序会继续运行，并且程序员可以检测是否终端
            thread.interrupt();

            // TODO 静态方法，测试当前线程是否被中断，并且如果正在中断还会将线程中断状态该为false
            System.out.println(Thread.interrupted()); //true

            // TODO 测试线程是否被中断
            System.out.println(thread.isInterrupted()); //false ，因为静态方法取消了中断

            System.out.println("不见");
        };
        new Thread(runnable).start();
    }

    /**
     * 不建议抑制InterruptedException异常(就是啥也不做)，介绍两种处理方法
     */
    @Test
    // TODO 1. 将异常抛出去
    public void test02() throws InterruptedException{
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO 2. 将当前线程设置为中断状态，这样程序员还可以检测中断状态
            Thread.currentThread().interrupt();
        }
    }

}

package javastudy.thread_.task;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * 演示具有返回值的线程类
 */
public class FutureTaskTest {
    /* 任务
        1. Callable和Runnable类似，但是Callable方法提供的call()方法可以抛出一个Exception对象，并且该方法会返回一个泛型类型的值
        2. Future接口用来保存异步计算的结果，可以启动一个计算，将Future对象交给某个线程，这个Future对象的所有者在结果计算好以后就可以使用
        get()来获取结果
            boolean cancel(boolean mayInterruptIfRunning); 尝试取消任务的运行，传入true就会中断这个任务，中断成功就返回true
            boolean isCancelled();                         如果任务完成之前被取消就返回true
            boolean isDone();                              如果任务结束，无论是正常完成，中途取消还是发生异常都返回true
            V get();                                       获取结果，如果还没计算好，就会阻塞
            V get(long timeout, TimeUnit unit);            如果在指定时间内获取不到结果就会抛TimeoutException异常
        3. RunnableFuture接口实现了前两个接口，而FutureTask又实现了RunnableFuture接口，可以使用FutureTask类来执行任务
        4. 可以使用线程池的submit()获取一个Future对象，获取直接使用FutureTask
     */
    @Test
    public void callableTest() throws ExecutionException, InterruptedException, TimeoutException {
        // TODO: 2022/1/16 传入Callable对象
        // 实现call()方法
        Callable<Integer> task = () -> {
            Thread.sleep(2000);
            return 0;
        };

        // 构造FutureTask对象，并将Callable对象传给它
        FutureTask<Integer> futureTask = new FutureTask<>(task);
        Thread thread = new Thread(futureTask);

        // 将会启动一个线程间接调用call()方法
        thread.start();

        System.out.println("开始");
        // Thread.sleep(3000);
        System.out.println("结束");
        //如果使用get(),在结果出来之前都会阻塞，注意如果没有调用start()就直接使用get()，线程会一直阻塞在这里
        // System.out.println(futureTask.get());
        // 可以捕获这个TimeoutException异常
        System.out.println(futureTask.get(1000, TimeUnit.MILLISECONDS));
    }

    @Test
    public void RunnableTest() throws InterruptedException {
        // TODO: 2022/1/16 传入Runnable对象
        // 可以把Runnable实现类传入FutureTask，需要提供一个默认值，get()会获取默认值
        // 看起来虽然奇怪，但是调用cancel、isCancelled、isDone这些方法来判断或改变线程状态
        Runnable runnable = () -> {
            // if (Thread.currentThread().isInterrupted())
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.err.println("线程被中断");
            }
            System.out.println("线程结束");
        };
        FutureTask<Integer> futureTask = new FutureTask<>(runnable, null); //不需要值的时候最好设为null
        new Thread(futureTask).start();
        Thread.sleep(1000);
        // cancel()会给线程添加中断标记，可以在子线程检测
        futureTask.cancel(true);
    }

}

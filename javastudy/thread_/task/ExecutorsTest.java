package javastudy.thread_.task;

import java.util.concurrent.*;

/**
 * 使用执行器类构造线程池
 */
public class ExecutorsTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 为了得到最优的运行速度，并发线程数最好等于处理器内核数，可以作为固定线程池的线程数
        int num = Runtime.getRuntime().availableProcessors() - 1; //减1是去除主线程

        // TODO 构造线程池可以使用Executors的静态工厂方法
        // TODO ExecutorService对象 立刻执行任务的线程池
        // 1. 必要时创建新线程，空闲线程会保留60秒。如果有空闲线程可用就使用空闲线程执行任务，否则就创建一个新线程
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 2. 池中包含固定数目的线程，空闲线程会一直保留。如果提交的任务多于空闲线程数就把未得到服务的线程放到队列中。
        ExecutorService executorService1 = Executors.newFixedThreadPool(num);
        // 3. 一种适合"fork-join"任务的线程池。其中复杂的任务会分解为更简单的任务，空闲线程会"密取"较简单的任务
        ExecutorService executorService2 = Executors.newWorkStealingPool();
        // 4. 只有一个线程的线程池。会顺序的执行所提交的任务。对于性能分析很有帮助
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();
        // 5. Executors可配合ThreadFactory实现类使用，便捷的创建需要的线程对象，传入Runnable时就会自动调用这个
        ExecutorService executorService4 = Executors.newCachedThreadPool((Runnable runnable) -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            // thread.setUncaughtExceptionHandler(); //设置未捕获异常处理器
            return thread;
        });
        // TODO submit()可以将Runnable或Callable对象交给ExecutorService对象进行处理。
        //分派任务，返回一个Future对象，用来得到结果或者取消结果
        Callable callable = () -> "你是猪";
        Runnable runnable = () -> System.out.println("我不是");
        Future future = executorService.submit(callable);
        System.out.println(future.get()); //你是猪
        // 可以传入Runnable对象看起来有点奇怪，使用future对象的get()会获取到null。但是可以调用isDone、cancel、isCancelled方法
        Future<?> future1 = executorService.submit(() -> System.out.println("我不是")); //默认值是null
        Future<Boolean> future2 = executorService.submit(runnable, Boolean.TRUE);//默认值是TRUE

        executorService.execute(runnable); //也是执行任务，只能传入Runnable

        // TODO 关闭线程
        // 关闭服务，已提交任务的线程不再接收新的任务，不会中断正在运行的任务
        executorService.shutdown();
        // 关闭服务，取消所有尚未开启的任务，正在运行的任务会抛中断异常
        executorService.shutdownNow();


        // TODO ScheduledExecutorService 调度执行任务的线程池
        // 5. 用于调度指定的固定线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(num);
        // 6. 用于调度执行的单线程池
        ScheduledExecutorService scheduledExecutorService1 = Executors.newSingleThreadScheduledExecutor();

        // TODO schedule()在指定的时间后执行任务
        ScheduledFuture future3 = scheduledExecutorService.schedule(callable, 2000, TimeUnit.MILLISECONDS);
        // scheduleAtFixedRate()在初始延迟后周期性地运行给定给定任务，下面就是在2秒后开始执行，每个1s执行一次
        ScheduledFuture<?> scheduledFuture =
                scheduledExecutorService.scheduleAtFixedRate(runnable, 2000, 1000, TimeUnit.MILLISECONDS);
        // 这个方法指定的是上一次调用完成后到下一次开始中间的间隔时间，这里是1秒
        scheduledExecutorService.scheduleWithFixedDelay(runnable, 2000, 1000, TimeUnit.MILLISECONDS);

        scheduledExecutorService.shutdown();
        scheduledExecutorService.shutdownNow();
    }
}

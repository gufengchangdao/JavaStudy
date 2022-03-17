package javastudy.thread_.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 演示执行器服务对任务组的控制
 */
public class ExecutorsTest02 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 先创建一个执行器对象
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 安排一组任务，每个任务都是延迟不同的时间再打印内容
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i; //需要是事实最终变量
            tasks.add(() -> {
                Thread.sleep(finalI * 700);
                return "我是御坂" + finalI + "号";
            });
        }

        // TODO: 2022/1/16 方式一、调用执行器服务的invokeAll()方法
        //  传入一个任务集合，这个方法会阻塞，直到所有任务都完成，并返回所有任务答案的一个Future对象列表
        // List<Future<String>> futures = executorService.invokeAll(tasks);
        // 等待完成时间最长的那个任务完成后才会执行下面的打印
        // for (Future<String> future : futures) {
        //     System.out.println(future.get());
        // }

        // TODO: 2022/1/16 方式二、调用invokeAny()方法
        //   1. invokeAny()执行给定任务，返回已成功完成的任务的结果（即不抛出异常）
        //   2. 对于要跳过的任务不是返回null而是要抛一个异常，比如 throw new NoSuchElementException();
        //   3. 此外还应该在线程中检验是否被中断来结束线程 if (Thread.currentThread().isInterrupted()) ，抛异常会中断其他线程，但不会
        //   结束线程，需要手动结束
        // String str = executorService.invokeAny(tasks);
        // System.out.println(str);

        // TODO: 2022/1/16 方式二、利用ExecutorCompletionService来管理
        //  该服务会管理Future对象的一个阻塞队列，其中包含所提交任务的结果(一旦结果可用就会放入队列)，使用该服务就不用等所有任务完成才能获取结果
        ExecutorCompletionService<String> service = new ExecutorCompletionService<>(executorService);
        for (Callable<String> task : tasks) //执行每一个任务
            service.submit(task);

        for (int i = 0; i < tasks.size(); i++) {
            // if (i==1) { //甚至可以实现获取第一个完成的结果
            //     executorService.shutdownNow(); //中断所有正在执行的任务，关闭线程
            //     break;
            // }
            System.out.println(service.take().get()); //take()会检索并删除代表下一个已完成任务的 Future，如果还没有，则等待
        }

        executorService.shutdown();
    }
}

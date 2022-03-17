package javastudy.thread_.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 演示Callable接口和Executor类的使用
 *
 * @author Cay Horstmann
 * @version 1.0 2018-01-04
 */
public class ExecutorDemo {
    /**
     * Counts occurrences of a given word in a file.
     *
     * @return the number of times the word occurs in the given word
     */
    public static long occurrences(String word, Path path) {
        try (var in = new Scanner(path)) {
            int count = 0;
            while (in.hasNext())
                if (in.next().equals(word)) count++;
            return count;
        } catch (IOException ex) {
            return 0;
        }
    }

    /**
     * 返回给定目录的所有后代
     *
     * @param rootDir 根目录
     * @return 根目录的所有后代组成的集
     */
    public static Set<Path> descendants(Path rootDir) throws IOException {
        try (Stream<Path> entries = Files.walk(rootDir)) {
            return entries.filter(Files::isRegularFile).collect(Collectors.toSet());
        }
    }

    /**
     * 生成一个在文件中查找含有关键词文件的任务
     *
     * @param word 查找的关键词
     * @param path 查找路径
     * @return 在指定路径查找含关键词文件的任务
     */
    public static Callable<Path> searchForTask(String word, Path path) {
        return () -> {
            try (var in = new Scanner(path)) {
                while (in.hasNext()) {
                    if (in.next().equals(word)) return path;
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("搜索在 " + path + " 路径被取消");
                        return null; //需要手动结束线程
                    }
                }
                throw new NoSuchElementException(); // 注意，找不到不是返回null而是抛一个异常
            }
        };
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        try (var in = new Scanner(System.in)) {
            System.out.print("Enter base directory (e.g. /opt/jdk-9-src): ");
            String start = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile): ");
            String word = in.nextLine();

            // 创建任务集合
            Set<Path> files = descendants(Path.of(start));
            var tasks = new ArrayList<Callable<Long>>();
            for (Path file : files) {
                Callable<Long> task = () -> occurrences(word, file); //每个任务是文件中关键字个数
                tasks.add(task);
            }
            ExecutorService executor = Executors.newCachedThreadPool();
            // 使用单线程执行器来查看多个线程是否加快了搜索速度
            // ExecutorService executor = Executors.newSingleThreadExecutor();

            // 执行任务并统计花费时间
            Instant startTime = Instant.now(); // 开始时间戳
            List<Future<Long>> results = executor.invokeAll(tasks);
            long total = 0;
            for (Future<Long> result : results)
                total += result.get();
            Instant endTime = Instant.now(); // 截止时间戳
            System.out.println("关键词 " + word + " 出现了: " + total);
            System.out.println("花费时间 : " + Duration.between(startTime, endTime).toMillis() + " 毫秒");

            var searchTasks = new ArrayList<Callable<Path>>();
            for (Path file : files)
                searchTasks.add(searchForTask(word, file));
            // TODO: 2022/1/16 invokeAny()执行给定任务，返回已成功完成的任务的结果（即不抛出异常）并取消未完成的任务
            Path found = executor.invokeAny(searchTasks);
            System.out.println(word + " 出现在: " + found);

            if (executor instanceof ThreadPoolExecutor) // the single thread executor isn't
                System.out.println("开启的最大线程数量为: " + ((ThreadPoolExecutor) executor).getLargestPoolSize());
            executor.shutdown();
        }
    }
}

package javastudy.thread_.threadsecuritycollection.blockingqueue;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/**
 * 搜索指定目录及其所有子目录下所有文件，打印出包含指定关键字的行
 *
 * @author Cay Horstmann
 * @version 1.03 2018-03-17
 */
public class BlockingQueueTest2 {
    private static final int FILE_QUEUE_SIZE = 10; // 阻塞队列的容量
    private static final int SEARCH_THREADS = 100; // 用来搜素关键字的线程数
    // TODO: 2022/1/15 DUMMY 是表示队列为空的一个信号，将这个许你对象放在队尾，当它出队就表示队列为空，把包放回队列并终止。多线程中很有用
    private static final Path DUMMY = Path.of("");
    private static BlockingQueue<Path> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main(String[] args) {
        try (var in = new Scanner(System.in)) {
            System.out.print("Enter base directory (e.g. /opt/jdk-11-src): ");
            String directory = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile): ");
            String keyword = in.nextLine();

            // TODO: 2022/1/15 生产者线程插入元素
            //  开启一个线程，用来把该目录及子目录下的所有文件路径对象放入阻塞队列
            Runnable enumerator = () -> {
                try {
                    enumerate(Path.of(directory));
                    queue.put(DUMMY); //一个虚拟对象，表示阻塞队列为空了
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException ignored) {
                }
            };
            new Thread(enumerator).start();

            // TODO: 2022/1/15 消费者线程获取元素
            //  开启了100个子线程用来搜素关键字
            for (int i = 1; i <= SEARCH_THREADS; i++) {
                Runnable searcher = () -> {
                    try {
                        var done = false;
                        while (!done) {
                            Path file = queue.take();
                            if (file == DUMMY) { //表示用于搜索文件的子线程已经结束了
                                queue.put(file); // 放回去很重要，因为其他子线程不知道是不是所有文件都搜索完了
                                done = true;
                            } else
                                search(file, keyword);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                    }
                };
                new Thread(searcher).start();
            }
        }
    }

    /**
     * 递归枚举给定目录及其子目录中的所有文件
     *
     * @param directory 开始枚举的目录
     */
    public static void enumerate(Path directory) throws IOException, InterruptedException {
        try (Stream<Path> children = Files.list(directory)) {
            for (Path child : children.collect(Collectors.toList())) {
                if (Files.isDirectory(child))
                    enumerate(child);
                else
                    queue.put(child);
            }
        }
    }

    /**
     * 在文件中搜索给定的关键字并打印所有匹配的行。
     *
     * @param file    要搜索的文件
     * @param keyword 要搜索的关键字
     */
    public static void search(Path file, String keyword) throws IOException {
        try (var in = new Scanner(file, StandardCharsets.UTF_8)) {
            int lineNumber = 0;
            while (in.hasNextLine()) {
                lineNumber++;
                String line = in.nextLine(); //读取一行内容
                if (line.contains(keyword)) //发现关键字
                    System.out.printf("%s:%d:\n%s%n", file, lineNumber, line);
            }
        }
    }
}

package javastudy.swing_.process;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 演示ProcessBuilder的使用
 */
public class ProcessTest {
    /**
     * ProcessBuilder配置进程信息和环境
     */
    @Test
    public void test01() throws IOException {
        // Process类在一个单独的操作系统进程中执行一个命令，允许我们与标准输入、输出和错误流交互。ProcessBuilder类则允许我们配置Process类
        ProcessBuilder builder = new ProcessBuilder("gcc", "myapp.c");//其实没有这个文件
        // 建立一个进程
        Process p = builder.start();
        // 每个进程都有一个工作目录，用来解析相对目录名。默认下进程的工作目录与虚拟机相同，通常是启动java程序的那个目录，使用directory改变工作目录
        builder = builder.directory(new File("src"));

        // 指定如何处理进程的标准输入、输出和错误流
        //我们写入的内容会成为进程的输入，读取的内容是进程的输出流和错误流的内容
        InputStream inputStream = p.getInputStream();
        OutputStream outputStream = p.getOutputStream();
        InputStream errorStream = p.getErrorStream();

        // 可以指定新进程的输入、输出和错误流的指向
        builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        // 将进程流重定向为文件
        builder.redirectInput(new File("in.txt"))
                .redirectOutput(new File("ou.txt"))
                .redirectError(new File("er.txt"));
        // 追加现有文件
        builder.redirectOutput(ProcessBuilder.Redirect.appendTo(new File("ou.txt")));
        // 合并输出流和错误流，设为true后就不能在使用redirectError和getErrorStream了
        builder.redirectErrorStream(true);

        // 修改进程的环境变量。需要先得到构建器的环境，再加入或删除环境变量条目
        Map<String, String> env = builder.environment();
        env.put("LANG", "fr_FR");
        env.remove("JAVA_HOME");
        // 使用ProcessBuild的属性启动一个新进程
        // 新进程将在directory()给出的工作目录中调用command()给出的命令和参数，并使用environment()给出的进程环境。
        p = builder.start();

        // 如果希望利用管道将一个进程的输出作为另一个进程的输入，可以使用startPipeline方法
        List<Process> processes = ProcessBuilder.startPipeline(List.of( // 建立四个进程
                new ProcessBuilder("find", "image"),
                new ProcessBuilder("grep", "-o", "\\.[^./]*$"),
                new ProcessBuilder("sort"),
                new ProcessBuilder("uniq")));
        Process last = processes.get(processes.size() - 1); // 取出最后一个进程
        String result = new String(last.getInputStream().readAllBytes()); //读取结果
    }

    /**
     * Process控制和监视进程
     */
    @Test
    public void test02() throws IOException, InterruptedException {
        Process process = new ProcessBuilder("gcc", "myapp.c").directory(new File("src")).start();
        // 读取进程的输出
        try (Scanner scanner = new Scanner(process.getInputStream())) {
            while (scanner.hasNextLine())
                System.out.println(scanner.nextLine());
        }

        // 等待一个进程完成，waitFor()返回退出值，0表示成功，或者返回一个非0的错误码
        int result = process.waitFor();
        // 当启动一个进程的时候，调用该方法查看进程是否仍然存活
        boolean alive = process.isAlive();

        // 设置等待时间，如果进程没有超时就返回true，否则就是false
        if (process.waitFor(10, TimeUnit.SECONDS)) {
            // 调用exitValue得到退出值
            result = process.exitValue();
        } else {
            // 杀死一个进程，下面两个方法效果与平台有关
            // process.destroy();
            process.destroyForcibly();
        }

        // 进程完成时会接收到一个异步通知，调用onExit可以获得一个CompletableFuture对象，用来调度任何动作
        CompletableFuture<Process> future = process.onExit();
        future.thenAccept(p -> System.out.println("结果为：" + p.exitValue()));
    }

    /**
     * ProcessHandle获取进程信息
     */
    public void test03() {
        // 要获取一个启动的进程的更多信息，可以使用ProcessHandle接口，使用以下四个方法获取该对象:
        //  1. 使用Process对象的toHandler()生成它的ProcessHandler
        //  2. 给定一个long型的操作系统进程ID，ProcessHandler.of(id)生成这个进程的句柄
        //  3. ProcessHandle.current()是运行这个JAVA虚拟机的进程的句柄
        //  4. ProcessHandler.allProcesses()可以生成对当前进程可见的所有操作系统进程的Stream<ProcessHandle>

        // 给定一个进行句柄，可以得到它的进程ID，父进程，子进程和后代进程
        ProcessHandle handle = ProcessHandle.current();
        long pid = handle.pid();
        Optional<ProcessHandle> parent = handle.parent();
        Stream<ProcessHandle> children = handle.children();
        Stream<ProcessHandle> descendants = handle.descendants();

        // info()获取进程的有关信息
        ProcessHandle.Info info = handle.info();
        Optional<String[]> arguments = info.arguments();
        Optional<String> command = info.command();
        Optional<String> s = info.commandLine();
        Optional<Instant> instant = info.startInstant();
        Optional<Duration> duration = info.totalCpuDuration();
        Optional<String> user = info.user();

        // ProcessHandle也可以监视和终止进程
        boolean alive = handle.isAlive();
        boolean b = handle.supportsNormalTermination();
        boolean destroy = handle.destroy();
    }

}

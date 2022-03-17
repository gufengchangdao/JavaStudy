package javastudy.errorProcess.exception_;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 测试带资源的try语句
 */
public class TryWithResources {
    /**
     * TODO try()可以自动调用资源的close()方法，就不用为了手动在finally中关闭资源还得再嵌套一层try-catch
     * 操作资源的类一般都实现了Closeable接口，它是AutoCloseable接口的子类，两个接口都是只有一个close()方法，Closeable的close抛出IOException，
     * 而AutoCloseable的close抛出Exception
     * 把资源打开在try()里面，当try块退出的时候会自动调用资源close()方法，无论是正常退出还是出现异常而退出，就好像使用了finally一样
     */
    private static void test01() {
        FileOutputStream out = null;
        try (Scanner in = new Scanner(new FileInputStream("src/javastudy/errorProcess/exception_/异常类.md"),
                StandardCharsets.UTF_8);
             //可以放多个
             out // try中也可以放声明过的事实最终变量
        ) {
            while (in.hasNext())
                System.out.println(in.next());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO try可以自动解决异常被覆盖的问题
     * 当try块中抛出一个异常，并且在close方法(类似finally中抛出异常)中也抛出一个异常时，就会抛出close方法的异常，原来的异常就会丢失掉了
     * 当出现这种情况时try()语句会 自动捕获 ，并由e.addSuppressed()方法增加到与原来的异常中
     * 你还可以调用e.getSuppressed()来获取一个异常数组，存有从close方法抛出被抑制的异常
     */
    private static void test02() {
        //两层try语句模拟无法处理异常必须要抛出的情况
        try {
            try (Scanner in = new Scanner(new FileInputStream("src/javastudy/errorProcess/exception_/异常类.md"),
                    StandardCharsets.UTF_8);
            ) {
                throw new Exception("try中异常");
            }
        } catch (Exception e) {
            e.printStackTrace(); //close方法不好抛出异常，打印不出来close方法抛出的异常
            // TODO e.getSuppressed()返回抑制的异常数组，没有自动调用addSuppressed()方法，因此是个空数组
            // System.out.println(Arrays.deepToString(e.getSuppressed()));
        }
    }

    /**
     * 读取控制台输入
     */
    private static void test03() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("请输入数据：");
            String str = scanner.nextLine();
            System.out.println("你输入的是：\n" + str);
        }
    }

    public static void main(String[] args) {
        // test01();
        // test02();
        test03();
    }
}

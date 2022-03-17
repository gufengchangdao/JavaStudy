package javastudy.errorProcess.exception_;

import java.util.*;

/**
 * 打印计算阶层方法的递归调用过程中的堆栈轨迹
 *
 * @author Cay Horstmann
 * @version 1.10 2017-12-14
 */
public class StackTraceTest {
    /**
     * Computes the factorial of a number
     *
     * @param n a non-negative integer
     * @return n! = 1 * 2 * . . . * n
     */
    public static int factorial(int n) {
        System.out.println("factorial(" + n + "):");
        var walker = StackWalker.getInstance();
        // TODO 在每个给定的栈帧上完成给定的动作，从最近调用的方法开始
        walker.forEach(System.out::println); //递归过程就会打印堆栈信息
        int r;
        if (n <= 1) r = 1;
        else r = n * factorial(n - 1);
        System.out.println("return " + r);
        return r;
    }

    public static void main(String[] args) {
        try (var in = new Scanner(System.in)) {
            System.out.print("Enter n: ");
            int n = in.nextInt();
            factorial(n);
        }
    }
}

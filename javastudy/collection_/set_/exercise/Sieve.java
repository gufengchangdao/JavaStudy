package javastudy.collection_.set_.exercise;

import java.util.BitSet;

/**
 * 使用位集合实现埃拉筛法，求1-2000000位中质数的个数和计算机位运算的时间
 *
 * @author Cay Horstmann
 * @version 1.21 2004-08-03
 */
public class Sieve {
    public static void main(String[] s) {
        int n = 2000000;
        long start = System.currentTimeMillis();
        var bitSet = new BitSet(n + 1); //创建位集
        int count = 0;
        int i;
        for (i = 2; i <= n; i++)
            bitSet.set(i); //全设为1
        i = 2;
        int sqrt = (int) Math.sqrt(n);
        while (i<=sqrt) {
            if (bitSet.get(i)) { //当前数是质数
                count++;
                int k = 2 * i;
                while (k <= n) {
                    bitSet.clear(k); //清楚该质数的所有倍数
                    k += i;
                }
            }
            i++;
        }
        // 剩下的数为1的全是质数
        while (i <= n) {
            if (bitSet.get(i)) count++;
            i++;
        }
        long end = System.currentTimeMillis();
        System.out.println(count + " primes");
        System.out.println((end - start) + " milliseconds");
    }
}
package javastudy.thread_.threadsecuritycollection;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 演示Arrays类中并行化方法的使用
 */
public class ParallelMethods {
    public static void main(String[] args) {
        // TODO Arrays类提供了大量的并行化方法，在处理大量数据和多处理器下效率远高于线性计算
        String[] strings = new String[10];
        for (int i = 9; i >= 0; i--) {
            strings[i] = "御坂" + (i + 1) + "号";
        }

        // TODO 并行化排序
        Arrays.parallelSort(strings);
        Arrays.parallelSort(strings, Comparator.comparingInt(String::length));
        Arrays.parallelSort(strings, 0, 9);

        // TODO 该方法由一个函数计算得到的值填充原函数，函数的参数是数组的索引
        Arrays.parallelSetAll(strings, s -> strings[s] + "卡哇伊");

        // TODO 还有一个很古怪的方法，用一个给定集合操作的相应前缀的累加结果替换各个数组元素
        int[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Arrays.parallelPrefix(ints, Integer::sum); //相加
        // Arrays.parallelPrefix(ints, (x,y)->x*y); //相乘
        for (int i : ints)
            System.out.println(i);
        // 得到数组{ 1 3 6 10 15 21 28 36 45 }，这其实是{1,1+2,1+2+3,...,1+2+3+...+9} 的结果
        // 大概原理是不断结合相邻、间隔两位、间隔三位...元素：{1,1+2,2,3,3+4,5,5+6...} --> {1,1+2+3,1+2+3+4,5,5+6,...}

    }
}

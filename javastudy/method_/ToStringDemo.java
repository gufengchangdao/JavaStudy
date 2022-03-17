package javastudy.method_;

import java.util.Arrays;

public class ToStringDemo {
    int len;
    double aDouble;

    // public String toString() {
    //     return "ToStringDemo{" +
    //             "len=" + len +
    //             ", aDouble=" + aDouble +
    //             '}';
    // }


    // 使用getClass().getName()而不用将类名硬编码写到toString()中去
    @Override
    public String toString() {
        return getClass().getName() +
                "len=" + len +
                ", aDouble=" + aDouble +
                '}';
    }

    public static void main(String[] args) {
        // 数组的toString()
        int[] arr={1,2,3,45,9};
        // 直接输出
        // System.out.println(arr); //[I@312b1dae   [I表明是一个整形数组
        // 优化
        System.out.println(Arrays.toString(arr)); //[1, 2, 3, 45, 9]
        // 打印多维数组就使用Arrays.deepToString()

    }
}

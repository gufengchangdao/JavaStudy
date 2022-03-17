package javastudy.utilities;

import java.util.Scanner;

/**
 * 概率问题的计算
 */
public class CalcUtils {
    public static void main(String[] args) {
        sampleData();
    }

    /**
     * 样本数据的计算，求N个数的
     * 和
     * 平均值
     * 最值
     * 均方差
     * 方差
     */
    public static void sampleData() {
        float sum = 0;
        float average;
        float maxDig = Float.MIN_VALUE;
        float minDig = Float.MAX_VALUE;
        float s = 0;
        float s2 = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.print("数据个数：");
        int mount = scanner.nextInt();
        System.out.println("请依次输入 " + mount + " 个数据：");
        float[] dig = new float[mount];
        for (int i = 0; i < mount; i++) {
            dig[i] = scanner.nextFloat();
            sum += dig[i];
        }

        average = sum / mount;
        for (int i = 0; i < mount; i++) {
            s2 += Math.pow(dig[i] - average, 2);
            if (dig[i] > maxDig)
                maxDig = dig[i];
            if (dig[i] < minDig)
                minDig = dig[i];
        }
        s2 /= mount - 1; //TODO 样本的方差是除以N
        s = (float) Math.sqrt(s2);
        System.out.println("--------- " + mount + " 个数据计算结果 ---------");
        System.out.println("和 " + sum);
        System.out.println("平均值 " + average);
        System.out.println("最大值 " + maxDig + ",最小值 " + minDig);
        System.out.println("均方值 " + s);
        System.out.println("方差 " + s2);
    }
}

package javastudy.array_;

import java.lang.reflect.Array;

/**
 * 数组的基础知识和Array的一些方法
 */
public class ArrayDemo {
    public static void main(String[] args) {
        ArrayDemo main = new ArrayDemo();
        main.staticMethods();

    }

    /**
     * 基础知识
     */
    public void basic() {
        // java的数组就是一个对象
        Object[] o = new Object[0]; //长度为0的数组，跟null不同
        Object temp = o;

        //一位数组创建
        //动态初始化
        int[] arr1 = new int[10];
        int[] arr2;
        arr2 = new int[12];
        //静态初始化
        int[] arr3 = {1, 2, 3, 4};
        int[] arr4 = new int[4];
        arr4[0] = 1;
        arr4[1] = 2;
        int[] arr5 = new int[]{1, 2, 3, 4};

        //二维数组创建
        int[][] arr6 = new int[2][3];
        int[][] arr7 = {{1, 2, 3}, {1, 2}, {4, 5, 6}};//列数不同，3,2,3
    }

    /**
     * Array还有一些静态方法，可用于处理不知道数组类型的但是数组引用是Object对象，来获取数组的一些数据
     */
    public void staticMethods() {
        long[] longs = {123, 456, 789, 147, 258, 369};
        Object obj = longs;

        // TODO getXxx()
        System.out.println(Array.get(obj, 2)); //返回的是Object类型的数据
        System.out.println(Array.getLong(obj, 2)); //返回long数据

        // TODO setXxx()
        Array.set(obj, 0, 999);
        Array.setInt(obj, 1, 888); //使用int也不会报错

        // TODO getLength()
        System.out.println(Array.getLength(obj));

        // TODO newInstance()创建指定类型和大小的数组，还可以创建二维数组
        int[] ints = (int[]) Array.newInstance(int.class, 10);
    }

}



package javastudy.array_;

import java.util.Arrays;
import java.util.List;

/**
 * 演示伴生类Arrays的常用方法
 */
public class ArraysDemo {
	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		//返回由指定数组支持的固定大小的列表
		List<Object> list1 = Arrays.asList((Object) arr);
		List<String> list2 = Arrays.asList("字", "符", "串");

		// 数组的复制
		int[] newArr = Arrays.copyOf(arr, arr.length * 2);
		System.arraycopy(arr, 0, newArr, arr.length, arr.length); //优秀的复制方法，比for循环快
        System.out.println(Arrays.toString(newArr));

		// 排序
		Arrays.sort(arr);

		// 快速打印多维数组元素列表
		int[][] arr1 = {{1, 2, 3}, {2, 3, 4}, {5}};
		System.out.println(Arrays.deepToString(arr1));
	}
}

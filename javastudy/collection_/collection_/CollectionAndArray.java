package javastudy.collection_.collection_;

import java.util.LinkedList;
import java.util.List;

/**
 * 数组和集合的相互转换
 */
public class CollectionAndArray {
    public static void main(String[] args) {
        String[] strings = new String[]{"巫师","剑士","武僧","格斗家","弓箭手"};

        // TODO 数组转集合,使用List.of()包装器
        LinkedList<String> list = new LinkedList<>(List.of(strings));

        // TODO 集合转数组，使用toArray()方法
        // 1. 返回Object类型的数组
        Object[] obj01 = list.toArray();
        // 2. 传入指定类型并且大小为0的数组，返回相同类型数组
        String[] obj02 = list.toArray(new String[0]);
        // 3. 传入长度大于等于集合个数的数组，会把元素赋给该数组，不会创建新数组，还是原数组
        String[] strings1 = new String[strings.length];
        String[] obj03 = list.toArray(strings1);
        System.out.println(strings1 == obj03); //true
        // 4. 使用方法引用，传入构造器，返回指定类型的数组，还是这个看起来更优雅
        String[] obj04 = list.toArray(String[]::new);

        // TODO 自己循环遍历，一个一个赋值，上面的方法也无非是遍历一个一个添加

    }
}

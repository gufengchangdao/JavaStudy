package javastudy.collection_.collection_;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Collection接口常用方法的测试
 */
public class CollectionMethod {
    public static void main(String[] args) {
        Collection list = new ArrayList();

        //TODO 添加
        list.add("TRICK");
        list.add(10);
        list.add(true);
        //boolean addAll(Collection<? extends E> c)

        System.out.println(list.isEmpty());

        //TODO 返回迭代器
        Iterator iterator = list.iterator();
        // 迭代器越过第一个元素后并返回第一个元素
        Object first = iterator.next();
        // 迭代器删除上次调用next()时返回的元素，毕竟只有看过的元素才决定是否删除
        // 如果想用remove()删除两个连续的元素，中间需要插入一个next()，因为必须让迭代器跨过第二个元素才能删除
        iterator.remove(); // 集合中移除了第一个元素

        System.out.println(list.size());

        // TODO 删除元素
        list.remove("TRICK");   //调用删除对象的equals方法判断
        // 删除另一个集合c中出现过的所有元素
        //boolean removeAll(Collection<?> c)
        // 删除另一个集合c中未出现过的元素
        // list.retainAll();
        // 删除符合条件的元素
        list.removeIf(e -> {return false;});

        //TODO 返回一个包含此集合中所有元素的数组
        Object[] objects = list.toArray();

        //返回包含此集合中所有元素的数组; 返回的数组的运行时类型是指定数组的运行时类型，这里类型不都是String，会抛异常
//        String[] strings=new String[10];
//        list.toArray(strings);

        // TODO 清空集合
        list.clear();
    }

}

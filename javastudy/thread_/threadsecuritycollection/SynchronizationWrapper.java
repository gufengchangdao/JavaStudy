package javastudy.thread_.threadsecuritycollection;

import java.util.*;

/**
 * 演示同步包装器的使用
 */
public class SynchronizationWrapper {
    public static void main(String[] args) {
        /* TODO: 2022/1/16 同步包装器
            1. 早期提供了Vector和HashTable这样线程线程安全的实现，但现在这些类被认为已经过时，集合库中提供了不同的机制，即同步包装器
            2. 任何集合类都可以使用同步包装器变成线程安全的，其原理是把通用方法都重写了，都使用synchronized代码块把原方法放进去，而获取的锁
            即为集合本身
            3. 应该要确保没有任何线程通过原始的非同步方法访问数据结构。最简单的方法就是确保不保存任何原始对象的引用，简单地创造一个集合并立即传
            递给包装器，像下面的一样
            4. concurrent包下的集合比包装器更加优秀，他们是精心实现的，在映射中即便多个线程访问不同的桶，他们都能访问map而不受到阻塞。其中经
            常更改的数组列表是一个例外，同步的ArrayList要胜过CopyOnWriteArrayList
         */
        // 注意这里没有保存ArrayList的原始引用
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());

        // 注意在使用迭代器的时候还是需要加synchronized，源码也说了必须由用户手动同步
        // public Iterator<E> iterator() {return c.iterator(); // Must be manually synched by user!}
        synchronized (list){
            for (Integer e : list) {
                System.out.println(e);
            }
        }

    }
}

/*
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *

 *   内部静态类
 *       static class Node<K,V> implements Map.Entry<K,V> {
 *           final int hash;     哈希值
 *           final K key;
 *           V value;
 *           Node<K,V> next;
 *       ...
 *       }
 *
 * */
/*
HashMap
1. 实现了Map接口
2. hashMap底层是数组 + 链表 + 红黑树
3. HashMap类大致相当于Hashtable ，除了它是不同步(线程不安全)的，并允许null
4. 并允许null的值和null键
5. 两个重要参数:
    初始容量:创建哈希表是的容量              DEFAULT_INITIAL_CAPACITY = 1 << 4;  默认初始容量
    负载因子:调用哈希表容量增长操作的度量      DEFAULT_LOAD_FACTOR = 0.75f;        默认负载因子
6. 无参构造器初始化负载因子为0.75，此时table为null，第一次添加扩容到16，大于临界值(length*0.75)就会扩容到原先的2倍，并计算新的临界值
这里选2是有原因的，HashMap存储元素的位置是根据计算的哈希值和和数组的大小的，具体为 p = tab[i = (n - 1) & hash]，其中n就是索引，数组扩容后n也
会发生改变，那么i发生变化的话就无法再使用get()找到原先值所在索引了，但是默认扩容情况为16、32、64...，就算指定了初始容量也会后来扩容到这些值，使用
这些值计算索引，结果就只取决于哈希值了，不用担心因为数组扩容而找不到原索引位置
7.
 */
package javastudy.collection_.map_;

import java.util.*;

public class HashMapDemo {
    public static void main(String[] args) {

        //1.创建对象
        HashMap hashMap = null;
        //(1) 指明泛型类型，不指明是默认为<Object,Object>
//        HashMap<String,Integer> hashMap=new HashMap();
        //默认构造，初始容量默认为16，负载因子默认为0.75
        hashMap = new HashMap();
        //(3) 指定初始容量
//        hashMap=new HashMap(20);
        //(4) 指定初始容量和负载因子
//        hashMap=new HashMap(20,0.8f);

        //2.创建复制品
        HashMap hashMapCopy = new HashMap(hashMap);

        //3.将指定的值与此映射中的指定键相关联
        hashMap.put(null, null);
        hashMap.put("beiTa", true);
        hashMap.put("name", 15.2D);

        //4.将指定map集合所有键值对放入
        hashMap.putAll(hashMapCopy);

        //5.删除指定的键值对
        hashMap.remove(null);
//        hashMap.remove(null,null);


        //6.是否还有对应的键值对
        System.out.println(hashMap.containsKey("beiTa"));//true
        System.out.println(hashMap.containsValue(true));//true

        //7.获取指定key对应的value
        //get的妙用：当要检测某个元素是否在map中，不需要遍历，只用调用get() 方法看是否返回null即可，get()方法可以直接锁定要查找元素对应的索引位置
        System.out.println(hashMap.get("name"));    //15.2,方法返回的是V型对象，这里是Object
        System.out.println(hashMap.get("xiaoBai")); //null，找不到就返回null

        // 处理键第一次出现的问题
        System.out.println(hashMap.getOrDefault("你好", "默认值"));//找不到不是返回null而是传入的默认值
        hashMap.putIfAbsent("你好", "小明"); //如果不存在指定键，就添加该键值对

        //8.检测是否为空
        System.out.println(hashMap.isEmpty());//false

        //9.返回键值对数量
        System.out.println(hashMap.size());//2

        //10.返回key集合(Set)
        Set set = hashMap.keySet();

        //11.返回value集合(Collection)
        Collection values = hashMap.values();

        //为了方便遍历，创建了entrySet集合，该集合中的元素类型为Entry
        //entrySet中，定义了类型为Map.Entry，但实际上存放的还是HashMap$Node
        //注意entrySet的速度要高于keySet方法，建议遍历的时候使用entrySet
        Set set1 = hashMap.entrySet();
        System.out.println(set1.getClass());//class java.util.HashMap$EntrySet
        Iterator iterator = set1.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next.getClass());
            System.out.println(((Map.Entry) next).getKey() + " " + ((Map.Entry) next).getValue());
        }

        //12.删除所有映射
        hashMap.clear();
    }
}

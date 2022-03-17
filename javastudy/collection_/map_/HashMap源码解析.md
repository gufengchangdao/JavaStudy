```java

/*
HashMap
   重要参数:
   初始容量:创建哈希表是的容量
   负载因子:调用哈希表容量增长操作的度量

   底层为数组Node<K,V>[] table，Node为HashMap静态类
   无参构造器初始化负载因子为0.75，此时table为null，第一次添加扩容到16，大于临界值(length*0.75)就会扩容到原先的2倍，并计算新的临界值
   有参构造器可以传入初始容量和负载因子
   如果一条链表的元素个数达到TREEIFY_THRESHOLD(8)，并且table大小达到MIN_TREEIFY_CAPACITY(64)，该链表就会进行树化(红黑树)，否则仍然
       采用数组扩容机制

*/
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"all"})
public class HashMapExplain {
   /**
    * 默认初始容量
    */
   static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
   /**
    * 最大初始容量，如果指定的初始容量大于该值，则按照该值创建
    */
   static final int MAXIMUM_CAPACITY = 1 << 30;
   /**
    * 默认负载因子
    */
   static final float DEFAULT_LOAD_FACTOR = 0.75f;
   /**
    * 树化阈值
    */
   static final int TREEIFY_THRESHOLD = 8;

   static final int UNTREEIFY_THRESHOLD = 6;
   /**
    *最小树容量
    */
   static final int MIN_TREEIFY_CAPACITY = 64;
   /**
    *静态内部类
    */
   static class Node<K,V> implements Map.Entry<K,V> {
       final int hash;         //每个节点会把哈希值存入，当数组扩容后，同一个对象再次计算哈希值也不会是这个索引，但是表的数据不会更改
       final K key;
       V value;
       HashMap.Node<K, V> next;
       //...
   }

   /**
    * HashMap.Node类的数组，长度总是2的幂次方
    */
   transient Node<K,V>[] table;

   transient Set<Map.Entry<K,V>> entrySet;

   /**
    * 保存键值对的个数
    */
   transient int size;

   /**
    * 结构被修改的次数
    */
   transient int modCount;

   /**
    * 下次扩容所需size大小
    */
   int threshold;

   /**
    * 负载因子
    */
   final float loadFactor;

   static final int tableSizeFor(int cap){};   //使用带参数的构造器，构造器中使用此方法初始化 threshold



   //构造器
   //传入初始容量和负载因子，给loadFactor赋值，计算 threshold
   public HashMap(int initialCapacity, float loadFactor){}
   //使用默认负载因子并调用上一个构造器
   public HashMap(int initialCapacity){}
   //使用默认负载因子
   public HashMap(){}
   //使用默认负载因子并把Map集合中每一个键值对赋给值新对象
   public HashMap(Map<? extends K, ? extends V> m){}

   public boolean isEmpty() {return size == 0;}  //简单的判断

   /**
    * 查找
    */
   public V get(Object key) {
       HashMap.Node<K,V> e;
       return (e = getNode(key)) == null ? null : e.value;     //这里找得到就返回对象，找不到就返回null
   }

   final HashMap.Node<K,V> getNode(Object key) {
       //tab为表，hash为key的哈希值
       HashMap.Node<K,V>[] tab; HashMap.Node<K,V> first, e; int n, hash; K k;  //辅助变量
       //第一步判断 (1)表不为空 (2)表中存在数据 (3)key对应的索引下存在数据
	   //由哈希值计算索引，注意n扩容按照16/32/64的规律扩容，索引只取决于key的大小，不用担心扩容会影响get()获取原值
       if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & (hash = hash(key))]) != null) { 
           //先检查第一个节点(哈希值相等前提下 同一个对象或者equals方法判定相等 )
           if (first.hash == hash &&
                   ((k = first.key) == key || (key != null && key.equals(k))))
               return first;
           //检查后续节点
           if ((e = first.next/*e现在是第二个节点*/) != null) {
               if (first instanceof HashMap.TreeNode)  //如果该下标对应的节点已经是一棵树了
                   return ((HashMap.TreeNode<K,V>)first).getTreeNode(hash, key);
               do {                                    //还不是树
                   if (e.hash == hash &&
                           ((k = e.key) == key || (key != null && key.equals(k))))
                       return e;
               } while ((e = e.next/*寻找下一个节点*/) != null);
           }
       }
       return null;
   }

   /*
   HashMap put添加步骤:
       (1) 计算哈希值
       (2) 判断是否是第一次添加，是的话添加前先扩容
       (3) 判断哈希值确定的所引处是否已经有元素
           没有就直接添加
           有的话:先和第一个节点的元素比较是否重复，重复就不添加
                 不重复的话，判断是否是一颗红黑树，是就调用红黑树的方法继续判断后续节点有无重复元素决定是否添加
                 前两个都不满足，遍历链表看后续节点是否有重复元素，有就不添加，无重复就添加到表尾并判断是否符合树化条件
                 上述3个条件判断后，有重复元素就替换原value并返回
       (4) (添加成功后)判断是否需要扩容
       (5) 结束
   */
   /**
    * 添加(或者替换)操作
    */
   public V put(K key, V value) {
       return putVal(hash(key)/*计算哈希值*/, key, value, false, true);
   }//原先有该key就返回原value，没有就返回null

   final V putVal(int hash, K key, V value, boolean onlyIfAbsent/*如果已经存在是否放弃添加*/,
                  boolean evict) {
       HashMap.Node<K,V>[] tab; HashMap.Node<K,V> p; int n, i; //定义辅助变量
       //判断是否是第一次添加，是的话添加前先扩容
       if ((tab = table) == null || (n = tab.length) == 0)
           n = (tab = resize()).length;
       //判断key对应的索引处是否有元素了，没有就添加，有就判断是否有重复的再决定是否添加
       if ((p = tab[i = (n - 1) & hash]) == null)
           tab[i] = newNode(hash, key, value, null);//newNode方法会调用Node的构造器创建对象
       else {
           HashMap.Node<K,V> e; K k;
           //判断key这个索引处的元素key、==、equals是否相同，判定是"同一个对象"的话，就不添加
           //这个判断机制可由自己决定(重写key对应类的equals方法)
           if (p.hash == hash &&
                   ((k = p.key) == key || (key != null && key.equals(k))))
               e = p;
           //不相等时再判断 p 是不是一颗红黑树
           //如果是一颗红黑树，则调用putTreeVal方法判断是否添加
           else if (p instanceof HashMap.TreeNode)
               e = ((HashMap.TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
           //前两个都不满足，再把key和后续节点比较，判断是否添加，如果添加的话就添加到链表尾部
           else {
               for (int binCount = 0; ; ++binCount) {
                   //循环到链表尾部，添加到链表尾部
                   if ((e = p.next) == null) {
                       p.next = newNode(hash, key, value, null);
                       //如果该链表结点总数等于树化阈值(8)了就对该链表进行树化准备
                       //调用treeifyBin方法还不会立即树化，如果tab.length < MIN_TREEIFY_CAPACITY(64)，只会扩容操作(resize)，不会树化

                       if (binCount >= TREEIFY_THRESHOLD - 1)
                           treeifyBin(tab, hash);
                       break;
                   }
                   //找到相同的元素，不再添加
                   if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                       break;
                   p = e;
               }
           }
           // 替换操作，如果有重复的元素，到这里e为重复的元素，否则e为null
           if (e != null) {
               V oldValue = e.value;
               if (!onlyIfAbsent || oldValue == null)
                   e.value = value;    //map是替换原value，set是共用一个value，替换了引用其实还是一个对象
               afterNodeAccess(e);     //空方法
               return oldValue;        //返回原value
           }
       }
       ++modCount;
       //判断是否需要扩容，添加元素后再和增量阈值比较的
       if (++size > threshold)
           resize();
       afterNodeInsertion(evict);//这个方法是空方法，是用来让HashMap子类去实现的，比如添加一个元素后删除旧的元素来释放内存
       return null;    //返回null代表成功
   }

   /**
    * 扩容操作<br>
    * 一般扩容为原先的2倍
    * 元素的索引会重新计算
    * @return 扩容后的table表
    */
   final HashMap.Node<K,V>[] resize() {
       HashMap.Node<K,V>[] oldTab = table;
       int oldCap = (oldTab == null) ? 0 : oldTab.length;  //旧容量
       int oldThr = threshold;
       int newCap, newThr = 0; //新容量、新扩容阈值

       //非首次扩容
       if (oldCap > 0) {
           //达到最大容量，不再扩容
           if (oldCap >= MAXIMUM_CAPACITY) {
               threshold = Integer.MAX_VALUE;
               return oldTab;
           }
           //新容量不超过最大容量时，容量扩容为两倍
           else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                   oldCap >= DEFAULT_INITIAL_CAPACITY)
               newThr = oldThr << 1; // double threshold
       }
       //新容量为旧的扩容阈值
       else if (oldThr > 0)
           newCap = oldThr;
       //首次扩容，零初始阈值使用默认值
       else {
           newCap = DEFAULT_INITIAL_CAPACITY;                              //16，新容量
           newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);//12 ，计算增量阈值
       }

       if (newThr == 0) {
           float ft = (float)newCap * loadFactor;
           newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                   (int)ft : Integer.MAX_VALUE);
       }
       threshold = newThr;
       HashMap.Node<K,V>[] newTab = (HashMap.Node<K,V>[])new HashMap.Node[newCap]; //真正扩容
       table = newTab;
       //数据转移到新table表中去
       if (oldTab != null) {
           for (int j = 0; j < oldCap; ++j) {
               HashMap.Node<K,V> e;
               if ((e = oldTab[j]) != null) {
                   oldTab[j] = null;   //便于回收
                   //如果该索引处只有一个节点
                   if (e.next == null)
                       newTab[e.hash & (newCap - 1)/*重新计算下标*/] = e;
                   //如果该索引处是一颗红黑树
                   else if (e instanceof HashMap.TreeNode)
                       ((HashMap.TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                   //如果该索引处为一个链表
                   else {
                       HashMap.Node<K,V> loHead = null, loTail = null;
                       HashMap.Node<K,V> hiHead = null, hiTail = null;
                       HashMap.Node<K,V> next;
                       do {
                           next = e.next;
                           if ((e.hash & oldCap) == 0) {
                               if (loTail == null)
                                   loHead = e;
                               else
                                   loTail.next = e;
                               loTail = e;
                           }
                           else {
                               if (hiTail == null)
                                   hiHead = e;
                               else
                                   hiTail.next = e;
                               hiTail = e;
                           }
                       } while ((e = next) != null);
                       if (loTail != null) {
                           loTail.next = null;
                           newTab[j] = loHead;
                       }
                       if (hiTail != null) {
                           hiTail.next = null;
                           newTab[j + oldCap] = hiHead;
                       }
                   }
               }
           }
       }
       return newTab;
   }

   /*----------- entryset -----------*/

   /**
    * 为了方便遍历，HashMap里面还提供了entrySet方法
    * 返回一个 Set<Map.Entry<K,V>> 集合，entrySet集合里面存放的是键值对
    * Map.Entry是Map声明的一个内部接口，它表示Map中的一个实体（一个key-value对）。接口中有getKey(),getValue方法。
    * 集合中的元素编译类型为Map.Entry，运行类型还是 HashMap$Node(table表中的数据,Entry子类) ， 可以把元素向上转型为Map.Entry来调用其方法
    *
    */
   public Set<Map.Entry<K,V>> entrySet() {
       Set<Map.Entry<K,V>> es;
       return (es = entrySet) == null ? (entrySet = new HashMap.EntrySet()) : es;
   }
}

```
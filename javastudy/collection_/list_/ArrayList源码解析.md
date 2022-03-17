```java
import jdk.internal.util.ArraysSupport;

import java.util.ArrayList;
import java.util.Arrays;

/**
* ArrayList源码解读
*/
public class ArrayListMethod {
   @SuppressWarnings({"all"})
   /*
   * ArrayList中维护了一个Object类型的数组elementData
   * 创建ArrayList对象时，如果使用无参构造器，则初始elementData容量为0，第一次添加会扩容到10，再次扩容时就是之前的1.5倍
   *   如果使用指定大小的构造器，则初始elementData容量为指定大小，再次扩容也是原先1.5倍
   * 在添加是size==length时会进行扩容
   *
   * */

   public boolean add(E e) {
       modCount++;
       add(e, elementData, size);
       return true;
   }

   private void add(E e, Object[] elementData, int s) {
       if (s == elementData.length)    //不够用了才进行扩容
           elementData = grow();
       elementData[s] = e;             //添加
       size = s + 1;
   }



   /**
    * 默认初始容量
    */
   private static final int DEFAULT_CAPACITY = 10;

   /**
    * 数组最大长度，再大可能会内存溢出
    */
   public static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;

   /**
    * 空数组
    */
   private static final Object[] EMPTY_ELEMENTDATA = {};

   private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

   transient Object[] elementData;

   /**
    * 记录集合被修改的次数，防止多线程操作出现异常
    */
   protected transient int modCount = 0;

   /**
    * 元素个数
    */
   private int size;

   //构造器

   /**
    * 传入初始容量
    * 传入0就数组就为空数组，小于0抛异常，大于0才创建对象数组
    * @param initialCapacity
    */
   public ArrayList(int initialCapacity) {
       if (initialCapacity > 0) {
           this.elementData = new Object[initialCapacity];
       } else if (initialCapacity == 0) {
           this.elementData = EMPTY_ELEMENTDATA;
       } else {
           throw new IllegalArgumentException("Illegal Capacity: "+
                   initialCapacity);
       }
   }

   /**
    * 无参构造器，数组初始化为空数组
    */
   public ArrayList() {
       this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
   }

   /**
    * 裁剪掉无用的空间，可以用这个方法来最小化数组所占据的空间
    */
   public void trimToSize() {
       modCount++;
       if (size < elementData.length) {
           elementData = (size == 0)
                   ? EMPTY_ELEMENTDATA                 //空数组
                   : Arrays.copyOf(elementData, size); //裁剪后的数组
       }
   }

   /**
    * 确定容量是否够用
    * 只有 (1)指定的最小容量大于当前容量 (2)数组不为空 (3)指定的最小容量大于默认初始容量 才会扩容
    *
    */
   public void ensureCapacity(int minCapacity) {
       if (minCapacity > elementData.length
               && !(elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
               && minCapacity <= DEFAULT_CAPACITY)) {
           modCount++;
           grow(minCapacity);  //增加容量
       }
   }


   /**
    * 扩容操作
    * minCapacity为实际需要的最小容量
    *
    */
   private Object[] grow(int minCapacity) {
       int oldCapacity = elementData.length;
       if (oldCapacity > 0 || elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
           int newCapacity = ArraysSupport.newLength(oldCapacity,
                   minCapacity - oldCapacity, /* 最小增长量 */
                   oldCapacity >> 1           /* 首选增长量(0.5倍) */);
           return elementData = Arrays.copyOf(elementData, newCapacity);
       } else {
           //第一次扩容
           return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
       }
   }

   /**
    * 由已知参数计算所需新容量
    * oldLength    旧容量
    * minGrowth    最小增长量
    * prefGrowth   首选增长量(0.5倍)
    *
    */
   public static int newLength(int oldLength, int minGrowth, int prefGrowth) {
       int newLength = Math.max(minGrowth, prefGrowth) + oldLength;    //一般都是prefGrowth + oldLength，即 oldLength * 1.5
       if (newLength - MAX_ARRAY_LENGTH <= 0) {
           return newLength;
       }
       return hugeLength(oldLength, minGrowth);    //如果新容量太大就会使用最小所需容量
   }

}

```
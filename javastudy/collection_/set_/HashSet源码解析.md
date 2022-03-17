```java
import java.util.HashMap;

public class HashSetExplain {

   /**
    * HashSet的底层-->HashMap
    */
   private transient HashMap<E,Object> map;

   /**
    * 占位用(共享"女友")
    */
   private static final Object PRESENT = new Object(); //需要是静态的

   /**
    * 添加操作
    */
   public boolean add(E e) {
       return map.put(e, PRESENT)==null;   //map.put方法返回null才是添加成功，因此添加成功返回true
   }

}
```
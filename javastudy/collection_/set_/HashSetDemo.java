/*
HashSet
    底层为HashMap
    不能存入相同的对象，但是相不相同是由哈希值、==、equals方法判断，可以重写equals方法，由自己来决定添不添加

 */
package javastudy.collection_.set_;

import java.util.HashSet;
import java.util.Iterator;

public class HashSetDemo {
    @SuppressWarnings({"all"})
    public static void main(String[] args) {
        HashSet hashSet = new HashSet();

        hashSet.add("KA WA YI");

        System.out.println(hashSet.contains("KA WA YI"));

        System.out.println(hashSet.isEmpty());

        System.out.println(hashSet.size());

        Iterator iterator = hashSet.iterator();

        Object[] objects = hashSet.toArray();   //这个返回的是Object类型
        //没有指明泛型类型的话就只能接收Object类型，指明以后可以传入数组，使得返回的类型和数组类型一致
        HashSet<String> strings = new HashSet<>();
        String[] strings1 = strings.toArray(new String[0]);//这个返回的类型是String类型

        hashSet.remove("KA WA YI");

        hashSet.clear();
    }
}

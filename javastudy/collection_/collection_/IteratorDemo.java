/*
Iterator
    Iterator对象成为迭代器，主要用于遍历Collection集合中的元素
    只要是实现了Collection接口的类都可以使用Iterator

*/
package javastudy.collection_.collection_;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 演示ArrayList集合的遍历
 */
public class IteratorDemo {
    @SuppressWarnings("all")
    public static void main(String[] args) {
        Collection list = new ArrayList();
        list.add("TALENT");
        list.add(999);
        list.add(true);
        list.add(null);//存储为字符串

        System.out.println("--- iterator ---");
        // TODO 1. 迭代器遍历，实现了Iterable的集合类都可以使用迭代器
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){ //判断是否还有下一个数据
            System.out.println(iterator.next());    //返回迭代中的下一个元素
        }

        iterator = list.iterator(); //可以通过重新赋值回到开始位置(也只能重新调用)
        // TODO 2. 使用forEachRemaining()来遍历，需要传入一个lambda表达式
        iterator.forEachRemaining(e->{
            System.out.println(e);
        });

        System.out.println("--- foreach ---");
        // TODO 3. foreach遍历，其实也是交给编译器替换为迭代器遍历
        for (Object x : list) {
            System.out.println(x);
        }

        //TODO 4. 如果是ArrayList，还可以用普通for循环遍历
        ArrayList arrayList=(ArrayList) list;
        for (int i = 0; i <arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
    }
}

package javastudy.reflection_.exercise;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 利用反射实现泛型类型的擦除
 */
public class TypeEraSure {
    public static void main(String[] args) throws ReflectiveOperationException {
        ArrayList<String> list = new ArrayList<>();

        list.add("张三");
        list.add("张三妻子");
        list.add("张三儿子");
        list.add("全在牢中");

        // TODO 利用反射可以添加其他非指定泛型类型的元素
        Method addMethod = list.getClass().getMethod("add", Object.class);
        addMethod.invoke(list,110);

        System.out.println(list); // [张三, 张三妻子, 张三儿子, 全在牢中, 110]
    }
}

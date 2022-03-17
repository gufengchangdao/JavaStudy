package javastudy.genericity_;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.function.IntFunction;

/**
 * 泛型的限制与局限性，多是类型擦除惹的祸
 * 下面的代码不少走擦边球，所以警告可能有点多
 */
public class GenericityLimit {

    public static void main(String[] args) {
        // TODO 1. 不能用基本数据类型实例化类型参数
        // 主要原因是类型擦除后，Object无法存储基本类型
        // Child<int> child = new Child<int>();

        // TODO 2. 运行时类型查询只适用于原始类型
        Child<String> child = new Child<>();
        // if(child instanceof Child<String>) //报错
        if (child instanceof Child) {} //可以
        System.out.println(child.getClass()); //class Child

        // TODO 3. 不能创建参数化类型的数组，但是可以声明
        //  建议不用数组而使用集合，像ArrayList
        // 因为擦除后如果赋值为不同类型的泛型类型无法检测出来
        Child[] childs = new Child[10];
        // Child[] childs = new Child<Integer>[10]; //报错，
        childs[0] = new Child<String>(); //如果用上面那个，这个就无法检测出来

        //声明还是可以的
        Child<String>[] childs1 = childs;

        // 可以先声明通配类型再强制类型转换
        Child<Integer>[] childs2 = (Child<Integer>[]) new Child[10];
        // childs2[0]=new Child<String>(); //报错

        // 有可变参数的方法，虚拟机就会创建String类型的泛型数组
        addAll(null, new Child<String>(), new Child<String>());


    }

    // 可变参数的方法
    public static <T> void addAll(Collection<T> coll, T... ts) {
        System.out.println(ts.getClass().getName());
        // coll.addAll(Arrays.asList(ts));
    }


}

// TODO 4. 不能抛出或捕获泛型类异常，实际上连扩展Throwable都不可以
// class Child<T> extends Exception{ //报错
class Child<T> {
    public T first;
    public T second;
    public Object[] elements;

    public Child() {
    }

    public Child(T first, T second) {
        this.first = first;
        this.second = second;
    }

    // TODO 5. 不能实例化类型变量
    // T first = new T(); //报错

    // 可以使用反射来解决这个问题，传入一个泛型类型的类对象
    // Class类本身是泛型的，String.class就是一个Class<String>的实例(还是唯一实例)
    public static <T> Child<T> getInstance(Class<T> cl) { //T取Class对象的类型
        try {
            return new Child<>(cl.getConstructor().newInstance(), cl.getConstructor().newInstance());
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public T get(int n) {
        return (T) elements[n];
    }

    // TODO 6. 要创建泛型的数组主要可以利用函数式表达式或者反射
    // 使用函数式表达式来创建数组
    // Child.get(value -> new Integer[5],12,123);
    public static <T> void get(IntFunction<T[]> constr, T... a) {
        T[] result = constr.apply(2);
        System.out.println(result.getClass());
    }

    // 使用反射获取元素类对象并调用Array的newInstance()
    public static <T> void get(T... a) {
        T[] result = (T[]) Array.newInstance(a.getClass().getComponentType(), 2);
        System.out.println(result.getClass());
    }

    // TODO 7. 可以取消对检查型异常的检查
    // 对捕获到的检查型异常调用这个方法，这个方法的异常可以不用捕获，也就是“骗”编译器说这个异常不是检查型异常，要注意的是这个方法的异常不能捕获，
    // 就是直接输出堆栈轨迹了
    static <T extends Throwable> void throwAs(Throwable t) throws T {
        throw (T) t;
    }

    // TODO 8. 注意擦除后的冲突
    // 擦除后变为equals(Object)， 这与Object.equals(Object)相冲突
    // 解决方式：重新命名
    // public boolean equals(T value) {
    // }
}
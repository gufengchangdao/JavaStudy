/*
static
    什么时候会加载static语句块？
    当一个类被主动使用时，Java虚拟就会对其初始化，类初始化的时候，就会执行静态代码。如下六种情况为主动使用：
        (1) 当创建某个类的新实例时（如通过new或者反射，克隆，反序列化等）
        (2) 当调用某个类的静态方法时
        (3) 当使用某个类或接口的静态字段时
        (4) 当调用Java API中的某些反射方法时，比如类Class中的方法，或者java.lang.reflect中的类的方法时
        (5) 当初始化某个子类时
        (6) 当虚拟机启动某个被标明为启动类的类（即包含main方法的那个类）
    Java编译器会收集所有的类变量初始化语句和类型的静态初始化器，将这些放到一个特殊的方法中：clinit。
    特别要注意的是：使用final修饰的静态字段，在调用的时候不会对类进行初始化！以及类被加载了不一定就会执行静态代码块，只有一个类被主动使用的时候，静态代码才会被执行！

*/
package javastudy.keyword_;

public class StaticDemo {
    public static void main(String[] args) {
        // new A();
        //单使用final修饰的静态字段不会使类被加载
        System.out.println(A.num);
    }
}
class A{
    public static final int num=5;

    static {
        System.out.println("A Static Load");
    }
}

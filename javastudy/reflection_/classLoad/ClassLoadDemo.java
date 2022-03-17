/*
类加载：
    静态加载：
        编译时加载相关的类，如果没有就报错，依赖性太强，
            比如new一个没有的类，java文件无法编译成class文件(在IDEA上不行，会立马提示)
    动态加载：
        运行时加载需要的类，如果运行时不用该类，即使不存在该类，也不报错，降低了依赖性
            比如Class.forName("Dog"); 即使没有Dog类，不运行这一步也不会报错

    类加载时机
        1、当创建对象的时候                  静态加载
        2、当子类被加载，父类也被加载          静态加载
        3、调用类中的静态成员时               静态加载
        4、通过反射                         动态加载
    类不会被加载的情况
        1、使用类名.class的方式获取类对象
        2、调用类的static final常量(编译器常量)，但如果是static final修饰的是一个域(比如要创建一个其他的对象)，类就会被加载
 */
package javastudy.reflection_.classLoad;

/**
 * 演示静态加载和动态加载
 */
public class ClassLoadDemo {
    public static void main(String[] args) throws Exception {
        //静态加载
        //这里创建一个没有加载的类对象，再javac编译的过程中会出错，无法编译生成class文件
//        Dog dog = new Dog();

        //动态加载
        //只有调用该方法的时候才会报错
        Class<?> dog = Class.forName("Dog");

        // TODO 注意
        //  类名.class获取类对象的方式不会加载对应类
    }
}

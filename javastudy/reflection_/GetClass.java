package javastudy.reflection_;

import java.lang.reflect.Constructor;

/**
 * 演示得到指定类的Class对象的几种方法
 */
public class GetClass {
    public static void main(String[] args) throws Exception {
        String className="javastudy.reflection_.Example";

        //1、通过Class.forName()方法
        //前提：已知一个类的全类名
        //多用于配置文件，读取类全路径，加载类
        Class<?> aClass1 = Class.forName(className);

        //2、通过 类名.class 得到
        //前提：已知具体的类
        //该方法最为安全可靠，程序性能最高
        //多用于参数传递，比如通过反射得到对应的构造器对象
        Class<Example> aClass2 = Example.class;
        //应用：
        Constructor<?> constructor = aClass1.getConstructor(int.class);//含参构造器
        //静态同步方法

        //3、对象.getClass()
        //用于有对象实例
        Example example = new Example();
        Class<? extends Example> aClass3 = example.getClass();
        System.out.println(example.getClass());//这个是调用example的Class对象的toString方法，返回该类的全类名
        System.out.println(example.getClass().getClass());//这个是Class对象的全类名：class java.lang.Class

        //4、通过类加载器(有4种)来获取
        //(1)先得到类加载器
        ClassLoader classLoader = example.getClass().getClassLoader();
        //(2)通过类加载器得到对象
        Class<?> aClass4 = classLoader.loadClass(className);

        //另外：
        //基本数据类型可以通过 基本数据类型.class 获取
        Class<Integer> integerClass = int.class;    //基本数据类型会进行包装
        Class<Character> characterClass = char.class;
        Class<Byte> byteClass = byte.class;

        //基本数据类型对应的包装类可以通过 类型名.type 获取
        Class<Integer> integerClass1 = Integer.TYPE;    //表示原始类型int的Class实例
        Class<Integer> integerClass2 = Integer.class;//当然 .class 也可以
        Class<Character> characterClass1 = Character.TYPE;
        //注意包装类的.class是获取包装类的Class对象，而包装类的.TYPE是获取对应的基本数据类型的Class对象，两个对象不相等
        System.out.println((integerClass==integerClass1)+" "+(integerClass==integerClass2));//true false
    }
}

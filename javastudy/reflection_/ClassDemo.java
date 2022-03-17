/*
Class
    1、Class也是类，继承Object
    2、Class类对象不是new出来的，而是系统创建的
    3、对于某个类的Class类对象，在内容中只有一份，因为类只加载一次
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            return loadClass(name, false);
        }
        调用ClassLoader的loadClass()方法加载类
        参数String name就是类的全名，像javastudy.reflection_.Example
    4、每个类的实例都会记得自己是由哪个Class实例所产生
    5、通过Class实例可以得到一个类的完整结构
    6、Class对象是存放在堆中的
    7、类的字节码二进制数据，是在方法区中的，有的地方称为类的元数据
    8、因为方法区是被所有线程共享的，所以必须考虑数据的线程安全。假如两个线程都在试图找lava的类，在lava类还没有被加载的情况下，只应该有一个线程去
       加载，而另一个线程等待
*/
package javastudy.reflection_;

public class ClassDemo {
    public static void main(String[] args) throws Exception {
        String className="javastudy.reflection_.Example";

        //第一次创建该对象会创建该类的Class对象(调用ClassLoader的loadClass()方法加载类)，如果这里new了，下面就不会再创建了(不会再调用)
//        Example example = new Example();

        Class<?> aClass = Class.forName(className); //进行类加载，创建该类的Class对象，如果已经创建就不会再创建了
        Class<?> aClass1 = Class.forName(className);
        //可以看出同一个类的Class对象只有一个
        System.out.println(aClass1==aClass);    //true
        System.out.println(aClass.hashCode()+" "+aClass1.hashCode());   //668386784 668386784


    }
}

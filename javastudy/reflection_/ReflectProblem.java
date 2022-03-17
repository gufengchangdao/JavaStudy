/*
反射机制

 */
package javastudy.reflection_;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

public class ReflectProblem {
    public static void main(String[] args) throws Exception {
        //读取配置文件信息
        String filepath="src\\javastudy\\reflection_\\co.properties";       //配置文件路径
        Properties properties = new Properties();
        properties.load(new FileInputStream(filepath));         //读取
        String className = properties.getProperty("className"); //key
        String methodName=properties.getProperty("methodName"); //key
        String nameField = properties.getProperty("nameField");

        //加载类，返回Class对象
        Class aClass = Class.forName(className);

        //通过aClass得到加载的类的实例
        Object object = aClass.getDeclaredConstructor().newInstance();
        System.out.println("Object的运行类型为: "+ object.getClass() );//class javastudy.reflection_.Example，运行类型正是加载类的类型

        //通过aClass得到加载的类的 "methodName" 方法对象
        Method method = aClass.getMethod(methodName);
        //通过method调用方法，传统：对象.方法()，反射机制：方法对象.invoke(对象)
        method.invoke(object);

        //getField不能获取私有属性
        Field name = aClass.getField(nameField);
        System.out.println(name.get(object));

        //构造器
        Constructor constructor = aClass.getConstructor();  //返回无参构造器
        Constructor constructor1 = aClass.getConstructor(int.class);    //有参构造器

        //反射调用优化
        //setAccessible是启动和禁止访问安全检查的开关，true表示反射的对象在使用时取消访问检查，提高反射的效率(使用效率稍微提高一些)
        //不会改变成员的访问权限
        method.setAccessible(true);
        name.setAccessible(true);
        constructor.setAccessible(true);

    }
}

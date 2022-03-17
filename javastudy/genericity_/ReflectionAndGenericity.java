package javastudy.genericity_;

import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

/**
 * 反射和泛型
 */
public class ReflectionAndGenericity {
    /**
     * 泛型Class类及其方法
     */
    @Test
    public void test01() throws Exception {
        // Class类就是泛型的，String.class就是一个Class<String>的对象(还是唯一的)
        Class<String> aClass = String.class;

        // 泛型构造器，Constructor<T> getConstructor(Class<?>... parameterTypes)
        Constructor<String> constructor = aClass.getConstructor();

        // T newInstance(Object ... initargs)
        String str = constructor.newInstance();

        // T cast(Object obj) 可以把obj转为T型返回强制类型转换后的T类型或null(如果obj为null的话)，如果不能转就会抛出异常
        String cast = aClass.cast(45);

        // T[] getEnumConstants() ,如果T是枚举类型就会返回所有值组成的数组，否则返回null
        String[] enumConstants = aClass.getEnumConstants();

        // TODO 可以说泛型的使用避免了类型转换，放方法返回的类型具有特定性
    }

    /**
     * 使用Class<T>参数进行类型匹配，利用Class类的T类型来匹配方法中声明的T类型
     */
    @Test
    public void test02() {
        String[] instance = getInstance(String.class);
        System.out.println(instance.getClass().getName()); //[Ljava.lang.String; 看出是String数组
    }

    public static <T> T[] getInstance(Class<T> c) {
        return (T[]) Array.newInstance(c, 2);
    }


}

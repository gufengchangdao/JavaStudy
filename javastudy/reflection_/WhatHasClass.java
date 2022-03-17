package javastudy.reflection_;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * 哪些类型可以有Class对象
 */
public class WhatHasClass {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //外部类、成员内部类、静态内部类、局部内部类、匿名内部类
        Class<String> stringClass = String.class;
        Class<A> aClass2 = A.class;

        //接口
        Class<Serializable> serializableClass = Serializable.class;

        //数组
        Class<long[]> aClass = long[].class;
        Class<long[][]> aClass1 = long[][].class;

        //枚举
        Class<Thread.State> stateClass = Thread.State.class;

        //注解
        Class<Deprecated> deprecatedClass = Deprecated.class;   //废弃注解
        Class<SuppressWarnings> suppressWarningsClass = SuppressWarnings.class; //抑制警告注解

        //基本数据类型
        Class<Integer> integerClass = int.class;

        //void
        Class<Void> voidClass = void.class;
    }
}
class A{

}
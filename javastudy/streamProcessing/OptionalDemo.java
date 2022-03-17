/*
Optional类
(1) Optional类是一个容器，可以保存任何对象，对空指针也做了优化，在不确认对象是否为null的场景下很好用
(2) 该类用final修饰，不可继承
*/

package javastudy.streamProcessing;

import java.util.Optional;

public class OptionalDemo {
    public static void main(String[] args) {
        // 创建Optional类实例
        Optional<String> str = Optional.of("一个字符串");
        Optional<Object> empty = Optional.empty();  //返回一个空值的对象

        // 判断和get方法
        System.out.println("两个对象是否为null？"+str.isPresent()+"  "+!empty.isEmpty());//两个方法正好反过来了

        System.out.println("获取对象中的值："+str.get());   //value为null时会报错，一般会先判断

        str.ifPresent(System.out::println); //如果str的value属性不为null就会执行括号里的方法，这里还用了lambda表达式的静态引用

        System.out.println("如果对象值为null，就会使用默认值："+str.orElse("这是默认值"));//存在就输出，为null时就会使用默认值
        System.out.println("如果对象值为null，就会使用默认值："+empty.orElse("这是默认值"));



    }
}

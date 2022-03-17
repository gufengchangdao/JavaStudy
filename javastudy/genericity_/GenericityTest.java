package javastudy.genericity_;

import java.io.Serializable;

/**
 * 泛型的简单使用
 */
// TODO 泛型类
// TODO java库使用E表示集合中元素类型，K和V表示键和值，T(或者U和S)表示“任意类型”
public class GenericityTest<T> {
    public T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    // TODO 泛型方法
    public <S> S test02(S value1, S value2) {
        return null;
    }

    // TODO 类型变量的限定
    // 限定类型用 & 分隔
    public <S extends Comparable & Serializable> S test03(S[] a) {
        return null;
    }

    public static void main(String[] args) {
        // TODO 通配符
        // 这个引用的变量不能使用set()方法，就是把一个数据作为T类型参数赋值给对象中的T类型字段，这是不允许的
        // 不然Comparable的子类都能赋值给T类型字段，JAVA不允许，但是可以这样调用 test01.setValue(null);
        GenericityTest<? extends Comparable> test01 = new GenericityTest<String>();
        GenericityTest<? extends Comparable<String>> test02;
        // test01.setValue("13"); //String是实现Comparable的，但是不能把Comparable的子类赋给String字段
        test01.setValue(null);

        //
        GenericityTest<? super Comparable> test03 = new GenericityTest<Object>();
        // test03.setValue(new Object()); //set()不能使用
        test03.getValue();

        // 无限定通配符，跟原始调用不同，get方法会返回Object，不能使用set()方法(不能把对象赋给一个?类型)
        GenericityTest<?> test04;

        // TODO 总结：不能调用 ? 作为参数的方法
        // 解决方法，如果是函数式表达式的话可以进行强转后调用，比如Function<?, String> 转为 Function<Object, String>就可以调用了
    }

    // 使用数组类型Comparable方法的类型，比如test04(new String[]{"dsd"})使用String类型
    public static <T extends Comparable<T>> T test04(T[] a) {
        return null;
    }

    // 这个方法有两个要求：
    // 1. T数组类型实现了Comparable接口
    // T是数组类型的Comparable()方法泛型类型或其子类
    public static <T extends Comparable<? super T>> T test05(T[] a) {
        return null;
    }
}
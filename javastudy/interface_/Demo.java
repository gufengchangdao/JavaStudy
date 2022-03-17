/*
interface
    1. 接口就是给出一些没有实现的方法，封装到一起，到某个类要使用的时候，再根据具体情况把这些方法写出来
    2. 接口的所有抽象方法必须实现，不过抽象类实现接口不用实现其方法
    3. 方法一般不能有主体，但是jdk8以后，可以通过以下方法拥有主体
        可以有默认实现方法 default
        可以有静态方法    static
        不过通常的做法一般都是将静态方法放在伴随类中，比如Collection/Collections、Path/Paths、Array/Arrays
    4. 接口所有属性、方法都是public的，属性还是public final static 的，因为可以直接调用
    5. 一个类可以实现多个接口
    6. 一个接口不能继承类，但是可以继承多个别的接口，注意可以是多个
 */
package javastudy.interface_;

public class Demo implements A {
    //实现接口方法
    @Override
    public void cry() {

    }

    //重写方法，静态方法无法重写
    @Override
    public void fly() {
        A.super.fly();
    }

    public static void main(String[] args) {
//        A.name="狐狸";会报错
        System.out.println(A.name);
    }
}

interface A {
    //加了public是冗余的，final修饰的，必须赋值
    public String name = "小狗狗";

    //抽象方法可以省略abstract关键字，默认就是抽象的
    void cry();

    /* 默认实现方法
     * 默认方法的一个重要作用就是“接口演化”，当接口中的方法更新时，便无法兼容原代码，将代码设置为默认方法便可以解决问题
     * */
    default void fly() {
        System.out.println("飞不起来的");
    }

    //静态方法
    static void play() {
        System.out.println("这个可以有");
    }
}

interface B {
}

//同时继承了A和B
interface C extends A, B {
}

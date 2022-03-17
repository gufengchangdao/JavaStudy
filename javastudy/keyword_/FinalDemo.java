/*
final
    1.final会用到的时候
        不希望类被继承时
        不希望父类的某个方法被子类重写时
        不希望父类的某个属性值被修改时
        不希望某个局部变量被修改时
    2.final修饰的属性名一般大写且使用 _ 分割，像 MY_GAME
    3.final修饰的属性在定义时，必须赋初值，并且以后不能再修改，但是原生方法(native)可以绕过JAVA语言的访问控制机制。赋值可以在如下位置：
        定义时
        在构造器中
        在代码块中
    4.如果final是静态的，则赋值只能在如下位置：
        定义时
        在静态代码块中
    5.如果类不能final的，但是含有final方法，虽然该方法不可以重写，但是可以被继承
    6.一个类已经是final的了，就没必要把属性、方法也设为final了
    7.final和static搭配使用，效率更高
    8.包装类(Integer、Double、Float、Boolean等)，String 都是final类
 */

package javastudy.keyword_;

public class FinalDemo {
    //1 非静态属性
    //(1) 定义时赋值
    public final String game="红色警戒2";
    //(2) 代码块中赋值
    public final String game2;
    {
        game2="红色警戒2";
    }
    //(3) 构造器中赋值
    public final String game3;
    public FinalDemo(String game3) {
        this.game3 = game3;
    }

    //2 静态属性
    //(1) 定义时赋值
    public final static String game4="红色警戒2";
    //(2) 代码块中赋值
    public final static String game5;
    static {
        game5="红色警戒2";
    }

}

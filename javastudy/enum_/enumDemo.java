package javastudy.enum_;

import javastudy.utilities.ReflectionUtils;

public class enumDemo {
    /*
    Enum
    1. 每个枚举类都继承Enum类，并且都是final修饰的，无法继承其他类
    2. 每个枚举实例都是唯一的，即便是使用序列化深度拷贝也不会创建第二个实例
     */
    public static void main(String[] args) {
        //根据定义顺序进行比较
        System.out.println(Food.FISH.compareTo(Food.CAKE));//-3

        System.out.println(Food.FISH.equals(Food.FISH));//true

        System.out.println(Food.FISH+" "+Food.FISH.name());//FISH FISH
        //返回此枚举常数的序数
        System.out.println(Food.EGG.ordinal());//2
        // 返回枚举值的数组，该方法不是Enum类的，是编译器给Food加上的
        Food[] values = Food.values();

    }
}

enum Food{
    //枚举类型必须写在最前面 ，相当于 public final static FISH=new Food("最喜欢的");
    //每一个枚举类型都有属于自己的属性，他们之前的属性不共享
    FISH("最喜欢的"),EGG(2),BANANA,CAKE;    //括号内的相当于创建对象时传入的参数

    public String fev;
    public int pri;

    //构造方法，枚举的构造器总是private的，不能改变
    Food() {}

    Food(String fev) {
        this.fev = fev;
    }

    Food(int pri) {
        this.pri = pri;
    }

}




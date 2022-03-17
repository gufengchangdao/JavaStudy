package javastudy.genericity_;

import java.time.LocalDate;
/*
TODO JAVA泛型的转换总结
    1. 虚拟机没有泛型，只有普通的类和方法
    2. 所有的类型参数都会替换为他们的限定类型
    3. 会合成桥方法来保持多态
    4. 为保持类型安全性，必要时会插入强制类型转换

TODO 类型擦除：
1. 类型擦除的正当理由是从非泛型代码到泛型代码的转变过程，在不破坏现有类库的情况下，将泛型融入java语言
2. 无论何时定义一个泛型类型，都会自动提供一个相应的原始类型。
3. 原始类型用第一个限定来替换类型变量，或者如果没有给定限定，就会替换为Object 比如`<S extends Comparable>`替换为Comparable
4. 如果是`<S extends Comparable & Serializable>`，就会替换为Serializable类型，并且在必要的地方插入强制类型转换为Comparable，
   为了提高效率，应该将标签接口放在限定列表的末尾
5. Java泛型的处理几乎都在编译器中进行，编译器生成的bytecode是不包涵泛型信息的，泛型类型信息将在编译处理是被擦除，这个过程即类型擦除。
6. 通常情况下，一个编译器处理泛型有两种方式：
    1.Code specialization。在实例化一个泛型类或泛型方法时都产生一份新的目标代码（字节码or二进制代码）。例如，针对一个泛型list，可能需要 针对string，integer，float产生三份目标代码。
    2.Code sharing。对每个泛型类只生成唯一的一份目标代码；该泛型类的所有实例都映射到这份目标代码上，在需要的时候执行类型检查和类型转换。
    JAVA采用的就是第二种
7. 类型擦除可以简单的理解为将泛型java代码转换为普通java代码，只不过编译器更直接点，将泛型java代码直接转换成普通java字节码。

 */

/**
 * 桥方法的应用 ———— 保持多态
 */
public class TypeErasure {
    public static void main(String[] args) {
        DateInterval interval = new DateInterval();
        Pair<LocalDate> pair = interval;
        pair.setSecond(LocalDate.now());

    }
}

class Pair<T> {
    private T first;
    private T second;
    public Pair() {}
    public Pair(T first, T second) {this.first = first;this.second = second;}
    public T getFirst() {return first;}
    public void setFirst(T first) {this.first = first;}
    public T getSecond() {return second;}
    public void setSecond(T second) {this.second = second;}
    // 擦除类型后，所有的T都会替换为Object
}

class DateInterval extends Pair<LocalDate> {
    @Override
    public void setSecond(LocalDate second) {
        if (second.compareTo(getFirst()) >= 0)
            super.setSecond(second);
    }
    /* TODO 但是这里擦除类型后就会出现问题
    上面的方法变为
        public void setSecond(LocalDate second) {...}
    但是从父类那里继承而来的还有
        public void setSecond(Object second) {...}
    这是两个不同的方法，在引用类型为Pair而实际类型为DateInterval的对象调用setSecond()，会调用哪个方法呢？
    我们希望这个调用能具有多态性，会调用子类的方法，为了解决这个问题，编译器会在DateInterval类中生成一个桥方法，为
        public void setSecond(Object second) {
            setSecond((LocalDate) second);
        }
    在多态中就会调用这个setSecond(Object second)方法，这个方法就会调用我们重写的setSecond(LocalDate second)方法，保持了多态
    */

    @Override
    public LocalDate getSecond() {
        return super.getSecond();
    }
    /*
    在DateInterval中有两个getSecond()方法，
        LocalDate getSecond()
        Object getSecond()
    在虚拟机中会有参数类型和返回类型共同指定一个方法，因此编译器可以为两个仅返回类型不同的方法生成字节码，虚拟机能正常处理
    */

}
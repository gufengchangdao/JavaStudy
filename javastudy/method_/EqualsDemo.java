package javastudy.method_;

import java.util.Arrays;
import java.util.Objects;

/**
 * 几种equals()方法的重写
 */
public class EqualsDemo {
    public int len;

    // TODO 方式一、先调用父类比较方法，再对本类属性进行比较，如果有超类中写了就很适用
    // public boolean equals(Object obj) {
    //     if (!super.equals(obj)) return false;
    //     return len == ((EqualsDemo) obj).len;
    // }

    // TODO 方式二、使用getClass()进行比较，这是编译器自动生成的也是最普遍的equals方法
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EqualsDemo that = (EqualsDemo) o;
        return len == that.len;
    }

    // TODO 方式三、使用instanceOf进行比较，即便o为null也只是返回false，不会报错。
    //  但是使用instanceOf不符合java规范的对称性，即有a.equals(b)但可能没有b.equals(a)
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (!(o instanceof EqualsDemo)) return false;
    //     EqualsDemo that = (EqualsDemo) o;
    //     return len == that.len;
    // }

    // TODO 方式四、捕获ClassCastException异常
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     EqualsDemo that = null;
    //     try {
    //         that = (EqualsDemo) o;
    //     } catch (ClassCastException e) {
    //         e.printStackTrace();
    //     }
    //     return len == that.len;
    // }

    // TODO 方式五、啥也不做
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     EqualsDemo that = null;
    //     try {
    //         that = (EqualsDemo) o;
    //     }catch (ClassCastException ignored){}
    //     return len == that.len;
    // }

    public static void main(String[] args) {
        // 判断对象是否相等
        Object a=null;
        Object b=null;
        System.out.println(Objects.equals(a,b)); //可以不用判断对象是否为null，但是我往往希望方法的调用者不是null

        // 判断数组是否相等
        int[] aa=null;
        int[] bb=null;
        System.out.println(Arrays.equals(aa,bb));

    }
}

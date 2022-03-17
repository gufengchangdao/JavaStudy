package javastudy.method_;

import java.util.Objects;

/**
 * Objects伴生类的一些静态方法使用
 */
public class ObjectsDemo {
    public static void main(String[] args) {
        // TODO requireNonNullElse
        Test test = null;
        // test为空不为空后面的表达式都会被计算
        Objects.requireNonNullElse(test, new Temp());
        // 懒计算：test为空后面表达式才会被计算
        Objects.requireNonNullElseGet(test, Temp::new);


    }
}

class Test {
    public Test() {
        System.out.println("构造函数执行");
    }
}

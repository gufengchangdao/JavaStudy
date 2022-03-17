package javastudy.innerclass_.skill;

/**
 * 静态方法输出外围类名和方法
 */
public class StaticMethodToString {
    public static class MyClass {
        public static String printLog() {
            // TODO 首先创建一个Object匿名子类的一个匿名对象，然后用getEnclosingClass()获取该对象的外围对象，即包含这个方法的对象
            return new Object(){}.getClass().getEnclosingClass().getName();
            // return new Object(){}.getClass().getEnclosingMethod().getName();
        }
    }

    public static void main(String[] args) {
        System.out.println(MyClass.printLog());
    }
}

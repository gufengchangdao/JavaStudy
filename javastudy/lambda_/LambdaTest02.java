package javastudy.lambda_;

/**
 * 方法引用测试
 */
public class LambdaTest02 {
    /*
    *TODO 方法引用
    * object::instanceMethod
    * Class::instanceMethod
    * Class::staticMethod
    * 要用::运算符分割方法名和对象或类名
    * 可以在方法引用中使用this和super
    * */
    private static void check01() {
        System.out.println("测试用的方法");
    }

    private void check02() {
        System.out.println("测试用的方法");
    }

    private static <T> int check03(T t) {
        return (int) t;
    }

    public LambdaTest02() {
        System.out.println("调用无参构造器");
    }
    public LambdaTest02(int i) {
        System.out.println("调用有参构造器");
    }
    public static void main(String[] args) {
        // 1. Class::staticMethod，静态引用其他类的方法
        Test01 test01 = LambdaTest02::check01;

        // 2. object::instanceMethod，引用成员方法，使用对象名
        Test01 test02 = new LambdaTest02()::check02;

        // 3. 使用带泛型的方法
        Test02<Integer> test03 = LambdaTest02::check03;

        // 4. 引用构造方法，无参数还是有参数是根据接口方法参数结构对应的构造器来说的
        // 引用无参构造方法
        Test03 test04= LambdaTest02::new;
        // Demo02 instance = test04.getInstance(); //此时test04的getInstance方法就相当于Demo02的构造器
        // 引用有参构造方法
        Test04 test05= LambdaTest02::new;
        // Demo02 instance1 = test05.getInstance(5);
        // 引用数组构造方法
        Test05<LambdaTest02[]> arr= LambdaTest02[]::new;
        LambdaTest02[] array =arr.getInstance(2); //接口创建数组，并指定数组个数
        array[0]=new LambdaTest02();
        array[1]=new LambdaTest02();

    }
}

interface Test01 {
    void print();
}

interface Test02<T> {
    int print(T t);
}
interface Test03{
    LambdaTest02 getInstance();
}
interface Test04{
    LambdaTest02 getInstance(int x);
}
interface Test05<T>{
    T getInstance(int num);
}

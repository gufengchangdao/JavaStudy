package javastudy.keyword_;

/**
 * 方法参数
 */
public class MethodParameter {
    public static void main(String[] args) {
        // java语言总是采用按值调用，方法得到的是所有参数值的一个副本,不管是基本数据类型还是对象引用
        // 跟c++不同，c++可以按值调用，也可以按引用调用

        Integer integer=10;
        new MethodParameter().test(integer);
        System.out.println(integer); //10
        // 这是输出10是因为Integer的value是final的，是不可变的，方法的赋值只是基本数据类型装箱后再赋值给那个副本，改变了副本的引用，除此之外，
        // 包装类都是不可变的
    }
    private void test(Integer integer){
        integer=15;
    }
}

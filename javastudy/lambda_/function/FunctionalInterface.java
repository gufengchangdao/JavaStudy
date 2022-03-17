package javastudy.lambda_.function;

import java.util.function.*;

/**
 * 常用函数式接口的使用
 * 使用这些特殊化接口比使用通用接口更高效
 */
public class FunctionalInterface {
    public static void main(String[] args) {
        test01(() -> System.out.println("Runnable:你好"));
        test02(() -> "Supplier:你好");
        test03(System.out::println);
        test04((str1, str2) -> System.out.println(str1 + str2));


        // TODO 指定参数类型的函数式接口免去的装箱拆箱带来的消耗
        Predicate<Integer> predicate = integer -> true;
        IntPredicate intPredicate = ints -> true;
        DoublePredicate doublePredicate = doubles -> true;

        LongBinaryOperator longBinaryOperator = (left, right) -> (left + right) / 2;

        IntFunction<Integer> intFunction = ints -> ints;
    }

    // TODO Runnable接口，无参数无返回值
    public static void test01(Runnable action) {
        action.run();
    }

    // TODO Supplier接口，无参数有一个T返回值
    public static void test02(Supplier<?> action) {
        System.out.println(action.get());
    }

    // TODO Consumer接口，一个参数，无返回值
    public static void test03(Consumer<String> action) {
        action.accept("Consumer:你好");
    }

    // TODO BiConsumer接口，两个参数，无返回值
    public static void test04(BiConsumer<String, String> action) {
        action.accept("BiConsumer:", "你好");
    }
    // 剩下的懒得写了
}

// FunctionalInterface为函数式接口的注释
@java.lang.FunctionalInterface
interface GetSum<K> {
    K sum(K num1, K num2);
}
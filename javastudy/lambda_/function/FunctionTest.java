/*
Function接口
    1. 在之前的实例中，想要使用lambda表达式要么先创建或调用已有的函数式接口
    2. java.util.function包下面吗已经提供了很多预定义函数式接口，就是没有实现任何功能，仅用来封装lambda表达式的对象
    3. 下面这个Function就是有一个参数值和一个返回值的apply函数，还有其他函数(不会用)
*/

package javastudy.lambda_.function;

import java.util.function.Function;

/**
 * 演示Function接口使用
 */
public class FunctionTest {
	public static void main(String[] args) {
		// TODO 一般使用
		// 创建Function接口函数，参数类型是String(第一个)，返回类型是String(第二个)
		Function<String, String> function = (n) -> "我不喜欢" + n;

		//静态引用的方式赋值
		Function<Integer, String> function1 = String::valueOf;
		String str = function1.apply(500);

		// 调用该对象存储的方法
		System.out.println(function.apply("吃海鲜"));
		//R apply(T t); 是Function接口中的方法

		System.out.println(Function.identity().apply(50));
		// identity()为静态方法，只会返回一个Function对象，该对象的apply()方法只会返回参数值

		// TODO 函数复合
		Function<Integer, Integer> f1 = x -> x + 1;
		Function<Integer, Integer> f2 = x -> x * 2;
        Function<Integer,Integer> f3 = f1.andThen(f2); //先执行函数f1，将结果带入到f2中,g(f(x))
        Function<Integer,Integer> f4 = f1.compose(f2); //先执行f2,再执行f1,f(g(x))
	}
}

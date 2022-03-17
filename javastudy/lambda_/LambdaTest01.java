/*
lambda表达式
1. lambda表达式可以用非常少的代码实现抽象方法
2. lambda表达式不能独立执行，必须实现函数式接口(只有一个方法的接口)，并且会返回一个函数式接口的对象
3. lambda表达式可以实现匿名内部类的效果，但是和匿名内部类的创建方式不同，并且相同的匿名内部类的对象的哈希值不同，相同的lambda表达式创建的对象哈希值相同
4. lambda就是闭包
5. 有一条规则是lambda表达式中捕获的变量必须实际上是事实最终变量，即变量初始化后就不再为他赋值，
    解决方法：在使用lambda外面创建新的引用需要用到的变量，这样可以保证是“final”的，没有改变过的
6. 注意，在lambda中箭头函数直接跟表达式的话，注意 v++ 和 ++v 的区别, 前者是不会被计算的
*/
package javastudy.lambda_;

/**
 * 演示lambda的基本使用
 */
public class LambdaTest01 {
    public static void main(String[] args) {
        // 1. 实现无参数抽象方法
        Action action = () -> "我想吃东坡肉";
        System.out.println(action.eat());

        // 2. 实现有参数抽象方法
        // Calc calc= Integer::sum;    //引用Integer的静态方法
        Calc calc = (x, y) -> x + y;
        System.out.println(calc.add(5, 10));

        // 3. 表达式使用代码块
        Check check = (n) -> {
            if (n >= 60) return "你及格了";
            else return "你不及格";
        };
        System.out.println(check.check(100));

        // 4. 表达式修改变量
        int num = 10;
        // num=1; 重新为变量赋值后，lambda再引用就会报错
        final int num2 = 20;
        Calc calc1 = (int a, int b) -> {
            // num=12;  //lambda表达式无法修改局部变量，这里会报错，但是lambda表达式如果在类中定义的，可以修改类中属性值
            return num;
        };

        // 5. 表达式不能使用被改变过的引用变量
        for (int i = 0; i < 10; i++) {
            int finalI = i; //保证 i 是“final”的，没有改变过的
            Calc calc2 = (int a, int b) -> {
                // 如果lambda表达式引用一个在外部改变的变量是不合法的
                // return i;  //报错
                return finalI;
            };
        }

        // 6. 表达式中使用this时，this指的是创建lambda表达式的方法的this参数

    }
}

// 函数式接口：仅包含一个抽象方法的接口，如果不符合规范，这样的接口不能用lambda表达式创建匿名对象
interface Action {
    String eat();
}

interface Calc {
    int add(int a, int b);
}

interface Check {
    String check(int a);
}
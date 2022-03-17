package javastudy.reflection_.proxy;

import java.lang.reflect.*;
import java.util.*;

/**
 * This program demonstrates the use of proxies.
 *
 * @author Cay Horstmann
 * @version 1.01 2018-04-10
 */
public class ProxyTest {
    public static void main(String[] args) {
        var elements = new Object[1000];

        // fill elements with proxies for the integers 1 . . . 1000
        for (int i = 0; i < elements.length; i++) {
            Integer value = i + 1;
            var handler = new TraceHandler(value);
            // 三个参数为类加载器、存有元素需要实现接口的Class对象数组，调用处理器
            Object proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Comparable.class}, handler);
            // 数组中填充代理对象
            elements[i] = proxy;
        }

        // construct a random integer
        Integer key = new Random().nextInt(elements.length) + 1;

        // 程序中本来调用的Integer中的compareTo方法其实调用了代理对象处理的invoke方法
        int result = Arrays.binarySearch(elements, key);

        // print match if found
        if (result >= 0) System.out.println(elements[result]);
    }
}

/**
 * 打印所调用方法的参数和名字, 随后调用用包装的对象作为隐式参数调用这个方法
 */
class TraceHandler implements InvocationHandler {
    private Object target; /*目标类对象*/

    /**
     * Constructs a TraceHandler
     *
     * @param t the implicit parameter of the method call
     */
    public TraceHandler(Object t) {
        target = t;
    }

    /**
     * 调用原方法
     *
     * @param proxy jdk创建的代理对象
     * @param m     目标类中的方法
     * @param args  方法参数
     * @return 运行结果
     */
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        // 打印目标对象，target.toString()
        System.out.print(target);
        // 打印目标类要调用的方法，compareTo
        System.out.print("." + m.getName() + "(");
        // 打印方法的显示参数
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                System.out.print(args[i]);
                if (i < args.length - 1) System.out.print(", ");
            }
        }
        System.out.println(")");

        // 调用目标类的方法
        return m.invoke(target, args);
    }
}

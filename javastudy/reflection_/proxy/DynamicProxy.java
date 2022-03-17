package javastudy.reflection_.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
1. 步骤：
    + 创建一个公共接口(必要的)和目标类
    + 创建代理类对象并调用接口方法
*/

/**
 * 演示动态代理，底层使用反射来完成的
 */
public class DynamicProxy {
    public static void main(String[] args) {
        // 1. 创建目标对象
        UsbSell manufacturer = new Manufacturer();

        // 2. 创建MySellHandler对象
        MySellHandler handler = new MySellHandler(manufacturer);

        // 3. 创建代理类对象
        //      (1) MySellHandler不是代理类，Proxy会在内存中生成一个代理类
        //      (2) 创建的proxy对象类名不是Proxy，但是扩展于Proxy
        //      (3) jdk为我们的生成了一个叫$Proxy0（这个名字后面的0是编号，有多个代理类会一次递增）的代理类，这个类文件时放在内存中的，我们
        //          在创建代理对象时，就是通过反射获得这个类的构造方法，然后创建的代理实例。
        UsbSell proxy = (UsbSell) Proxy.newProxyInstance(manufacturer.getClass().getClassLoader(),/*目标类的类加载器*/
                manufacturer.getClass().getInterfaces(),/*目标类实现的接口类对象数组*/
                // new Class[]{UsbSell.class},
                handler/*实现InvocationHandler接口对象*/);

        // 4. 通过代理执行方法
        System.out.println(proxy.sell(1));

        // 判断是否为代理类
        System.out.println(Proxy.isProxyClass(MySellHandler.class)); //false
        System.out.println(Proxy.isProxyClass(proxy.getClass())); //true
    }
}

class MySellHandler implements InvocationHandler {
    private Object target = null;

    // 目标对象是活动的，需要传进来，传入对象是谁，就给谁代理
    public MySellHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 代理类调用目标方法
        Object price = method.invoke(target, args);

        // ---- 增强功能 ----
        System.out.println("商家给你购物优惠卷");
        if (price != null) {
            price = (float) price + 25;
        }
        // -----------------

        return price;
    }
}
package javastudy.genericity_;

import javastudy.utilities.tuple.Tuple;
import javastudy.utilities.tuple.TwoTuple;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态代理实现混型
 * 混型：混合多个类的能力，以产生一个可以表示混型中国所有类型的类，使组装多个类更简易
 * 可以使用组合、多重继承或是代理来实现混型，这几个方法都需要创建接口
 */
public class DynamicProxyMixin {
	public static void main(String[] args) {
		// 代理类对象
		Object proxy = MixinProxy.newInstance(Tuple.tuple(new Imp1(), Interface1.class),
				Tuple.tuple(new Imp2(), Interface2.class),
				Tuple.tuple(new Imp3(), Interface3.class));
		Interface1 proxy1 = (Interface1) proxy;
		Interface2 proxy2 = (Interface2) proxy;
		Interface3 proxy3 = (Interface3) proxy;
		proxy1.f1();
		proxy2.f2();
		proxy3.f3();
	}
}

class MixinProxy implements InvocationHandler {
	/**
	 * 被代理对象集合，String为方法名，Object为目标对象
	 */
	Map<String, Object> delegatesByMethod;

	// 填充delegatesByMethod集合
	public MixinProxy(TwoTuple<Object, Class<?>>... pairs) {
		delegatesByMethod = new HashMap<String, Object>();
		for (TwoTuple<Object, Class<?>> pair : pairs) {
			for (Method method : pair.second.getMethods()) {
				String methodName = method.getName();
				if (!delegatesByMethod.containsKey(methodName))
					delegatesByMethod.put(methodName, pair.first);
			}
		}
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getName();
		Object delegate = delegatesByMethod.get(methodName);
		return method.invoke(delegate, args);
	}

	/**
	 * 创建代理类对象
	 *
	 * @param pairs [目标类, 接口类]二元组
	 * @return 代理类对象
	 */
	@SuppressWarnings("unchecked")
	public static Object newInstance(TwoTuple<Object, Class<?>>... pairs) {
		Class<?>[] interfaces = new Class[pairs.length];
		for (int i = 0; i < pairs.length; i++) {
			interfaces[i] = pairs[i].second;
		}
		ClassLoader cl = pairs[0].first.getClass().getClassLoader();
		return Proxy.newProxyInstance(cl, interfaces, new MixinProxy(pairs));
	}
}

// 所有接口
interface Interface1 {
	void f1();
}

interface Interface2 {
	void f2();
}

interface Interface3 {
	void f3();
}
// 所有实现类
class Imp1 implements Interface1 {
	public void f1() {
		System.out.println("接口一的方法");
	}
}

class Imp2 implements Interface2 {
	public void f2() {
		System.out.println("接口二的方法");
	}
}

class Imp3 implements Interface3 {
	public void f3() {
		System.out.println("接口三的方法");
	}
}
package javastudy.reflection_;

import java.lang.reflect.Field;
import java.security.Permission;

/**
 * 演示如何禁止反射
 */
public class ForbidReflect {
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
		SystemClass main = new SystemClass();
		Field s = main.getClass().getDeclaredField("s");
		s.setAccessible(true);
		s.set(main, "新值");
		System.out.println(s.get(main));
	}
}

// 目标类
class SystemClass {
	static {
		try {
			System.setSecurityManager(new MySecurityManager());
		} catch (SecurityException se) {
			System.out.println("SecurityManager already set!");
		}
	}

	private String s = "privateValue";
}

class MySecurityManager extends SecurityManager {
	@Override
	public void checkPermission(Permission perm) {
		if (perm.getName().equals("suppressAccessChecks")) {
			throw new SecurityException("Can not change the permission dude.!"); //通过抛异常来阻止反射
		}
	}
}

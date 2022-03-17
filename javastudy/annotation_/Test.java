package javastudy.annotation_;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 通过反射来读取Record类的注释信息
 */
public class Test {

	public static void main(String[] args) throws ClassNotFoundException {
		Class recordC = null;
		recordC = Class.forName("javastudy.annotation_.Record");

		System.out.println("----------- 构造方法的描述如下 -----------");
		Constructor[] constructors = recordC.getDeclaredConstructors();
		for (Constructor constructor : constructors) {
			//如果该构造方法拥有指定类型的注释
			if (constructor.isAnnotationPresent(Construct_annotation.class)) {
				// 获得指定类型的数据
				Construct_annotation ca = (Construct_annotation) constructor.getAnnotation(Construct_annotation.class);
				System.out.println(ca.value()); //获得注释信息
			}

			// 获得该构造方法参数的注释，二维数组对应每个参数的每个注释，如果无参构造方法就是空数组
			Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
			for (Annotation[] i : parameterAnnotations) {
				if (i.length == 0) System.out.println("未添加Annotation的参数");    //该参数无注释
				else
					for (Annotation annotation : i) {
						Field_Method_Parameter_Annotation pa = (Field_Method_Parameter_Annotation) annotation;
						System.out.println(pa.describe());  //获取自定义注释两个方法的值
						System.out.println(pa.type());
					}
			}
			System.out.println();
		}

		System.out.println("----------- 字段的描述如下 -----------");
		Field[] fields = recordC.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Field_Method_Parameter_Annotation.class)) {
				Field_Method_Parameter_Annotation fa = field.getAnnotation(Field_Method_Parameter_Annotation.class);
				System.out.println(field.getName() + "字段的描述为 " + fa.describe());
				System.out.println(field.getName() + "字段的类型为 " + fa.type());
			}
			System.out.println();
		}

		System.out.println("----------- 字段的描述如下 -----------");
		Method[] methods = recordC.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(Field_Method_Parameter_Annotation.class)) {
				Field_Method_Parameter_Annotation fa = method.getAnnotation(Field_Method_Parameter_Annotation.class);
				System.out.println(method.getName() + "方法的描述为 " + fa.describe());
				System.out.println(method.getName() + "方法的返回类型为 " + fa.type());
			} else {
				System.out.println(method.getName() + "方法没有注释");
			}
			System.out.println();
		}
	}

}

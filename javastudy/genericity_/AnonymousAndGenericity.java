package javastudy.genericity_;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 匿名类与泛型的关系
 */
public class AnonymousAndGenericity {
	public static void main(String[] args) {
		// TODO 因为泛型类只有在创建对象时才能得知泛型类型的信息
		//  可以使用匿名类来获取该对象中泛型T的类型
		TypeLiteral<Integer> literal01 = new TypeLiteral<>() {
		};
		System.out.println(literal01); //java.lang.Integer
		TypeLiteral<ArrayList<String>> literal02 = new TypeLiteral<>() {
		};
		System.out.println(literal02); //java.util.ArrayList<java.lang.String>
	}
}

class TypeLiteral<T> {
	public final Type type;

	public TypeLiteral() {
		Type classType = this.getClass().getGenericSuperclass();
		// System.out.println(this.getClass());
		// System.out.println(classType);
		if (classType instanceof ParameterizedType)
			type = ((ParameterizedType) classType).getActualTypeArguments()[0]; //获取到TypeLiteral的泛型类型T
		else { //如果不是匿名的方式创建的话，classType只是Object的类对象
			throw new UnsupportedOperationException("只能通过创建匿名类来获取实例");
		}
	}

	public TypeLiteral(Type type) {
		this.type = type;
	}

	public String toString() {
		if (type instanceof Class) return ((Class<?>) type).getName();
		else return type.toString();
	}
}
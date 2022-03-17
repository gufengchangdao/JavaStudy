package javastudy.genericity_.excrcise;

/**
 * 自限定的泛型，泛型类型只能是A或其子类，一般子类继承时泛型类型都用子类本身
 * 特点是继承父类并且泛型类型是自己的子类中，其中泛型类型都是子类，而不是父类(这里的A)
 * 可以用来规范泛型类型的范围
 */
public class CRGWithBasicHolder extends A<CRGWithBasicHolder> {
	@Override
	public CRGWithBasicHolder getElement() {
		return super.getElement();
	}
}

// class A<T extends A>{ //去掉<T>就是使用了通配符而已
class A<T extends A<T>> {
	private T element;

	public T getElement() {
		return element;
	}
}

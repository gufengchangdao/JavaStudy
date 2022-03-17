package javastudy.method_;

import java.util.Arrays;
import java.util.Objects;

/**
 * HashCode的重写
 */
public class HashCodeDemo {
	int anInt;
	float aFloat = 10F;
	double aDouble = 10D;
	boolean aBoolean = true;
	char aChar = 'a';//byte、char、short或者int类型
	long aLong = 100L;
	Object object = new Object();

	@Override
	public int hashCode() {
		// return new Double(aDouble).hashCode();
		// return Double.hashCode(aDouble); //使用 Double.hashCode()来避免创建对象
		// return 7*Objects.hashCode(anInt)+11*Objects.hashCode(aDouble); //使用null安全的方法获取hashCode
		// return Objects.hash(anInt,aDouble); //由提供的所有对象的散列码组合而成
		return Arrays.hashCode(new Object[]{anInt, aDouble}); //原理同上
	}

	//散列码常用计算公式，当然还是上面的省事
	public int IdeHashCode() {
		int result = 17;
		result = result * 37 + (aBoolean ? 0 : 1);
		result = result * 37 + (int) aChar;
		result = result * 37 + (int) (aLong ^ (aLong >>> 32));
		result = result * 37 + Float.floatToIntBits(aFloat);
		long l = Double.doubleToLongBits(aDouble);
		result = result * 37 + (int) (l ^ (l >>> 32));
		result = result * 37 + Objects.hashCode(object);
		return result;
	}
}

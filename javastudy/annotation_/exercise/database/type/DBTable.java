//: annotations/database/DBTable.java
package javastudy.annotation_.exercise.database.type;

import javastudy.utilities.ReflectionUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

@Target(ElementType.TYPE) // Applies to classes only
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
	public String name() default "";
	TypeEnum type = TypeEnum.TABLE;

} ///:~
class A{
	public static void main(String[] args) {
		ReflectionUtils.printAllInfo(DBTable.class);
	}
}
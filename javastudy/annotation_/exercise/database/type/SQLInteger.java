//: annotations/database/SQLInteger.java
package javastudy.annotation_.exercise.database.type;

import javastudy.annotation_.exercise.database.type.Constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInteger {
	TypeEnum type = TypeEnum.INTEGER;
	/**
	 * 变量名
	 */
	String name() default "";

	/**
	 * 整数类型名
	 */
	String typeName() default "INT";

	Constraints constraints() default @Constraints;
} ///:~

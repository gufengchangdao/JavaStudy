//: annotations/database/SQLString.java
package javastudy.annotation_.exercise.database.type;

import javastudy.annotation_.exercise.database.type.Constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLString {
	TypeEnum type = TypeEnum.STRING;
	/**
	 * varchar的大小
	 */
	int value() default 0;

	String name() default "";

	Constraints constraints() default @Constraints; //默认使用该注解的默认
} ///:~

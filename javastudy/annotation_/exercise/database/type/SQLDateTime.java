package javastudy.annotation_.exercise.database.type;

import javastudy.annotation_.exercise.database.type.Constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLDateTime {
	TypeEnum type = TypeEnum.DATETIME;
	String name() default "";

	Constraints constraints() default @Constraints;
}

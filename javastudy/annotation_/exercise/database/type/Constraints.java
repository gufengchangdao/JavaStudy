//: annotations/database/Constraints.java
package javastudy.annotation_.exercise.database.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraints {
	TypeEnum type = TypeEnum.CONSTRAINTS;
	boolean primaryKey() default false;

	boolean allowNull() default false;

	boolean unique() default false;

} ///:~

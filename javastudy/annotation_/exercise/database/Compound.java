package javastudy.annotation_.exercise.database;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public interface Compound {
	void work(Field field, Annotation annotation, List<String> list);
}

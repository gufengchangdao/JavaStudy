package javastudy.reflection_;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;

public class LookupTest {
    /**
     * 使用可变句柄来读写字段
     */
    public Object getFieldValue(Object obj, String fieldName, MethodHandles.Lookup lookup)
            throws NoSuchFieldException, IllegalAccessException {
        Class<?> cl = obj.getClass();
        Field field = cl.getDeclaredField(fieldName);
        VarHandle handle = MethodHandles.privateLookupIn(cl, lookup).unreflectVarHandle(field);
        return handle.get(obj);
    }
}

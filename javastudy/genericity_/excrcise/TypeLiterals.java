package javastudy.genericity_.excrcise; /**
 * @version 1.01 2018-04-10
 * @author Cay Horstmann
 */

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A type literal describes a type that can be generic, such as
 * ArrayList<String>.
 */
class TypeLiteral<T> {
    private Type type;

    /**
     * 这个构造器必须用作为构造匿名子类的方式调用，像 new javastudy.genericity_.excrcise.TypeLiteral<. . .>(){}.
     * 因为泛型类只有在创建对象时才能得知泛型类型的信息
     */
    public TypeLiteral() {
        Type parentType = getClass().getGenericSuperclass(); //使用匿名方式创建对象时，这个T的类型就会已知了
        // System.out.println(getClass().getName()); // javastudy.genericity_.excrcise.TypeLiterals$1
        // System.out.println(parentType); // javastudy.genericity_.excrcise.TypeLiteral<java.util.ArrayList<java.lang.Integer>>
        if (parentType instanceof ParameterizedType) { //是泛型类或接口类型
            type = ((ParameterizedType) parentType).getActualTypeArguments()[0];
        } else
            throw new UnsupportedOperationException("构造器必须是匿名类方式创建");
    }

    private TypeLiteral(Type type) {
        this.type = type;
    }

    /**
     * Yields a type literal that describes the given type.
     */
    public static TypeLiteral<?> of(Type type) {
        return new TypeLiteral<>(type);
    }

    public String toString() {
        if (type instanceof Class) return ((Class<?>) type).getName();
        else return type.toString();
    }

    // 需要使用map来放TypeLiteral对象，所以这里重写了equals和hashCode方法，因为type是唯一的，这一点类似于每个类的类对象是唯一的
    public boolean equals(Object otherObject) {
        return otherObject instanceof TypeLiteral && type.equals(((TypeLiteral<?>) otherObject).type);
    }

    public int hashCode() {
        return type.hashCode();
    }
}

/**
 * 格式化器类，使用添加的类型格式化规则给对象的字段进行格式化
 */
class Formatter {
    // 使用 Function<?, String>而不是Function<Object, String>是因为Function<T, String>不能传给Function<Object, String>
    private final Map<TypeLiteral<?>, Function<?, String>> rules = new HashMap<>();

    /**
     * 添加一个类型的格式化器到集合中
     *
     * @param type             这个规则适用的类型
     * @param formatterForType 这个类型要调用的函数式表达式
     */
    public <T> void forType(TypeLiteral<T> type, Function<T, String> formatterForType) {
        rules.put(type, formatterForType);
    }

    /**
     * 使用格式化器格式化传入对象的所有字段
     *
     * @param obj 一个对象
     * @return 该对象的所有字段格式化后组成的字符串
     */
    public String formatFields(Object obj) throws IllegalArgumentException, IllegalAccessException {
        var result = new StringBuilder();
        for (Field f : obj.getClass().getDeclaredFields()) {
            result.append(f.getName());
            result.append("=");
            f.setAccessible(true);
            // 获取到该字段的类型所对应的格式化器
            Function<?, String> formatterForType = rules.get(TypeLiteral.of(f.getGenericType()));
            if (formatterForType != null) {
                // TODO 格式化函数使用的是Function<?, String>，无法调用String apply(? t),但是可以将函数式表达式进行强转，转换后就可以调用了
                @SuppressWarnings("unchecked")
                Function<Object, String> objectFormatter = (Function<Object, String>) formatterForType;
                result.append(objectFormatter.apply(f.get(obj)));
            } else
                result.append(f.get(obj).toString());
            result.append("\n");
        }
        return result.toString();
    }
}

public class TypeLiterals {
    public static class Sample {
        ArrayList<Integer> nums;
        ArrayList<Character> chars;
        ArrayList<String> strings;

        public Sample() {
            nums = new ArrayList<>();
            nums.add(42);
            nums.add(1729);
            chars = new ArrayList<>();
            chars.add('H');
            chars.add('i');
            strings = new ArrayList<>();
            strings.add("Hello");
            strings.add("World");
        }
    }

    private static <T> String join(String separator, ArrayList<T> elements) {
        var result = new StringBuilder();
        for (T e : elements) {
            if (result.length() > 0) result.append(separator);
            result.append(e.toString());
        }
        return result.toString();
    }

    public static void main(String[] args) throws Exception {
        var formatter = new Formatter();
        formatter.forType(new TypeLiteral<ArrayList<Integer>>() {
        }, lst -> join(" ", lst));
        formatter.forType(new TypeLiteral<ArrayList<Character>>() {
        }, lst -> "\"" + join("", lst) + "\"");
        System.out.println(formatter.formatFields(new Sample()));
    }
}

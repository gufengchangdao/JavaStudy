package javastudy.genericity_;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Scanner;

/*
TODO 虚拟机中的泛型类型信息
1. JAVA泛型的突出特性之一就是虚拟机中擦除泛型类型，但是擦除的类仍然保留原先微弱的记忆。
2. 原始的类知道它源于的泛型类，可以使用反射来获取泛型类的信息
3. 使用接口Type，包含了所有的类型，Type接口有几个子类
    Class描述具体类型，比如Object、String等等
    TypeVariable 描述类型变量，如 T 或 T extends Comparable<? super T>
    WildCardType描述通配符类型，如 ? super T
    ParameterizedType描述泛型类或接口类，如 Comparable<? super T>
    GenericArrayType描述泛型数组，如 T[]
    可以用instanceof来检验获取的Type是哪一种类型
*/

/**
 * 使用反射打印类信息，包括泛型类的信息
 *
 * @author Cay Horstmann
 * @version 1.11 2018-04-10
 */
public class GenericReflectionTest {
    public static void main(String[] args) {
        // read class name from command line args or user input
        String name;
        if (args.length > 0) name = args[0];
        else {
            try (var in = new Scanner(System.in)) {
                System.out.println("Enter class name (e.g., java.util.Collections): ");
                name = in.next();
            }
        }
        try {
            Class<?> cl = Class.forName(name);
            // 打印类信息
            printClass(cl);
            System.out.println(" {");
            // 打印字段信息
            for (Field field : cl.getDeclaredFields())
                printField(field);
            System.out.println();
            // 打印方法信息
            for (Method m : cl.getDeclaredMethods())
                printMethod(m);
            System.out.println("}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printClass(Class<?> cl) {
        System.out.print(cl);
        // TODO getTypeParameters()，如果这个类型被声明为泛型类型，则返回泛型类型变量，否则为长度为0的数组
        printTypes(cl.getTypeParameters(), "<", ", ", ">", true);
        // TODO getGenericSuperclass(), 获得这个类型所声明超类的泛型类型
        Type sc = cl.getGenericSuperclass();
        if (sc != null) {
            System.out.print(" extends ");
            printType(sc, false);
        }

        // TODO getGenericInterfaces()，获得这个类型所声明接口的泛型类型
        printTypes(cl.getGenericInterfaces(), " implements ", ", ", "", false);
        System.out.println();
    }

    public static void printField(Field field) {
        if (field.getModifiers() != 0)
            System.out.print(Modifier.toString(field.getModifiers()) + " ");
        printType(field.getGenericType(), false);
        // private javastudy.utilities.Pair<? super T> parent;
        System.out.print(" ");
        System.out.println(field.getName() + ";");
    }

    public static void printMethod(Method m) {
        String name = m.getName();
        System.out.print(Modifier.toString(m.getModifiers()));
        System.out.print(" ");
        printTypes(m.getTypeParameters(), "<", ", ", "> ", true); //打印泛型类型的声明

        // TODO getGenericReturnType() 获取这个方法声明的泛型返回类型
        printType(m.getGenericReturnType(), false); //打印返回类型
        System.out.print(" ");
        System.out.print(name);
        System.out.print("(");
        printTypes(m.getGenericParameterTypes(), "", ", ", "", false); //打印参数类型
        System.out.println(")");
    }

    /**
     * 打印执行格式的泛型类型参数名，如
     * [pre]T1[sep]T2[suf]
     *
     * @param types        类型对象数组
     * @param pre          字符头
     * @param sep          间隔符
     * @param suf          字符尾
     * @param isDefinition 是否再继续寻找泛型类型的上限，泛型的类型不需要继续，而变量的声明像Pair<\? super T>需要需要isDefinition为true
     */
    public static void printTypes(Type[] types, String pre, String sep, String suf, boolean isDefinition) {
        // 类似 <T extends java.lang.Object> 这样的情况阻止打印 extends java.lang.Object
        if (pre.equals(" extends ") && Arrays.equals(types, new Type[]{Object.class}))
            return;
        if (types.length > 0) System.out.print(pre);
        for (int i = 0; i < types.length; i++) {
            if (i > 0) System.out.print(sep);
            printType(types[i], isDefinition);
        }
        if (types.length > 0) System.out.print(suf);
    }

    /**
     * 打印单个类型，这个类最重要的方法
     *
     * @param type         类型
     * @param isDefinition 是否再继续寻找泛型类型的上限
     */
    public static void printType(Type type, boolean isDefinition) {
        // Class描述具体类型
        if (type instanceof Class) {
            var t = (Class<?>) type;
            // System.out.print(t.getName());
            System.out.print(t.getSimpleName()); // 这里就不输出全类名了
        }

        //TypeVariable 描述类型变量，如 T 或 T extends Comparable<? super T>
        else if (type instanceof TypeVariable) {
            var t = (TypeVariable<?>) type;
            System.out.print(t.getName());
            if (isDefinition) //TODO 如果为true的话，表示还需要查找该类型的上限
                printTypes(t.getBounds(), " extends ", " & ", "", false);
            // TODO getBound()返回表示此类型变量的上限的Type对象的数组。 请注意，如果没有明确声明上限，则上限为Object 。
            // isDefinition设为false，表示不再继续寻找该类型的上限
        }

        // WildCardType描述通配符类型，如 ? super T
        else if (type instanceof WildcardType) {
            var t = (WildcardType) type;
            System.out.print("?");
            // TODO getUpperBounds() 获取这个变量的子类限定
            // TODO getLowerBounds() 获取这个变量的超类限定
            printTypes(t.getUpperBounds(), " extends ", " & ", "", false);
            printTypes(t.getLowerBounds(), " super ", " & ", "", false);
        }

        // ParameterizedType描述泛型类或接口类，如 Comparable<? super T>
        else if (type instanceof ParameterizedType) {
            var t = (ParameterizedType) type;
            // TODO getOwnerType() 如果是一个内部类型，则返回其外部类型，如果是一个顶级类型，则返回null
            Type owner = t.getOwnerType();
            if (owner != null) {
                printType(owner, false);
                System.out.print(".");
            }
            // TODO getRawType() 获取这个参数化类型的原始类型
            printType(t.getRawType(), false);
            // TODO getActualTypeArguments() 获取这个参数化类型声明的类型参数
            printTypes(t.getActualTypeArguments(), "<", ", ", ">", false);
        }

        // GenericArrayType描述泛型数组，如 T[]
        else if (type instanceof GenericArrayType) {
            var t = (GenericArrayType) type;
            System.out.print("");
            printType(t.getGenericComponentType(), isDefinition);
            System.out.print("[]");
        }
    }
}

/**
 * 栗子类
 *
 * @param <T>
 */
class Pairs<T extends Comparable<Member>> {
    private T first;
    private T second;
    T end;
    private Pair<? super T> parent;
    private Pair<? extends T> child;
    public Pairs() {
    }
    public Pairs(T first, T second) {
        this.first = first;
        this.second = second;
    }
    public T getFirst() {
        return first;
    }
    public void setFirst(T first) {
        this.first = first;
    }
    public T getSecond() {
        return second;
    }
    public void setSecond(T second) {
        this.second = second;
    }
    public static void main(String[] args) {
        // 获取Pair的全类名
        System.out.println(new Object() {
        }.getClass().getEnclosingClass().getName());
    }
}
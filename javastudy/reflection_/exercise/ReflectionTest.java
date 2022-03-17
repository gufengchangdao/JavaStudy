package javastudy.reflection_.exercise;

import java.util.*;
import java.lang.reflect.*;

/*
getName()可以获取方法名和字段名，但是无法获取参数定义的名
*/

/**
 * 使用反射打印指定类的所有信息
 * 可用于一些类数据的查看，比如查看一个内部类的组成
 *
 * @author Cay Horstmann
 * @version 1.11 2018-03-16
 */
public class ReflectionTest {
    public static void main(String[] args) throws ReflectiveOperationException {
        // read class name from command line args or user input
        String name;
        if (args.length > 0) name = args[0];
        else {
            var in = new Scanner(System.in);
            System.out.println("Enter class name (e.g. java.util.Date): ");
            name = in.next();
        }

        // print class name and superclass name (if != Object)
        Class cl = Class.forName(name); //TODO 类加载
        Class supercl = cl.getSuperclass(); //TODO 得到父类
        String modifiers = Modifier.toString(cl.getModifiers()); //TODO 得到类的修饰字符串
        if (modifiers.length() > 0) System.out.print(modifiers + " ");
        System.out.print("class " + name);
        if (supercl != null && supercl != Object.class) //如果该类有父级并且父级不是Object的话
            System.out.print(" extends " + supercl.getName()); //TODO 获取类名
        System.out.print("\n{\n");
        printConstructors(cl); //打印构造器
        System.out.println();
        printMethods(cl); //打印方法
        System.out.println();
        printFields(cl); //打印字段
        System.out.println("}");
    }

    /**
     * 打印一个类的所有构造器
     *
     * @param cl a class
     */
    public static void printConstructors(Class cl) {
        Constructor[] constructors = cl.getDeclaredConstructors(); //TODO 得到类或接口的全部构造器

        for (Constructor c : constructors) {
            String name = c.getName(); //TODO 获取构造器名
            System.out.print("   ");
            String modifiers = Modifier.toString(c.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print(name + "(");

            // print parameter types
            Class[] paramTypes = c.getParameterTypes(); //TODO 获取参数类对象组成的数组
            for (int j = 0; j < paramTypes.length; j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    /**
     * 打印一个类的所有方法
     *
     * @param cl a class
     */
    public static void printMethods(Class cl) {
        Method[] methods = cl.getDeclaredMethods(); //TODO 获取所有方法

        for (Method m : methods) {
            Class retType = m.getReturnType(); //TODO 获取该方法返回类型对应的类对象
            String name = m.getName();

            System.out.print("   ");
            // print modifiers, return type and method name
            String modifiers = Modifier.toString(m.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print(retType.getName() + " " + name + "(");

            // print parameter types
            Class[] paramTypes = m.getParameterTypes();
            for (int j = 0; j < paramTypes.length; j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.print(")");

            // 打印该方法抛出异常的类型
            Class<?>[] exceptionTypes = m.getExceptionTypes();//TODO 获取该方法或构造器抛出异常的类型
            for (int i = 0; i < exceptionTypes.length; i++) {
                if (i == 0)
                    System.out.print(" throws ");
                else if (i != exceptionTypes.length - 1) System.out.print(" ");
                System.out.print(exceptionTypes[i].getSimpleName()); //TODO 打印简化的类名
            }
            System.out.println(";");
        }
    }

    /**
     * 打印一个类的所有字段
     *
     * @param cl a class
     */
    public static void printFields(Class cl) {
        Field[] fields = cl.getDeclaredFields(); //TODO 获取所有字段对应的类对象

        for (Field f : fields) {
            Class type = f.getType(); //TODO 获取该字段对应的类型
            String name = f.getName();
            System.out.print("   ");
            String modifiers = Modifier.toString(f.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.println(type.getName() + " " + name + ";");
        }
    }
}


/*
Class对象及其他对象的方法演示(方法太多了，理解就是想要啥就有啥)
    name ---- 名字
    getDeclared...  声明过的(也就包括了private和protect修饰的)
    getModifiers    获得修饰符
    get...Type      获得某一类的Class对象
    setAccessible   设置或者取消Java语言访问检查

*/
package javastudy.reflection_;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * Class对象及其他对象的方法演示(方法太多了，理解就是想要啥就有啥)
 */
public class ClassMethodDemo {

    //-------------------------------------------- Class对象 --------------------------------------------
    @Test
    public void test1() throws Exception {
        String className = "javastudy.reflection_.Example";
        Class<?> aClass = Class.forName(className);

        //toString()返回该class对象是哪个类的对象
        System.out.println(aClass); //class javastudy.reflection_.Example
        //返回类全名，与前面不同，输出没有"class"
        System.out.println(aClass.getName());   //javastudy.reflection_.Example
        //返回简单类名
        System.out.println(aClass.getSimpleName());
        //class字段和getClass返回该类的运行类型
        System.out.println(Class.class);      //class java.lang.Class
        System.out.println(aClass.getClass());//class java.lang.Class
        //创建一个该类的对象实例，可以转型
        //TODO getDeclaredConstructor()可以获取这个类或接口的全部方法，但不包括由超类继承的方法
        Object object = aClass.getDeclaredConstructor().newInstance();
        //返回父类Class对象
        Class<?> superclass = aClass.getSuperclass();

        // Class的is方法
        // aClass.isArray()
        // aClass.isInterface()
        // aClass.isEnum()
    }

    //-------------------------------------------- 包 --------------------------------------------
    @Test
    public void test2() throws Exception {
        String className = "javastudy.reflection_.Example";
        Class<?> aClass = Class.forName(className);

        //返回包对象
        Package aPackage = aClass.getPackage();
        //返回包名
        System.out.println(aPackage.getName());//javastudy.reflection_
        System.out.println(aPackage.getClass());//class java.lang.Package
    }

    //-------------------------------------------- 属性 --------------------------------------------
    @Test
    public void test3() throws Exception {
        String className = "javastudy.reflection_.Example";
        Class<?> aClass = Class.forName(className);
        Object object = aClass.getDeclaredConstructor().newInstance();

        //获取该类属性
        Field myGameField = aClass.getField("myGame");  //public属性
        Field ageField = aClass.getDeclaredField("age");
        //设置指定对象的该属性，传入对象
        myGameField.set(object, false);

        // TODO 访问权限管理
        ageField.setAccessible(true);   //修改私有属性前必须先抑制访问检查
        ageField.trySetAccessible();    //为这个可访问的对象设置可访问标记
        ageField.canAccess(aClass);     //检测该对象的指定字段是否可以访问

        ageField.set(null, 22);          //对于static属性，修改和访问的时候可以不传入对象
        //获取指定对象的该属性，注意get()方法返回的是一个类，推测基本数据类型会被包装
        System.out.println(myGameField.get(object));//true
        System.out.println(ageField.get(null));     //22
        //得到该Class类对象的所有public属性，包含本类及其父类
        Field[] fields = aClass.getFields();
        for (Field f : fields) {
            System.out.println(f.get(object));
        }
        //返回所有属性
        Field[] declaredFields = aClass.getDeclaredFields();    //不抑制访问检查的话不可以get(访问)
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }
    }

    //-------------------------------------------- 接口 --------------------------------------------
    @Test
    public void test4() throws Exception {
        String className = "javastudy.reflection_.Example";
        Class<?> aClass = Class.forName(className);

        Class<?>[] interfaces = aClass.getInterfaces();
    }

    //-------------------------------------------- 方法 --------------------------------------------
    @Test
    public void test5() throws Exception {
        String className = "javastudy.reflection_.Example";
        Class<?> aClass = Class.forName(className);
        Object object = aClass.getDeclaredConstructor().newInstance();

        //获取方法对象
        Method cry = aClass.getMethod("cry", String.class, int.class);    //获取含参方法
        Method tou = aClass.getMethod("tou");                           //无参方法
        Method[] methods = aClass.getMethods();
        Method pri = aClass.getDeclaredMethod("pri", String.class);      //私有方法
        Method[] declaredMethods = aClass.getDeclaredMethods();

        //调用方法
        Object invoke = cry.invoke(object, "李四", 15);
        tou.invoke(null);                       //静态方法可以不传对象
        pri.setAccessible(true);                    //私有方法调用要先set
        pri.invoke(object, "你好");             //调用私有方法

        //返回修饰符
        //说明.txt: 默认修饰符是0 、public是1、private是2、protected是4、static是8、final是16
        // int modifiers = cry.getModifiers();
        // TODO 使用Modifier工具类来输出字符串或者监测是否为某个修饰词
        String str = Modifier.toString(cry.getModifiers());
        boolean isAb = Modifier.isAbstract(cry.getModifiers());
        boolean isFi = Modifier.isFinal(cry.getModifiers());

        //返回返回值类的Class对象
        Class<?> returnType = cry.getReturnType();

        //返回方法名
        String name = cry.getName();

        //返回参数类型数组
        Class<?>[] parameterTypes = cry.getParameterTypes();
    }

    //-------------------------------------------- 构造器 --------------------------------------------
    @Test
    public void test6() throws Exception {
        String className = "javastudy.reflection_.Example";
        Class<?> aClass = Class.forName(className);

        //获取构造器
        Constructor constructor = aClass.getConstructor();  //返回无参构造器
        Constructor constructor1 = aClass.getConstructor(int.class);    //有参构造器
        //getDeclaredConstructor除了访问私有构造器外，还可以访问默认无参构造器，如果类中没有写构造器，创建对象就得用这个了
        Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(String.class);//访问私有构造器
        Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();

        //构造器创建对象
        Object o = constructor.newInstance();
        // 将此对象的accessible标志设置为指示的布尔值。
        // true的值表示反射对象应该在使用时抑制Java语言访问检查。 false的值表示反映的对象应该强制执行Java语言访问检查。
        declaredConstructor.setAccessible(true);
        Object o1 = declaredConstructor.newInstance();
    }

    //-------------------------------------------- 注解 --------------------------------------------
    @Test
    public void test7() throws Exception {
        String className = "javastudy.reflection_.Example";
        Class<?> aClass = Class.forName(className);

        //返回注解信息
        Annotation[] annotations = aClass.getAnnotations();
    }

}

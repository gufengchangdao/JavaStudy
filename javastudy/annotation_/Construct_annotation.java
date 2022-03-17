/*
Annotation注解功能

1. 该功能不影响程序运行，但是会对编译器警告等辅助工具产生影响
2. @Target来设置注释类适用的程序元素种类，如果未设置@Target，则适用于所有程序元素，通过枚举类ElementType类来设置@Target
3. @Retention用来设置注释类的有效范围，未设置默认为CLASS表示的范围，通过枚举类RetentionPolicy，该类的三种范围：
        SOURCE  表示不编译Annotation到类文件中，有效范围最小
        CLASS   表示编译Annotation到类文件中，但是在运行时不加载Annotation到JVM中
        RUNTIME 表示在运行时加载Annotation到JVM中，因此可以使用反射读取注解信息，有效范围最大
   @Inherited允许子类继承父类的注解
4. 没有提供默认值的方法在注解时就必须提供值
5. 注解元素的类型只能是
	基本类型/String/Class/enum/Annotation/前面类型的数组
6. 默认值限制：元素不能有不确定的值，不能为null，如果要表示数据不存在可以定义一些特殊的值，例如空串或-1
7. 使用注解有一个快捷方式，如果注解中有一个名为value的元素，并且该元素是唯一需要赋值的一个元素，就可以传入一个数字，不需要使用名=值这样的方式了
8. 注解不支持继承，但是可以创建一个注解，含有一个enum元素，提供多种模式供选择，可节省代码，缺点就是无法细微改动
*/
package javastudy.annotation_;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.CONSTRUCTOR)            //用于构造方法
@Retention(RetentionPolicy.RUNTIME)         //在运行时加载Annotation到JVM中
public @interface Construct_annotation {    //@interface是Annotation类型的关键字
	String value() default "默认构造方法";    //定义一个具有默认值的String型成员
}

@interface ModeChoice{
	enum Mode{
	//...
	}
}
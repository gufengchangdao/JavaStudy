package javastudy.innerclass_;

/**
 * 探究内部类的结构
 */
public class InnerClass03 {
    private String name = "李逍遥";

    public class Test {
        @Override
        public String toString() {
            return name;
        }
    }

    private class Abc {
    }

    public void method() {
        int age = 10;
        // javastudy.innerClass_.InnerClass03$1Qwe
        class Qwe {
            public void test() {
                // System.out.println(age);
                System.out.println(getClass().getName());
            }
        }
    }

}
/*
TODO 前言：
1. 内部类时一个编译器现象，与虚拟机无关。编译器会把内部类转换为常规的类文件，用$分割外部类名和内部类名，而虚拟机对此一无所知


TODO 内部类的名称为 InnerClass03$Test，下面使用反射来查看内部类的组成信息
public class InnerClass03$Test (省略了类的路径)
{
   public InnerClass03$Test(InnerClass03);
   public java.lang.String toString();
   final InnerClass03 this$0;
}

1. 编译器在内部类生成了一个参数为外围类对象的构造器，在创建内部类时使用
2. 含有一个 this$0 的字段，指向外围类的对象
3. 由于内部类可以使用外围类的属性和方法，内部类有更大的访问权限，所以天生就比常规类更强大

private class InnerClass03$Abc
{
   private InnerClass03$Abc(InnerClass03);
   final InnerClass03 this$0;
}

4. private修饰的内部类经编译器编译后也是private的，JVM会重新计算嵌套类的修饰符号，private不影响虚拟机对对象的访问和创建
5. 注意到构造器也私有化了，别人也不能创建该对象了

class InnerClass03$1Qwe
{
   InnerClass03$1Qwe(InnerClass03);
   public void test();
   final InnerClass03 this$0;
}

6. 局部内部类的命名和前两个不太一样
 */
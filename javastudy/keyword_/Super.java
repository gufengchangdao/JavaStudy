/*
super
    super代表父类的引用，用于访问父类的属性、方法、构造器
    注意，要用super访问，就要求属性/方法/构造器必须是非private的
    super也可以用来解决重名问题
    多重继承的类，super访问遵循就近原则

 */
package javastudy.keyword_;

public class Super {
    public static void main(String[] args) {

    }
}
class SupA{
    private String name;
    public int sal;
    public SupA(String name) {
        this.name = name;
    }
}
class SupB extends SupA{
    int age;
    public SupB(String name,int age,int sal) {
        //访问父类的构造器，只能放在构造器的第一句，且只能出现一次
        super(name);
        //访问父类属性
        super.sal=sal;
        this.age=age;
    }
}

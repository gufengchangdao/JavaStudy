/*
this
    访问本类的属性、方法、构造器
    如果本类中没有此属性则从父类中继续查找
    在静态代码块中不能用this
 */
package javastudy.keyword_;

public class ThisDemo {
    private String name;
    private int age;
    private int sal;

    public ThisDemo(String name) {
        this.name = name;
    }

    private ThisDemo(String name, int age, int sal) {
        //调用本类的构造器，用this调用的话只能写在构造器中
        this(name);
        //调用本类的属性，即便是private
        this.age = age;
        this.sal = sal;
    }

    public String  cry(){
        return "嗷呜";
    }
    public void animal(){
        //调用本类的方法
        System.out.println("小狗在 " + this.cry());
    }
}

package javastudy.reflection_;

/**
 * 作为类加载的对象的例子
 */
@SuppressWarnings("all")
public class Example extends Parent implements A1,A2{
    public String name="天才";
    public boolean myGame=true;
    private static int age=14;

    public Example() {
    }

    public Example(int age) {
        this.age = age;
    }

    public static void tou(){
        System.out.println("你调用了tou()方法");
    }

    public void mmm(){
        System.out.println("你调用了mmm()方法");
    }

    private void pri(String name){
        this.name=name;
        System.out.println("name:" +this.name);
    }
}

class Parent{
    public String state="Sleep";
    private boolean love=true;

    public Parent() {
    }

    public Parent(String state) {
        this.state = state;
    }
    public void cry(String name,int sal){
        System.out.println("汪汪叫~");
    }
}

interface A1{

}
interface A2{

}
package javastudy.method_;

/**
 * 对象克隆，使用clone方法
 */
public class CloneDemo implements Cloneable {
    private Temp temp = new Temp();
    private int age;

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneDemo main = new CloneDemo();
        main.temp.name = "齐天大圣";
        main.age = 15;
        CloneDemo clone = (CloneDemo) main.clone();

        // TODO 默认的克隆操作：浅拷贝
        // 即只克隆本对象和其基本数据类型的值，没有克隆对象中引用的其他对象，并且只会克隆自己(CloneDemo)
        // 如果类中只有基本数据类型或是引用的都是一些不可更改的对象(比如Integer或String)，则使用浅拷贝没有问题，这种共享就是安全的
        // 或者在对象的生命周期，子对象一直包含不变的常量，没有更改器方法会改变他，也没有方法会生成他的引用，也是安全的
        main.temp.name = "艾希";
        System.out.println(clone.temp.name + "  " + clone.age); //艾希  15 ，因为temp属性时Temp对象的引用

        /*
         * TODO 数组的拷贝(浅拷贝)
         * 所有数组类型都有一个公共的clone方法，而不是受保护的，可以用该方法拷贝数组
         * */
        int[] ints = {2, 3, 4, 5, 6};
        int[] cloned = ints.clone();
        Temp[] temps = {new Temp(), new Temp()};
        Temp[] cloned2 = temps.clone();

    }

    /**
     * Object有一个protect修饰的Clone()方法，但是直接调用会抛异常，必须实现Cloneable接口
     * 子类只能调用受保护的clone方法来克隆 它自己的 对象，必须重新定义clone为public才能允许所有方法克隆对象（有些疑问）
     * Cloneable是java的少数标记接口(记号接口)之一，不含任何方法，它唯一的作用就是允许在类型查询中使用instanceof
     * if (obj instanceof Cloneable){...}
     *
     * @return 克隆好的对象
     * @throws CloneNotSupportedException 克隆不支持
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO 深度拷贝：具体情况具体实现

        return super.clone(); //进行浅拷贝
    }
}

class Temp {
    public String name;
}
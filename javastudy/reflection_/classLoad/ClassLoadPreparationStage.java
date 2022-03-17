package javastudy.reflection_.classLoad;

/**
 * 类加载准备阶段
 */
public class ClassLoadPreparationStage {

    //n1是实例属性，不是静态变量，因此在准备阶段不会分配内存
    //n2是静态变量，分配内存，n2默认值是0，不是 20
    //n3是static final 是常量，他和静态变量不一样，因为一旦赋值就不会变，n3=30
    public int n1 = 10;
    public static int n2 = 20;
    public static final int n3 = 30;
    public static int n4=(++n2);
    static {
        //...
    }
}

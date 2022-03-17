package javastudy.keyword_;

public class VarDemo {
    // 对于局部变量如果可以从变量的初始值推断出它的类型，就不需要再声明类型
    // var i=10; 报错
    public static void main(String[] args) {
        var i=10;
        var str="你好";
    }
}

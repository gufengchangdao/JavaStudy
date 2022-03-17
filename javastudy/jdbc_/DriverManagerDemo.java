/*
DriverManager 类是驱动程序管理器类，负责管理驱动程序

 */
package javastudy.jdbc_;

import org.junit.Test;
import java.sql.Connection;
import java.sql.DriverManager;

public class DriverManagerDemo {
    /*
    注册Driver驱动问题
    (1) 得到connection需要注册driver驱动，但是一般不会直接调用registerDriver()方法，有可能会使驱动程序注册两次
    (2) 在Driver类中就有static代码块 DriverManager.registerDriver(new Driver()); 也就是说Driver类被加载就会注册驱动
    (3) Driver 接口的驱动程序类都包含了静态代码块，在这个静态代码块中，会调用 DriverManager.registerDriver() 方法来注册自身的一个实例，
        所以可以换一种方式来加载驱动。（即只要想办法让驱动类的这段静态代码块执行即可注册驱动类，而要让这段静态代码块执行，只要让该类被类加载器加载即可）
    (4) 可以使用DriverManager.getDrivers()并且where遍历来检测有几个driver驱动
    (5) mysql驱动5.1.6可以无需显示调用Class.forName注册驱动，getConnection内部ensureDriversInitialized()方法会自动调用驱动jar包下
        java.sql.Driver 文本中的类名称去注册，不过最好加上，因为更加明确
    (6) 这里应用了Java的SPI机制(还不会，待学习)
    */
    @Test
    public void test01() throws Exception {
        String url = "jdbc:mysql://localhost:3306/collapse";
        String user = "root";
        String password = "zhangchao123";

        // DriverManager.registerDriver(new com.mysql.jdbc.Driver());               //画蛇添足
        Class.forName("com.mysql.jdbc.Driver");         //加载Driver类，调用其静态代码块来注册驱动
        // ClassLoader.getSystemClassLoader().loadClass("com.mysql.jdbc.Driver");   //效果同上
        Connection connection = DriverManager.getConnection(url, user, password);   //直接连接，内部会实现加载Driver类的
        System.out.println(connection);
    }


}

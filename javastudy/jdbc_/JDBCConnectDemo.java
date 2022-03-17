package javastudy.jdbc_;

import com.mysql.jdbc.Driver;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 演示数据库连接3种方式(递增)
 */
public class JDBCConnectDemo {
    //静态加载，再创建Driver对象
    @Test
    public void connectDemo1() throws SQLException {
        Driver driver = new Driver();
        String url = "jdbc:mysql://localhost:3306/collapse";
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "zhangchao123");
        Connection connect = driver.connect(url, properties);
        System.out.println(connect);//com.mysql.jdbc.JDBC4Connection@548e7350
        connect.close();
    }

    //使用反射加载Driver类，动态加载，更加的灵活，减少依赖性
    @Test
    public void connectDemo2() throws Exception {
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        //这里的强转也不够灵活，可以把信息存到properties里面，不过很麻烦
        //注意，这里的强转需要类的地址和和强转类的地址一样，要是引入的类的地址不一样会抛出异常
        // Object o = aClass.getConstructor().newInstance();
        Driver driver = (Driver) aClass.getDeclaredConstructor().newInstance();
    }

    //相关信息都存入properties中
    //使用DriverManager替代driver进行统一管理
    //DriverManager 类是驱动程序管理器类，负责管理驱动程序
    @Test
    public void connectDemo4() throws IOException, ClassNotFoundException, SQLException {
        // 测试前先存一个配置文件
        // Properties properties = new Properties();
        // properties.setProperty("user","root");
        // properties.setProperty("password","zhangchao123");
        // properties.setProperty("url","jdbc:mysql://localhost:3306/collapse");
        // properties.setProperty("driver","com.mysql.jdbc.Driver");
        // properties.store(new FileOutputStream("src\\javastudy\\jdbc_\\mysql.properties"),null);

        // 读取配置文件数据
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\javastudy\\jdbc_\\mysql.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");

        // 注册驱动，也可以不注册，但是使用类的全名而不是默认的会更明确
        // DriverManager.registerDriver(new com.mysql.jdbc.Driver());    //画蛇添足，会注册两个驱动
        Class.forName(driver);          //加载Driver类，调用其静态代码块来注册驱动
        // ClassLoader.getSystemClassLoader().loadClass(driver);   //效果同上

        // 连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

}

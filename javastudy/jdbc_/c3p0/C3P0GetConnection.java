/*
数据库连接池(database connection pool)
    问题分析:
        (1) 传统的JDBC数据库连接使用DriverManager来获取，每次向数据库建立连接的时候都要将Connection加载到内存中，再验证IP地址，用户名和密码，
            需要连接时就要向数据要求一个，频繁的连接操作将占用数据库很多的系统资源，容易造成数据库崩溃。
        (2) 每一次连接数据库使用完都得断开，连接数量过多可能导致内存泄露，mysql崩溃。如果程序出现异常而未能关闭，也会导致数据库内存泄露
        (3) 解决上面的问题，就出现了数据库连接池(DBCP)，获取连接速度大幅提升
    数据库连接池使用javax.sql.DataSource来表示，DataSource只是一个接口，由第三方来实现
    数据库连接池种类；
        (1) C3P0    速度相对较慢，稳定性不错，老牌连接池                              常用
        (2) DBCP    相对C3P0较快，但是稳定性比它差
        (3) Proxool 有监控连接池状态的功能，稳定性较C3P0差一些
        (4) BoneCP  速度快
        (5) Druid(德鲁伊)  是阿里提供的数据库连接池，集DBCP、C3P0、Proxool优点于一身    非常常用
    注意：在数据库连接池技术中，connection.close()不是真正的断掉连接，而是把使用的connection对象放回连接池，因为实现connection接口的类不同
 */
package javastudy.jdbc_.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 使用C3P0获取连接
 */
public class C3P0GetConnection {
    private static String user;
    private static String password;
    private static String url;
    private static String driver;

    //获取相关参数
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src\\javastudy\\jdbc_\\mysql.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        user = properties.getProperty("user");
        password = properties.getProperty("password");
        url = properties.getProperty("url");
        driver = properties.getProperty("driver");
    }

    @Test
    public void test01() throws PropertyVetoException, SQLException {
        //给数据源 ComboPooledDataSource 设置相关参数，连接管理是由 ComboPooledDataSource 来管理的
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setDriverClass(driver);

        //设置初始化连接数
        comboPooledDataSource.setInitialPoolSize(10);
        //设置最大连接数
        comboPooledDataSource.setMaxPoolSize(50);
        // 设置最小连接数
        // comboPooledDataSource.setMinPoolSize(2);
        //获取一个连接
        // Connection connection = comboPooledDataSource.getConnection("root", "zhangchao123");
        Connection connection = comboPooledDataSource.getConnection();
        System.out.println("获取到一个连接");
        connection.close(); //放回连接池
        comboPooledDataSource.close();//断开与数据源的连接，这样就不能getConnection了
    }

    //使用配置文件模板来完成
    //将 c3p0-config.xml 配置文件放在src下面，该文件指定了连接数据库和连接池的相关参数
    //参数的配置很像properties的键值对
    @Test
    public void test02() throws Exception {
        // 使用模板内定义的数据源名字为参数创建对象
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("wahaha");
        // 直接获取连接就可以了
        Connection connection = comboPooledDataSource.getConnection();
        System.out.println("获取到一个连接");
        connection.close(); //把connection放回连接池，注意connection未关闭
        comboPooledDataSource.close();
    }
}

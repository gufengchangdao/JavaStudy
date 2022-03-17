package javastudy.jdbc_.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * 使用Druid获取连接
 */
public class DruidGetConnection {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        //这个配置文件里放有用户相关信息和数据源的设置
        properties.load(new FileInputStream("src\\javastudy\\jdbc_\\druid\\druid.properties"));

        //创建一个指定参数的数据库连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        //获取一个连接
        Connection connection = dataSource.getConnection();
        System.out.println("获取到一个连接");
        connection.close(); //把connection放回连接池
    }
}

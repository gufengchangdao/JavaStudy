package javastudy.jdbc_.dao_.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 基于Druid的工具类
 * 用于获取连接和关闭资源
 */
public class JDBCUtilsByDruid {
    private static DataSource dataSource;

    //在静态代码块中获取dataSource对象，类加载的时候就执行
    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src\\javastudy\\jdbc_\\druid\\druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取一个连接
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    // public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
    //     try {
    //         if (connection!=null) connection.close();
    //         if (preparedStatement!=null) preparedStatement.close();
    //         if (resultSet!=null) resultSet.close();
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }
    // 关闭相关资源
    public static void close(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if (connection!=null) connection.close();
            if (statement!=null) statement.close();
            if (resultSet!=null) resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

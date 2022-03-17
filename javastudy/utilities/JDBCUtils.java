package javastudy.utilities;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 数据库连接工具类
 */
public class JDBCUtils {
    // 数据库配置文件地址
    private static DataSource dataSource;
    private static final String CONFIG_FILE = "src/com/config/druid.properties";

    static {
        Properties pro = new Properties();// 配置文件解析类
        try {
            // 配置文件的文件对象
            File config = new File(CONFIG_FILE);
            if (!config.exists()) {// 如果配置文件不存在
                throw new FileNotFoundException("缺少文件：" + config.getAbsolutePath());
            }
            pro.load(new FileInputStream(config));// 加载配置文件
            //创建一个指定参数的数据库连接池
            dataSource = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接。根据jdbc.properties中的配置信息返回对应的Connection对象。
     *
     * @return 已连接数据库的Connection对象
     */
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 安全的关闭数据库接口
     *
     * @param statement 要关闭的 Statement
     * @param resultSet 要关闭的ResultSet
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

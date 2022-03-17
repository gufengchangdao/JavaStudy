package javastudy.jdbc_.dbutils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * 使用Druid和DBUtils执行增删改操作
 */
public class DBUtilsUpdate {
    public static void main(String[] args) throws Exception {
        //使用Druid连接池获取一个连接
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\javastudy\\jdbc_\\druid\\druid.properties"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();

        //使用DBUtils实现 增删改 操作
        QueryRunner queryRunner = new QueryRunner();
        // String sql="insert into member values ('s','雅典娜','古代神话',9000,'治愈',9,now())";
        String sql = "delete from member where `name`=?";
        int affectedRows = queryRunner.update(connection,sql,"雅典娜");
        System.out.println("受影响的行数为 "+affectedRows);
        connection.close();
    }
}

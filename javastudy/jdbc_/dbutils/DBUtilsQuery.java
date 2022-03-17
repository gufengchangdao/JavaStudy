/*
resultSet的局限性：
    (1) 关闭connection或者Statement之后，resultSet就无法使用了(因为连接断开了)
    (2) resultSet不利于数据的管理，因为只能顺序(倒叙)遍历，使用get时需要传入参数字段名，不便捷

使用DBUtils类，可以很好的解决该问题
    (1) commons-dbutils是一个开源的JDBC工具类，它是对JDBC的封装，使用dbutils极大地简化jdbc编码的工作量
    (2) queryRunner是线程安全的，可以实现增删改查、批处理
    (3) ResultSetHandler接口：用于处理resultSet，将数据按照要求转化为另一种形式
        ArrayHandler        把结果集的第一行数据转化为对象数组
        ArrayListHandler    把结果集的每一行数据都转成一个数组，再存到List中
        BeanHandle          把结果集的第一行数据封装到一个对应的javaBean实例中
        BeanListHandle      把结果集的每一行数据都封装到一个对应的javaBean实例中，存到List里
        ColumnListHandler   把结果集某一列的数据存放到List中
        KeyedHandler(name)  将结果集每行数据都封装到map中去，再把这些map存到一个map中去，key为指定的key
        MapHandler          将结果集中的第一行数据封装到一个map中去，key是列名，value是对应的值
        MapListHandler      将结果集中的每一行数据都封装到一个map中去，然后再存放放到list中去
        ScalarHandler       查询单行单列数据

*/
package javastudy.jdbc_.dbutils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * DBUtils对象的query()方法使用
 */
public class DBUtilsQuery {
    private static Connection connection = null;
    private static QueryRunner queryRunner = null;
    private static String sql = null;

    static {
        //1、先拿到connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/collapse", "root", "zhangchao123");
            //2、创建 QueryRunner
            queryRunner = new QueryRunner();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    // 使用BeanListHandle查询所有数据
    public void test01() throws SQLException {
        //3、sql语句可以保留 ? ，在查询时可以作为参数传入
        sql = "select * from member where salary>= ?";
        List<Member> list = queryRunner.query(connection, sql, new BeanListHandler<>(Member.class), 8000);

        for (Member member : list) {
            System.out.println(member);//member已经重写过toString了
        }
        //4、关闭资源，只用connection就可以了
        connection.close();
    }

    @Test
    // 使用 BeanHandler查询单行记录
    public void test02() throws SQLException {
        sql = "select * from member where `name`=?";
        //查询单行的记录，如果没有符合条件的，就会返回null，有多条记录就会返回第一条记录
        Member query = queryRunner.query(connection, sql, new BeanHandler<>(Member.class), "德丽莎·阿波卡利斯");
        System.out.println(query);
        connection.close();
    }

    @Test
    // 使用 ScalarHandler查询单行单列记录
    public void test03() throws SQLException {
        sql="select salary from member where `name`=?";
        //查询单行单列记录，因此也不用加载类，返回值就是一Object对象，sql查询结果如果是多个数据，就会返回第一行第一个数据
        Object query = queryRunner.query(connection, sql, new ScalarHandler(), "德丽莎·阿波卡利斯");
        System.out.println("德丽莎·阿波卡利斯的薪水为 "+query);
        connection.close();
    }


}

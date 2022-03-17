/*
query解读:
    List<Member> list = queryRunner.query(connection, sql, new BeanListHandler<>(Member.class),8000);
        (1) query就是执行sql语句，得到resultSet -----封装-----> ArrayList集合中
        (2) new BeanListHandler<>(Member.class) --->将resultSet -> Member对象 ->封装到ArrayList集合中
            底层使用反射机制去获取 Member类的属性，然后进行封装
        (3) 8000是给sql语句中的 ? 赋值，有多个问号也可以写入多个值
        (4) query查询中的创建的Statement、resultSet不用管，在内部会关闭，自产自销，也就是说只用关闭connection
        (5) 储存数据的类要求:
            类中属性名要和对应数据的字段名相等，否则不会赋值
            对于想要赋值的属性需要定义set..()方法，无论该属性是否是public的，没有赋值的属性就是默认的值
            数据库中的char数据用String类型替代，不能使用char属性，但是可以使用int、float...，会自动转型，但是最好直接写成Integer、Float...
            date数据就用Date对象替代
*/
package javastudy.jdbc_.dbutils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.alibaba.druid.util.JdbcUtils.close;

/**
 * QueryRunner的query()方法源码解读
 */
public class QueryInterpretation {

    // 步骤:
    // 1.验证参数是否合法
    // 2.执行sql语句，并对返回的结果集进行加工处理
    // 3.释放资源，返回加工好的结果集
    // private <T> T query(Connection conn, boolean closeConn, String sql, ResultSetHandler<T> rsh, Object... params)
    //         throws SQLException {
    //     //连接为null会抛异常
    //     if (conn == null) {
    //         throw new SQLException("Null connection");
    //     }
    //     //sql为null也会抛异常
    //     if (sql == null) {
    //         if (closeConn) {
    //             close(conn);
    //         }
    //         throw new SQLException("Null SQL statement");
    //     }
    //     // 结果集处理对象为null会抛异常
    //     if (rsh == null) {
    //         if (closeConn) {
    //             close(conn);
    //         }
    //         throw new SQLException("Null ResultSetHandler");
    //     }
    //     //定义查询数据需要的参数
    //     PreparedStatement stmt = null;
    //     ResultSet rs = null;
    //     T result = null;
    //
    //     try {
    //         stmt = this.prepareStatement(conn, sql);    //创建PreparedStatement对象
    //         this.fillStatement(stmt, params);           //对sql的 ? 进行检验格式并赋值
    //         rs = this.wrap(stmt.executeQuery());        //执行sql语句，返回resultSet
    //         result = rsh.handle(rs);                    //resultSet -遍历resultSet数组-> ArrayList (使用到反射，对传入的class对象进行处理)
    //                                                     //因为JDBC4ResultSet含有elementData是一个Object数组，存放所有行数据
    //     } catch (SQLException e) {
    //         this.rethrow(e, sql, params);
    //
    //     } finally {
    //         try {
    //             close(rs);          //关闭ResultSet
    //         } finally {
    //             close(stmt);        //关闭PreparedStatement
    //             if (closeConn) {
    //                 close(conn);
    //             }
    //         }
    //     }
    //     return result;
    // }

}

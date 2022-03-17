package javastudy.jdbc_.dao_.dao;

import javastudy.jdbc_.dao_.utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.List;

/**
 * 开发BasicDAO，是其他DAO的父类
 * @param <T>
 */
public class BasicDAO<T> {  //泛型指定返回对象的具体类型
    private QueryRunner queryRunner=new QueryRunner();

    public int update(String sql,Object... parameters){
        Connection connection=null;
        try {
            connection= JDBCUtilsByDruid.getConnection();
            return queryRunner.update(connection, sql, parameters); //更新操作
        } catch (Exception e) {
            throw new RuntimeException(e);  //将编译异常转化为运行异常并抛出
        } finally {
            JDBCUtilsByDruid.close(connection,null,null); //关闭相关资源
        }
    }

    // 查询多条数据
    public List<T> queryMulti(String sql,Class<T> clazz,Object... parameters){
        Connection connection=null;
        try {
            connection= JDBCUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);  //将编译异常转化为运行异常并抛出
        } finally {
            JDBCUtilsByDruid.close(connection,null,null);
        }
    }

    public T querySingle(String sql,Class<T> clazz,Object... parameters){
        Connection connection=null;
        try {
            connection= JDBCUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql, new BeanHandler<>(clazz), parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);  //将编译异常转化为运行异常并抛出
        } finally {
            JDBCUtilsByDruid.close(connection,null,null);
        }
    }

    public Object queryScalar(String sql,Object... parameters){
        Connection connection=null;
        try {
            connection= JDBCUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql, new ScalarHandler(), parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);  //将编译异常转化为运行异常并抛出
        } finally {
            JDBCUtilsByDruid.close(connection,null,null);
        }
    }
}

/*
connection可使用的事务方法:
    (1) setAutoCommit(false)    false为关闭自动提交，默认是true，相当于 start transaction
    (2) setSavepoint()          设置保存点，返回SavePoint对象，可用于回滚时的参数
    (3) rollback()              可以无参，可以传入SavePoint对象，无参数就是回滚到关闭自动提交的地方
    (4) commit()                提交，当你关闭了自动提交就必须手动提交了

*/
package javastudy.jdbc_;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 演示事务使用
 * 执行两个sql指令，要求一个失败就回滚
 */
public class Transaction_ {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/collapse", "root", "zhangchao123");

            //关闭自动提交功能
            connection.setAutoCommit(false);

            String sql = "insert into member(id,`name`,department) values ('s','武器大师','英雄联盟')";
            String sql1 = "insert into member(id,`name`,department) values ('s','邪恶小法师','英雄联盟')";
            preparedStatement = connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate();
            // int a=5/0;   //故意抛异常
            int rows1 = preparedStatement.executeUpdate(sql1);

            //提交事务，如果关闭了自动提交还不手动提交的话，显示操作执行成功但是没有效果的
            connection.commit();
            System.out.println(rows + "  " + rows1);
        } catch (Exception e) {
            System.out.println("执行失败，开始回滚");
            try {
                //回滚，不传入参数就会回滚到setAutoCommit(false)的位置，不过回滚操作不一定要写在catch里面，当executeUpdate返回一个0的时候
                //就是可能操作失败了，就可以回滚了
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

/*
批处理
    (1) 当需要成批插入或者更新记录时，可以采用java的批量更新机制，该机制允许许多条语句一次性提交给数据库批量处理，比单独提交处理更有效率
    (2) 批量处理语句:
        addBatch()      添加需要批量处理的sql语句或参数
        executeBatch()  执行批量处理语句，返回int[] 数组，可以用来看哪些命令没有成功
        clearBatch()    清空批处理包的语句
    (2) 注意：如果要使用批处理语句，需要在url字符串中后面写上 ?rewriteBatchedStatements=true ，不然就算使用批处理语句，速度也只和一个个添加一样
    (2) 批处理和PreparedStatement一起搭配使用效率会更高，可以减少编译次数，又减少运行次数，效率大大提高

 */
package javastudy.jdbc_;

import org.junit.Test;

import java.sql.*;

/**
 * 批处理sql命令演示
 * 计算不同添加数据方式所需的时间
 * 测试方式:计算添加6000个数据所需时间，其中每添加1000个sql指令就会执行一次
 */
public class BatchDemo {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver"); //先注册一下驱动，虽然不注册也行，使用@Test测试前会运行静态代码块
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //使用statement普通添加
    @Test
    public void test01() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/collapse";
        Connection connection = DriverManager.getConnection(url,"root","zhangchao123");

        PreparedStatement delete = connection.prepareStatement("delete from temp");
        delete.executeUpdate();
        delete.close();

        Statement statement = connection.createStatement();
        long start =System.currentTimeMillis();
        for (int i = 0; i < 6000; i++) {
            //使用不使用批处理语句速度都一样
            // statement.addBatch("insert into temp values ("+i+","+"\'御坂"+i+"号\'"+",null)");
            statement.executeUpdate("insert into temp values ("+i+","+"\'御坂"+i+"号\'"+",null)");

            // if ((i+1)%1000==0){
            //     int[] ints = statement.executeBatch();
            //     statement.clearBatch();
            // }
        }
        long end = System.currentTimeMillis();
        System.out.println("执行时间(毫秒): "+(end - start));     //4011

        statement.close();
        connection.close();
    }

    //使用PreparedStatement普通添加
    @Test
    public void test02() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/collapse";
        Connection connection = DriverManager.getConnection(url,"root","zhangchao123");

        PreparedStatement delete = connection.prepareStatement("delete from temp");
        delete.executeUpdate();
        delete.close();

        String sql = "insert into temp values (?,?,null)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        long start =System.currentTimeMillis();
        for (int i = 0; i < 6000; i++) {
            preparedStatement.setInt(1,i);
            preparedStatement.setString(2,"御坂"+i+"号");
            preparedStatement.addBatch();

            if ((i+1)%1000==0){
                int[] ints = preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("执行时间(毫秒): "+(end - start));     //3987

        preparedStatement.close();
        connection.close();
    }

    //使用批处理方式和PreparedStatement添加
    @Test
    public void test03() throws SQLException {
        String url="jdbc:mysql://localhost:3306/collapse?rewriteBatchedStatements=true";
        Connection connection = DriverManager.getConnection(url,"root","zhangchao123");

        PreparedStatement delete = connection.prepareStatement("delete from temp");
        delete.executeUpdate();
        delete.close();

        String sql = "insert into temp values (?,?,null)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        long start =System.currentTimeMillis();
        for (int i = 0; i < 6000; i++) {
            preparedStatement.setInt(1,i);
            preparedStatement.setString(2,"御坂"+i+"号");
            preparedStatement.addBatch();

            if ((i+1)%1000==0){
                int[] ints = preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("执行时间(毫秒): "+(end - start));     //87

        preparedStatement.close();
        connection.close();
    }
}
/*
总结:
    (1) 需要成批插入或者更新记录时,PreparedStatement会比Statement快一点
    (2) 如果在url字符串中后面没有写上 ?rewriteBatchedStatements=true ，就算使用批处理语句，速度也只和一个个添加一样
    (3) 批处理很快，o(∩_∩)o

addBatch()源码分析:
    (1) 第一次调用就创建ArrayList -elementDate =>Object[]
    (2) elementDate =>Object[]存放预处理的sql语句
    public void addBatch() throws SQLException {
        if (this.batchedArgs == null) {             //第一次调用batchedArgs这个集合为null
            this.batchedArgs = new ArrayList();
        }
        //检验sql语句并添加进集合
        this.batchedArgs.add(new PreparedStatement.BatchParams(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull));
    }
*/
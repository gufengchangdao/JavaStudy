/*
Statement
    1、用于执行静态sql语句并返回其生成的结构的对象
    2、在连接建立后，需要对数据库进行访问，执行命令或sql语句，可以通过
        Statement           存在SQL注入
        PreparedStatement   预处理
        CallableStatement   存储过程
    3、Statement存在SQL注入问题，有风险，工作基本上不使用这个
    4、SQL注入就是利用某些系统没有对用户输入的数据进行充分的检查，而在用户输入数据中注入非法的SQL语句段或命令，恶意攻击数据库
        简单例子: 需要输入id、password，在id后面加一个 # ，表示注释，注释掉了后面的条件，id匹配就直接登录成功了
    5、PreparedStatement接口继承了Statement接口
    6、预处理(PreparedStatement)的优点:
        (1) 不再使用+拼接sql语句，减少语法错误
        (2) 有效的解决了SQL注入问题
        (3) 大大减少了编译次数，效率较高
    7、(...)Statement常用方法
        executeQuery()  查询，查询时没有选择数据库的话，就得用数据库名.表名
        executeUpdate() 增删改，返回受影响的行数，每次只能执行一个sql指令，sql指令有误这里会抛出SQLException异常
        execute()       执行任意的sql语句，返回boolean值，查询虽然不报错，但也只是返回true
        set...()        在PreparedStatement中给参数对应类型的值
        close()
    8、ResultSet常用
        next()将光标从当前位置向前移动一行。 ResultSet光标最初位于第一行之前，移到最后一行之后，就会返回false
        previous()向上移动一行，如果没有上一行就返回false
        get...()get可以指明要返回的类型(会自动转换，只要是可以转换的)，
            参数为选择行数据的第几列，从1开始，超过会抛异常，或者是数据的字段名
        getObject()
        getRow() 返回所在行数
        close()
        getMetaData().getColumnCount()可以得到行数据的列数

 */
package javastudy.jdbc_;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * 关于statement不同类别的演示
 * 这里只演示查询
 */
public class StatementDemo {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\javastudy\\jdbc_\\mysql.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverName = properties.getProperty("driver");

        Class.forName(driverName);
        Connection connection = DriverManager.getConnection(url, user, password);
        Scanner scanner = new Scanner(System.in);
        System.out.print("member表的id: ");
        String id=scanner.nextLine();
        System.out.print("member表的name: ");
        String name=scanner.nextLine();

        // statementTest(connection,id,name);
        preparedStatementTest(connection,id,name);

        connection.close();
    }

    public static void statementTest(Connection connection,String id,String name) throws Exception {
        Statement statement = connection.createStatement();

        //如果id = 1' or, name= or '1'= '1，则拼接起来就是
        //     select * from collapse.member where id='1' or' and `name`='or '1'= '1'; 那就所有数据都成立了
        String sql="select * from collapse.member where id='"+id+"' and `name`='"+name+"'";

        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            //用所在列数查询很少用，经常用字段名来查询
            // System.out.println(resultSet.getRow()+": "+resultSet.getString(2) +" "+resultSet.getString(3));
            System.out.println(resultSet.getRow()+": "+resultSet.getString("name") +" "+resultSet.getString("department"));
        }
        resultSet.close();
        statement.close();
        System.out.println("查询完毕");
    }

    public static void preparedStatementTest(Connection connection,String id,String name) throws Exception {
        //参数用 ? 表示，在创建PreparedStatement对象时传入字符串，使用set...()来设置参数(从1开始)，问号不需要括起来
        String sql="select * from collapse.member where id=? and `name`=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        preparedStatement.setString(2,name);

        //注意，这里不要传入参数，传入参数会按参数的查询，变成普通查询，而且sql语句中有 ? 是会抛异常的
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getRow()+": "+resultSet.getString("name") +" "+resultSet.getString("department"));        }
        resultSet.close();
        preparedStatement.close();
        System.out.println("查询完毕");
    }
}

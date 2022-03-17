/*
1. ResultSet接口表示在数据库的数据表中查询的结果集，ResultSet 对象具有指向其当前数据行的指针。最初，指针被置于第一行之前。
2. 获取每行数据的列数：resultSet.getMetaData().getColumnCount()
3. Java没有提供直接返回ResultSet行数的方法，三种方法查看ResultSet的行数:
    (1) 用sql语句中的count函数，然后从ResultSet里获取第一行的数字，
        这种方法不用遍历整个数据集，节省了运算时间，但是不能获取表中的具体数据，并且count()的参数要考虑好
    (2) 遍历ResultSet，用一个变量累加记录行数
        需要遍历整个结果集，在遍历过程中可以获取数据，但是在遍历完之后，不能再使用结果集了
    (2) 在创建Statement时加入参数
        Statement stmt = conn.createStatement(type, concurrency);
        type表示ResultSet的类型，
            ResultSet.TYPE_FORWARD_ONLY ：结果集不能滚动，这是默认值；
            ResultSet.TYPE_SCROLL_INSENSITIVE：结果集可以滚动，但ResultSet对数据库中发送的数据改变不敏感；
            ResultSet.TYPE_SCROLL_SENSITIVE ：结果集可以滚动，并且ResultSet对数据库中发生的改变敏感
            如果可以滚动，可以使用ResultSet 的first()、last()、beforeFirst()、afterLast()、relative()和absolute()等方法，以便在结果集中随意前后移动。
        concurrency表示是否可以使用ResultSet来更新数据库，
            ResultSet.CONCUR_READ_ONLY ：只读结果集，不能用于更新数据库；
            ResultSet.CONCUR_UPDATABLE ：可更新结果集，可以用于更新数据库。
            如果结果集是可更新的，那么可使用ResultSet的 updateRow()、insertRow()、moveToCurrentRow()、deleteRow()和cancelRowUpdates()
            等函数对数据库进行更新

*/
package javastudy.jdbc_;

import java.sql.*;
import java.util.Scanner;

/**
 * 得到ResultSet的行数和列数
 */
public class ResultSetGetRows {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/collapse", "root", "zhangchao123");


        System.out.print("查询的id: ");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.next();

        // query01(connection,id);
        // query02(connection,id);
        query03(connection,id);

        connection.close();
    }

    public static void query01(Connection connection,String id) throws SQLException {
        String sql="select count(*) from collapse.member where id= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int rows = resultSet.getInt(1);
        System.out.println("查找指定id的数据行数有 "+rows);

        resultSet.close();
        preparedStatement.close();
    }

    public static void query02(Connection connection,String id) throws SQLException {
        String sql="select * from collapse.member where id= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        int rows=0;
        while (resultSet.next()){
            //可以获取数据
            rows++;
        }
        System.out.println("查找指定id的数据行数有 "+rows);

        resultSet.close();
        preparedStatement.close();
    }
    public static void query03(Connection connection,String id) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql="select * from collapse.member where id= '"+id+"'";
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.last();
        System.out.println("查找指定id的数据行数有 "+resultSet.getRow()); //返回当前数据所在的行数，第一个数据是1

        //如果还要使用resultSet，就把指针移到第一行(就是第一个数据)
        // resultSet.first();

        resultSet.close();
        statement.close();
    }
}

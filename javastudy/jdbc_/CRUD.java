package javastudy.jdbc_;

import org.gjt.mm.mysql.Driver;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 数据库的增删改查
 */
public class CRUD {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\javastudy\\jdbc_\\operator\\mysql.properties"));
        String url = properties.getProperty("url");

        Driver driver = new Driver();
        Connection connect = driver.connect(url, properties);
        Statement statement = connect.createStatement();

        // update(statement);
        // query(statement);

        statement.close();
        connect.close();
    }

    /*
    executeUpdate
        (1) 方法返回受影响的行数
        (2) 每次只能执行一个sql指令
        (3) sql指令有误这里会抛出SQLException异常
     */
    public static void update(Statement statement) throws Exception {
        String sql;
        sql = "create database temp";
        // sql="use temp";  //如果url没有指明数据库，那后面的指令就得use一下
        // sql="create table news(id name primary key auto_increment,contents varchar(255))";
        // sql="insert into news values (null ,'程序没有bug')";
        // ...
        //返回受影响的行数
        int rows = statement.executeUpdate(sql);
        System.out.println("受影响的行数: " + rows);
    }

    /*
    executeQuery
    (1) collapse.member数据的格式
        id   | name                 | department      | salary | job                      | skin_num | create_time
        a    | 琪亚娜·卡斯兰娜        | 圣芙蕾雅学园       |   4500 | 为了世界的美好而战          |        7 | 2021-09-21 14:18:50
    (2) ResultSet为接口，com.mysql.jdbc.JDBC4ResultSet这个类实现了该接口
            含有一个rowData对象，存储行数据
                含有rows是ArrayList集合
                    含有elementData是一个Object数组，存放所有行数据，内部的数据使用byte数组存储的每一列的值
                    含有size就是数据个数
     */
    public static void query(Statement statement) throws Exception {
        String sql = "select * from collapse.member"; //executeQuery是不能用use切换数据库的，要么提前切换数据库，要吗直接写明数据库
        ResultSet resultSet = statement.executeQuery(sql);

        //next()将光标从当前位置向前移动一行。 ResultSet光标最初位于第一行之前，移到最后一行之后，就会返回false
        while (resultSet.next()) {
            //get可以指明要返回的类型(会自动转换，只要是可以转换的)，参数为选择行数据的第几列，从1开始，超过会抛异常
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String department = resultSet.getString(3);
            int salary = resultSet.getInt(4);
            Date date = resultSet.getDate(7);
            //getRow()返回当前行号
            System.out.println(resultSet.getRow()+"  "+ id + "\t" + name + "\t" + department + "\t" + salary + "\t" + date);
        }

        resultSet.close();
    }

}

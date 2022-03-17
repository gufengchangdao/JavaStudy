package javastudy.jdbc_.exercises;

import java.sql.*;
import java.util.Scanner;

/**
 * 支持增删改查操作
 * 使用Statement完成，需要手写指令
 */
public class Demo03 {
    private static Connection connection=null;
    private static Statement statement=null;

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/collapse";
        connection = DriverManager.getConnection(url, "root", "zhangchao123");
        statement= connection.createStatement();
        Scanner scanner = new Scanner(System.in);
        System.out.println("==== 成功连接到数据库，连接地址为 "+url.split("//")[1]+" ====");

        while (true) {
            System.out.println("请输入sql指令(quit退出)：");
            String sql = scanner.nextLine();
            if (sql.equalsIgnoreCase("quit"))
                break;
            try {
                // 通过关键字来判断要执行增删改操作还是查询操作
                if (sql.substring(0, 6).compareToIgnoreCase("select") == 0) {
                    query(statement, sql);
                } else {
                    update(statement, sql);
                }
            } catch (SQLException e){
                System.out.println("sql命令有误");
            }
        }
        statement.close();
        connection.close();
    }

    public static void query(Statement statement,String sql) throws SQLException {
        ResultSet resultSet = statement.executeQuery(sql);
        int col=0;
        System.out.println("-----------------------------------------------------------------------------------------");
        while (resultSet.next()){
            if (col==0) //得到这条数据中字段的个数，因为都一样，就判断一次
                col= resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= col; i++) {
                System.out.print(resultSet.getString(i)+"\t\t\t");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        resultSet.close();
    }

    public static void update(Statement statement,String sql) throws SQLException {
        int rows = statement.executeUpdate(sql);
        System.out.println("执行成功，受影响行数: "+rows);
    }
}
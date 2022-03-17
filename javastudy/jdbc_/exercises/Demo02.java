package javastudy.jdbc_.exercises;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

/**
 * 将用户信息存入properties文件中
 * 读取输入的sql指令并执行更新操作(不能查询)
 */
public class Demo02 {
    public static void main(String[] args) throws Exception {
        // 获取配置文件中的数据
        Properties properties = new Properties();
        properties.load(new FileInputStream("test_create.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");

        Class.forName(driver);  //加载指定的驱动
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        Scanner scanner = new Scanner(System.in);
        String sql;
        int num=0;
        while (true) {
            System.out.println("请输出第 " +(++num)+" 条指令(\"quit\"退出):");
            sql = scanner.nextLine();
            if (sql.equals("quit")){
                break;
            }
            System.out.println(sql);
            try {
                int rows = statement.executeUpdate(sql);
                System.out.println("改变 "+rows+" 行数据");
            } catch (SQLException e){
                System.out.println("指令有误，执行失败");
                num--;
            }
        }
        statement.close();
        connection.close();
        System.out.println("退出成功");
    }

    //创建用户信息，用于待会读取
    public static void createInformation () throws IOException {
        Properties properties = new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","zhangchao123");
        properties.setProperty("url","jdbc:mysql://localhost:3306");
        properties.setProperty("driver","com.mysql.jdbc.Driver");
        properties.store(new FileOutputStream("test_create.properties"),"information");
    }
}

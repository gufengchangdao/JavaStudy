/*
步骤:
    1.首先需要能够连接mysql数据库的jar文件，该文件可以理解为一个软件包，里面放了实现了java关于数据库的接口的各种类
    2.下载好 mysql-connector-java-5.1.7-bin.jar 之后，创建个目录放进去
    3. 把该项目添加到库中去，右键-->添加到库
 */
package javastudy.jdbc_.exercises;

import org.gjt.mm.mysql.Driver; //添加到库中后就可以引用了

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

/**
 * jdbc快速入门
 * 增删改操作
 */
public class Demo01 {
    public static void main(String[] args) throws SQLException {
        // 1. 注册驱动 - 加载driver类
        Driver driver = new Driver();

        // 2. 获取连接 - 得到connection
        // jdbc:mysql:// 表示一个协议，通过jdbc的方式连接mysql，固定的
        // localhost:3306 主机的IP地址和端口号
        // collapse 表示要连接的数据库
        // mysql的本质就是socket连接
        String url ="jdbc:mysql://localhost:3306/collapse";
        // 将用户名和密码放入properties对象中
        Properties properties = new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","zhangchao123");
        Connection connect = driver.connect(url, properties);

        // 3. 执行增删改查 - 发送sql命令给mysql执行 并接收返回值
        // String sql ="insert into member values ('s','舰长','自天而降',7700,'扫夹板',1,null)";
        // String sql ="delete from member where id='s'";
        Scanner scanner = new Scanner(System.in);
        String sql=scanner.nextLine();
        //statement用于执行静态sql语句并返回其生成的结果的对象，用来发送sql语句并返回结果
        Statement statement = connect.createStatement();
        //执行sql语句，返回影响的行数，失败的时间返回值就是0了。如果sql语句语法错误就会抛异常
        int rows = statement.executeUpdate(sql);    //顾名思义，执行更新操作，因此select不可以使用，因为要接收返回的信息
        System.out.println(rows>0?"成功":"失败");

        // 4. 释放资源
        statement.close();
        connect.close();
    }
}

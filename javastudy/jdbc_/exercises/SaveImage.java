package javastudy.jdbc_.exercises;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;

/**
 * 把图片等文件存入数据库的blob字段中
 */
public class SaveImage {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/temp", "root", "zhangchao123");
        String sql = "insert into temp(picture) values(?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        // TODO 方法一、转化为byte数组再存入blob字段中
        File file = new File("images/4.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        // 获取文件大小
        long fileSize = file.length();              //返回类型是long型的
        // int fileSize = inputStream.available();  //返回类型是int型的，当文件较大int装不下时就会返回int型的最大值
        // byte[] bytes = new byte[3024000];

        byte[] bytes = new byte[(int) fileSize];

        inputStream.read(bytes);
        statement.setBytes(1, bytes);

        // TODO 方法二、获取节点流后直接传入setBlob()方法中
        //      网上好像说都可以，但是不知道为什么我的其他文件可以成功，但是传图片会显示语法错误
        // statement.setBlob(1,new FileInputStream("C:\\Users\\Administrator\\Desktop\\Java workface\\images\\yuanyeye.png"));
        // statement.setBlob(1,new FileInputStream("C:\\Users\\Administrator\\Desktop\\1.jpg"));
        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    private void readImage() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/temp", "root", "zhangchao123");
        String sql = "select picture from temp where id=1";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        // TODO 获取字节数组
        // if (resultSet.next()){
        //     byte[] picture = resultSet.getBytes("picture");
        // }

        // TODO 先获取Blob对象再转换为字节数组
        if (resultSet.next()){
            Blob data = resultSet.getBlob("picture");

            InputStream is = data.getBinaryStream();
            int len = -1;
            byte[] buf = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            is.close();
            baos.close();
            byte[] picture = baos.toByteArray();
        }

    }
}

/*
    使用字符流输出和接收技巧:
        使用转换流 OutputStreamWriter、InputStreamReader  来将字节流转换为 字符流(Writer的子类)
        注意：(1) 如果不想用 socket.shutdownOutput();和while循环 读取，可以使用处理流的方法：
            在用write写完信息后用 newLine 作为信息结束标记， 但是必须要配合 readLine使用
             (2) 如果输出的是字符流，需要手动刷新，否则数据不会写入数据通道
*/
package javastudy.network_.socket_.tcp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 客户端
 * 主机：本机
 * 端口号：9999
 * 以 字符流 形式发送一条信息给服务端，并接收一条信息
 */
public class SocketTCP02Client {
    public static void main(String[] args) throws IOException {
        //连接服务端
        Socket socket = new Socket(InetAddress.getLocalHost(),9999);

        //输出信息
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));//使用处理流和转换流

        bufferedWriter.write("Hello, Server");
        bufferedWriter.newLine();   //插入一个换行符，表示写入的内容结束，必须要配合readLine使用
        bufferedWriter.flush();     //如果使用的是字符流，需要手动刷新，否则数据不会写入数据通道

        //接收回发信息
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        System.out.println(bufferedReader.readLine());

        //关闭IO流和socket,一般是越晚创建的越先关闭
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();

    }
}

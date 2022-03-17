package javastudy.network_.socket_.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 * 主机：本机
 * 端口号：9999
 * 以 字符流 形式接收一条信息并回发
 */
public class SocketTCP02Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(9999);

        //监听，等待连接
        Socket socket = serverSocket.accept();

        //接收信息
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        System.out.println(bufferedReader.readLine());
        Thread.sleep(2000); //延时一下，为了好看

        //回发信息
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

        bufferedWriter.write("Hello, Client");
        bufferedWriter.newLine();
        bufferedWriter.flush();

        //关闭IO流和socket,一般是越晚创建的越先关闭
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
        serverSocket.close();
    }
}

package javastudy.network_.socket_.tcp;

import javastudy.network_.StreamUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 * 主机：本机
 * 端口号：8888
 * 以 字符流 形式接收发来的图片并发送信息给客户端
 */
public class SocketTCP03Server {
    public static void main(String[] args) throws IOException {
        String destPath = "images/4CopyByTCP.jpg";

        ServerSocket serverSocket = new ServerSocket(8888);

        //监听，等待连接
        Socket socket = serverSocket.accept();

        //接收信息
        BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destPath));

        //方法一：边读边写
//        byte[] b = new byte[1024];
//        int readLen;
//        while ((readLen = inputStream.read(b)) != -1) {
//            outputStream.write(b, 0, readLen);
//        }

        //方法二：一次性读完再写
        byte[] destByte= StreamUtils.streamToByteArray(inputStream);
        outputStream.write(destByte);

        //回发信息
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        bufferedWriter.write("服务端: 收到图片");
        bufferedWriter.newLine();
        bufferedWriter.flush();

        //关闭IO流和socket
        bufferedWriter.close();
        outputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}

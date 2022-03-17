package javastudy.network_.socket_.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 * 主机：本机
 * 端口号：9999
 * 以字节流形式接收一条信息并回发
 */
public class SocketTCP01Server {
    public static void main(String[] args) throws IOException {
        //1. 在本机的9999端口处监听，等待连接
        //注意：要求本机没有其他服务监听9999
        //    这个ServerSocket可以通过accept方法返回多个Socket(多个客户端连接服务器的并发)
        ServerSocket serverSocket = new ServerSocket(9999);

        //2. 当没有客户端连接9999端口的时候，程序会阻塞在这里，等待连接
        //  如果有客户端连接，则会返回Socket对象，不再阻塞
        System.out.println("服务端在 9999 端口处监听，等待连接...");
        Socket socket = serverSocket.accept();
        System.out.println("--- 服务端连接成功，接收信息中 ---");

        //3. 通过getInputStream()读取客户端写入到数据通道中的数据并显示
        InputStream inputStream = socket.getInputStream();
        byte[] b = new byte[1024];
        int readLen;
        while ((readLen = inputStream.read(b)) != -1) {  //只要客户端的OutputStream关闭了，while循环就会退出
            System.out.println(new String(b, 0, readLen));
        }

        //回发信息
        System.out.println("--- 开始回发信息 ---");
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("我也好想你啊~".getBytes());
//        outputStream.close(); //虽然close也可以，但是会导致后面的getInputStream抛出异常
        //将此套接字的输入流放置在“流的末尾”。 发送到套接字的输入流侧的任何数据都被确认，然后静默丢弃。
        socket.shutdownOutput();

        //4.关闭流和Socket
        serverSocket.close();
        socket.close();
        inputStream.close();
        System.out.println("--- 服务端关闭 ---");

    }
}

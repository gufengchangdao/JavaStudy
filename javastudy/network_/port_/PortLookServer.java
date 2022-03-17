package javastudy.network_.port_;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 端口查看
 * 服务端部分
 */
public class PortLookServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(8888);

        Socket socket = serverSocket.accept();
        System.out.println("服务端连接成功");

        Thread.sleep(20*1000);

        System.out.println("服务端断开连接");
        socket.close();
    }
}

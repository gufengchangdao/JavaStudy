package javastudy.network_.socket_.socketCloseProblem;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketClose {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("服务端在9999端口监听中...");
            Socket socket = serverSocket.accept();

            //1、造成dout关闭
            //2、使用shutdownOutputStream()关闭输出流dout，不允许重新开启
            //3、使用Buffered不flush的话，关闭输出流数据会存在输出缓存中，不会发送出去的
//            test1AndTest2AndTest3(socket);
            //4、同步关闭测试
//            test4(socket);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public static void test1AndTest2AndTest3(Socket socket) {
        try {
            DataInputStream din = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            System.out.println(din.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test4(Socket socket) throws IOException {
        while (socket.getInputStream().read() > 0) ;
        synchronizedClose(socket);
    }

    public static void synchronizedClose(Socket sok) throws IOException {
        InputStream is = sok.getInputStream();
        sok.shutdownOutput(); // 关闭套接字的output，发送 'FIN' 到另一端
        sok.close(); // 关闭socket，或者关闭它的InputStream 或者 OutputStream
        System.out.println("服务端关闭成功");
    }
}

package javastudy.network_.socket_.tcp;

import javastudy.network_.StreamUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 客户端
 * 主机：本机
 * 端口号：8888
 * 以 字符流 形式发送一张图片，并接收服务端发来的信息
 */
public class SocketTCP03Client {

    public static void main(String[] args) throws IOException {
        String srcPath = "images/4.jpg";
        //请求连接
        Socket socket = new Socket(InetAddress.getLocalHost(), 8888);

        //发送图片
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(srcPath));
        BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());

        //方法一：边读边写
        //错误示范，我用循环读取和写入、shutdownOutput方法的时候，最后会抛出异常，并且图片最下面会有一部分为灰色
        //应该是文件没有读取完成，提前结束了，原因可能在shutdownOutput上面，用close替代的话，图片可以完整读取，但是还会抛异常
//        byte[] b = new byte[1024];
//        int readLen;
//        while ((readLen = inputStream.read(b)) != -1) {
//            outputStream.write(b, 0, readLen);
//        }
//        outputStream.flush();   //关键！在调用shutdownOutput之前刷新
//        socket.shutdownOutput();

        //方法二：一次性读完再写
        //调用工具类，里面就是输出到ByteArrayOutputStream对象并返回了一个byte数组
        byte[] srcByte=StreamUtils.streamToByteArray(inputStream);
        outputStream.write(srcByte);
        socket.shutdownOutput();

        //接收回发信息
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println(bufferedReader.readLine());

        //关闭IO流和Socket
        bufferedReader.close();
//        outputStream.close();
        inputStream.close();
        socket.close();

    }
}

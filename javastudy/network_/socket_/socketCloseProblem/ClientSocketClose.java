/*
Socket(套接字) 关闭问题
    1、调用shutdownOutput()套接字实际上会在网络 (FIN) 上发送一些东西，唤醒另一端的选择器，在另一端使用read()读取就会返回 -1
    2、关闭socket将关闭InputStream和OutputStream，而无法读取可能可用的数据
    3、Socket.close()方法执行“正常”断开连接，因为将发送留在输出流中的数据，然后发送一个 FIN以表示其关闭。FIN 将被另一方确认。如果需要等待对方
        关闭它的socket，就需要等待它的FIN，你就必须要检测 Socket.getInputStream().read() < 0，这意味着你也不能关闭自己的Socket，因为关
        闭的话就不能使用inputStream了
    4、一个socket对象就拥有一个InputStream对象和OutputStream对象

Socket和IO流关闭演示:
    说明:用dout表示socket的输出流，din表示socket的输入流
    1、可以造成dout被关闭的操作有：
        (1)调用dout.close();或din.close();因为使用这种流关闭，会造成socket被关闭，所以输入输出流都将不可再用。
        (2)调用socket.close();
        (3)调用socket.shutdownOutputStream();单方面关闭dout，此时din还可正常使用
    2、使用shutdownOutputStream()关闭输出流dout，不允许重新开启(连getOutputStream()方法都抛异常)
    3、使用Buffered输出内容并且使用shutdownOutputStream()关闭输出流dout，使用了writeUTF()后如果不使用flush()，输出缓冲区里的数据会被丢弃
        数据会存在输出缓存中，不会发送出去的
    4、同步关闭测试
 */
package javastudy.network_.socket_.socketCloseProblem;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocketClose {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);

            //1、造成dout关闭
//            test1(socket);
            //2、使用shutdownOutputStream()关闭输出流dout，不允许重新开启
//                test2(socket);

            //3、使用Buffered不flush的话，关闭输出流数据会存在输出缓存中，不会发送出去的
//            test3(socket);

            //4、同步关闭测试，关闭测试需要服务端处于读取状态
//            test4(socket);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test1(Socket socket) throws IOException {
        //可以发送
        System.out.println("测试前，socket现在是否关闭？ " + socket.isClosed());//false

        //不可以
//        socket.shutdownOutput();
        System.out.println("调用shutdownOutput()后，socket现在是否关闭？ " + socket.isClosed());//false

        //不可以(socket都关闭了好不好)
        socket.getInputStream().close();
//        socket.getOutputStream().close();
//        socket.close();
        System.out.println("关闭任意一个IO流或socket后，socket现在是否关闭？ " + socket.isClosed());//true

        //像服务端发送，看是否能接收(或者不抛异常)
        socket.getOutputStream().write("发给服务端，检测dout是否关闭".getBytes());
    }

    public static void test2(Socket socket) throws IOException {
        socket.shutdownOutput();
        //客户端getOutputStream()方法抛出SocketException异常，服务端抛出异常EOFException异常
        socket.getOutputStream().write("我已经关闭了输出流，还能再打开吗".getBytes());
    }

    public static void test3(Socket socket) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF("shutdownOutput后，数据发得得出去吗？");
        //使用Buffered时不刷新的时候数据在缓存区没有被服务端读取。在服务端读取的时候突然调用shutdownOutput导致服务端抛出EOFException异常
//        dataOutputStream.flush();
        socket.shutdownOutput();
    }

    public static void test4(Socket socket) throws IOException {
        System.out.println("客户端准备进行关闭");
        synchronizedClose(socket);
        System.out.println("客户端关闭成功");
    }

    //同步关闭套接字方法
    //双方必须使用相同的关闭方式
    //丢弃程序（应用层）中的数据会导致应用层出现非正常断开。而用while循环读取时，尽管所有数据都是在 TCP 层（while循环）接收的，但它们被丢弃了
    //这在某些操作系统上很重要，因为只要其缓冲区之一仍包含数据，它们实际上就不会关闭套接字。它们被称为“幽灵”套接字
    public static void synchronizedClose(Socket sok) throws IOException {
        InputStream is = sok.getInputStream();
        sok.shutdownOutput();   // 关闭套接字的output，发送 'FIN' 到另一端，将终止另一端的 read() > 0
        while (is.read() > 0) ; // 不断读取回复，直到收到另一端的 'FIN' 回复，收到时会返回 -1
        sok.close();            // 关闭socket，或者关闭它的InputStream 或者 OutputStream
    }
}

package javastudy.network_.socket_.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 客户端
 * 主机：本机
 * 端口号：9999
 * 以字节流形式发送一条信息给服务端，并接收一条信息
 */
public class SocketTCP01Client {
    public static void main(String[] args){
        InputStream inputStream=null;
        OutputStream outputStream=null;
        Socket socket=null;

        try {
            //1. 连接服务端(ip,端口号)，连接不上会抛出IOException异常，可以处理
            socket = new Socket(InetAddress.getLocalHost(), 9999);
//        socket = new Socket(InetAddress.getByName("www.baidu.com"), 9999);//也可以是域名创建的InetAddress对象
            System.out.println("--- 客户端连接成功,开始输出内容 ---");

            //2.连接上以后，生成Socket，得到Socket对象关联的输出流对象
            //返回的输出流运行类型为 Socket$SocketOutputStream
            outputStream = socket.getOutputStream();

            //3.通过输出流，写入数据到 数据通道
            outputStream.write("客户端：服务端你好啊，我们终于见面了！".getBytes());

            //接受回发信息
            System.out.println("--- 客户端等待对方发信息 ---");
            socket.shutdownOutput();    //结束服务端的while循环读取，类似于添加一个结束标记
            inputStream = socket.getInputStream();
            byte[] b=new byte[1024];
            int readLen;
            while ((readLen=inputStream.read(b))!=-1){
                System.out.println(new String(b,0,readLen));
            }

        } catch (UnknownHostException e) {  //连接失败的时候输出个内容
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("--- 客户端连接失败 ---");
        }
        finally {
            //4.关闭流对象和Socket，必须关闭！
            try {
                if (inputStream!=null)
                    outputStream.close();
                if (socket!=null)
                    socket.close();
                System.out.println("--- 客户端退出了 ---");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

package javastudy.network_.socket_.tcp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * TCP发送端
 * 目标端口号：9999
 * 从键盘中读取内容发送给服务端，再由服务端返回内容给客户端并打印在显示屏上
 *
 */
public class SocketTCP04Client {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String content;
        boolean loop=true;

        //连接服务端(ip,端口号)
        Socket socket = new Socket(InetAddress.getLocalHost(),9999);
        System.out.println("--- 客户端连接成功,开始输出内容 ---");
        //创建IO对象
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (loop){
            //读取输入内容
            content=scanner.next();

            //输出给服务端
                bufferedWriter.write(content);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            //打印服务端内容
            try {
                System.out.println("小哀: " + bufferedReader.readLine()); //服务端关闭了socket 读取就会抛出异常，捕捉处理
            } catch (IOException e){
                System.out.println("--- 小哀下线了，去服务端找找她吧 ---");
                loop =false;    //退出循环
            }
        }

        //关闭资源
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
    }
}

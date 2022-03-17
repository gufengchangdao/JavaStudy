/*
端口的查看
    当客户端连接到服务端后，实际上客户端也是通过一个端口和服务端进行通讯的，这个端口是TCP/IP分配的，是不确定的，是随机的
    windows + r ---> cmd(或者是IDEA下的终端) ---> netstat -an|more ---> 找到自己指定的端口号

只启动服务端：
协议       本地地址              外部地址                  状态
TCP    0.0.0.0:8888           0.0.0.0:0              LISTENING

服务端、客户端都启动
协议         本地地址                外部地址               状态
TCP    192.168.65.1:8888      192.168.65.1:20904     ESTABLISHED
TCP    192.168.65.1:20904     192.168.65.1:8888      ESTABLISHED
    客户端创建了20904端口，通过该端口与8888端口建立一条数据通道，当断开连接时，20904端口也就被释放了

 */
package javastudy.network_.port_;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 端口查看
 * 客服端部分
 */
public class PortLookClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 8888);
        System.out.println("客户端连接成功");

        Thread.sleep(20*1000);  //连接20s，方便观察

        System.out.println("客户端断开连接");
        socket.close();
    }
}

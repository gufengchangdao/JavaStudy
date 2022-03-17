package javastudy.network_.socket_.udp;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * UDP传输
 * 发送端B
 * 在端口<b>9998</>发送信息到端口<b>9999</b>，再接收信息
 */
public class UDPSenderB {
    public static void main(String[] args) throws IOException {
        //1.创建一个DatagramSocket对象，准备在 9998 端口接收数据
        //发送端也需要指定端口，跟TCP不一样，TCP的发送端的端口号为不确定的
        DatagramSocket datagramSocket = new DatagramSocket(9998);

        //2. 将需要发送的数据封装到DatagramPacket对象中
        byte[] bytes = "发送端B：你好，我想明天吃火锅ԅ(¯﹃¯ԅ)".getBytes(StandardCharsets.UTF_8);//这里还指定了编码格式，不过默认就是UTF-8
        //创建指定IP地址、指定端口、指定内容的DatagramPacket对象(包)
        //如果这里端口写 9998，并且在下面接收，就会发给自己
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("192.168.65.1"), 9999);
//        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getLocalHost(), 9999);//因主机的IP地址会经常改变

        //3. 发送信息
        datagramSocket.send(packet);

        //接收回复信息
        byte[] bytes1=new byte[1024];
        DatagramPacket receive = new DatagramPacket(bytes1, bytes1.length);
        datagramSocket.receive(receive);

        System.out.println(new String(receive.getData(),0,receive.getLength()));

        //关闭资源
        datagramSocket.close();
        System.out.println("发送端B退出了");
    }
}

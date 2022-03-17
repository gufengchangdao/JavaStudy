/*
    虽然叫接收端、发送端，但是哪一段都可以接收、发送
    UDP传输比TCP传输简单一些，因为IP、端口号、内容都封装到一个包内(DatagramPacket)了，接收也可以调用方法直接得到字节数组
        也没有TCP麻烦的连接，IO流输入输出，关闭IO流

 */
package javastudy.network_.socket_.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * UDP传输
 * 接收端A
 * 在端口<b>9999</>接收端口<b>9998</b>发来的信息，并回复
 */
public class UDPReceiverA {
    public static void main(String[] args) throws IOException {
        //1.创建一个DatagramSocket对象，准备在 9999 端口接收数据
        DatagramSocket datagramSocket = new DatagramSocket(9999);

        //2. 构建一个DatagramSocket对象，准备接收数据
        //因为一个数据包最大为64k,也就是 64 * 1024 个字节，也就是数组的最大值
        byte[] buf=new byte[1024];  //这里就输出几个字，用不了多大
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        //3. 调用接收方法，将通过网络传输的DatagramPacket对象填充到packet对象里面
        // 没有接收到信息的话，就会一直阻塞到这里
        System.out.println("接收端A等待接收数据...");
        datagramSocket.receive(packet);

        //4. 可以把packet进行拆包，取出数据
        int length= packet.getLength(); //实际接收到的数据字节长度
        byte[] data = packet.getData(); //接收到数据
        System.out.println(new String(data,0,length));

        //5. 回复信息
        byte[] data1 = "接收端A：好的，明天见！o(∩_∩)o".getBytes();
        DatagramPacket replyPacket = new DatagramPacket(data1, data1.length, InetAddress.getByName("192.168.65.1"), 9998);

        datagramSocket.send(replyPacket);

        //关闭资源
        datagramSocket.close();
        System.out.println("接收端A退出了");
    }
}

/*
InetAddress
    念: 爱内特..
    是Java包装用来表示IP地址的高级表示。几乎所有的Java网络相关的类都和它有关系
 */
package javastudy.network_;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class InetAddressDemo {
    public static void main(String[] args) throws UnknownHostException {

        //获取InetAddress对象
        //1. 获取本机的InetAddress对象
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost);//MS-EHPMGIRYIYCR/192.168.65.1    输出主机名(或域名)/IP地址

        //2. 根据指定主机名 获取 InetAddress对象
        InetAddress host1 = InetAddress.getByName("MS-EHPMGIRYIYCR");
        System.out.println(host1);//MS-EHPMGIRYIYCR/192.168.65.1

        //3. 根据域名 获取 InetAddress对象
        InetAddress host2 = InetAddress.getByName("www.baidu.com");
        System.out.println(host2);  //www.baidu.com/180.101.49.12       输出域名/IP地址


        //4. 通过 InetAddress对象 获取 对应的地址、主机/域名
        System.out.println("主机名: "+host1.getHostName() +",IP地址: "+host1.getHostAddress());
        System.out.println("域名: "+host2.getHostName() +",IP地址: "+host2.getHostAddress());

    }
}

/*
FileInputStream
    从文件系统中的文件获取输入字节
 */

package javastudy.iostream_.nodestream_;

import javastudy.iostream_.OpeTarget;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 演示FileInputStream的使用
 */
public class FileInputStreamDemo {
    public static void main(String[] args) {
        FileInputStream fileInputStream=null;

        try {
            fileInputStream=new FileInputStream(OpeTarget.SOURCE);
//            fileInputStream=new FileInputStream(new File(OpeTarget.SOURCE));

            //跳过并从输入流中丢弃 n字节的数据。
            fileInputStream.skip(3);

            //返回从此输入流中可以读取（或跳过）的剩余字节数的估计值，而不会被下一次调用此输入流的方法阻塞。
            System.out.println(fileInputStream.available());

            //读取文件内容
            //一个字节一个字节读取，由于汉字是三个字节，所以会乱码
            int date;
//            while ((date=fileInputStream.read())!=-1){
//                System.out.print((char) date);//返回的是int型，要转型
//            }

            byte[] b=new byte[8];//字节数组，注意每次读取8个字节，如果汉字被分次读取，那就是乱码了
            int len;            //存储读取的字节数
            while ((len=fileInputStream.read(b))!=-1){          // //每次读取到的字节都为[0,len),len之后的数据还是上次读取到的内容
//            while ((len=fileInputStream.read(b,2,6))!=-1){    //规定存放的起始下标和长度
                System.out.println(new String(b,0,len));//使用String的构造器输出
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream!=null) {
                try {
                    //关闭文件对象
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

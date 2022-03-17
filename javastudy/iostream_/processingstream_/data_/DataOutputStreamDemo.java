/*
DataInputStream能以一种与机器无关（当前操作系统等）的方式，直接从地从字节输入流读取JAVA基本类型和String类型的数据，常用于网络传输等（网络传
    输数据要求与平台无关）

 */

package javastudy.iostream_.processingstream_.data_;

import javastudy.iostream_.OpeTarget;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DataOutputStreamDemo {
    public static void main(String[] args) {
        DataOutputStream outputStream = null;
        DataInputStream inputStream=null;

        try {
            // 几种输出方式
            outputStream = new DataOutputStream(new FileOutputStream(OpeTarget.TARGET));
            // outputStream.writeBoolean(true);
            // outputStream.writeInt(20);

            // TODO 字符串输出的几种方式
            // write()写入字节数组，再用字节数组读取
            // outputStream.write("字符串变成字节再输出".getBytes(StandardCharsets.UTF_8));
            // 由于java的字符为Unicode编码，双字节的，writeBytes()只是将字符串的每一个字符的低字节内容写入，内容都是残缺的
            // outputStream.writeBytes("残缺的字符串");
            // writeChars()将字符串中的每一个字符的两个字节都写入，想读取出来难
            // outputStream.writeChars("另一种字符串输出");
            // 最好用的方式，writeUTF()是按照UTF编码后的字节长度写入，然后再将每一个字节的UTF编码写入，这样可以知道要读取内容的长度
            outputStream.writeUTF("输出字符串最常用的方法");


            inputStream=new DataInputStream(new FileInputStream(OpeTarget.SOURCE));
            // System.out.println(inputStream.readBoolean());
            // System.out.println(inputStream.readInt());

            // byte[] bytes=new byte[1024];
            // int len = inputStream.read(bytes);
            // System.out.println(new String(bytes,0,len));
            System.out.println(inputStream.readUTF());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                try {
                    if (outputStream!=null)
                    outputStream.close();
                    if (inputStream!=null)
                        inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }



    }
}

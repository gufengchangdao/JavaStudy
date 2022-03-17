/*
FileReader
    用于读取字符流
 */
package javastudy.iostream_.nodestream_;

import javastudy.iostream_.OpeTarget;

import java.io.FileReader;
import java.io.IOException;

/**
 * 演示FileReader的使用
 */
public class FileReaderDemo {
    public static void main(String[] args) {
        FileReader fileReader=null;

        try {
            fileReader=new FileReader(OpeTarget.SOURCE);
//            fileReader=new FileReader(new File(OpeTarget.SOURCE));

            fileReader.skip(10);//现在是跳过10个字符

            int date;
//            while ((date=fileReader.read())!=-1){   //一个字符一个字符读取
//                System.out.print((char) date);
//            }

            char[] cbuf=new char[8];    //字符数组
            int len;
//            while ((len=fileReader.read(cbuf,4,4))!=-1){
            while ((len=fileReader.read(cbuf))!=-1){
                System.out.print(new String(cbuf,0,len));   //还是调用String的构造方法输出
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader!=null)
                    fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

package javastudy.iostream_.nodestream_;

import javastudy.iostream_.OpeTarget;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 演示FileOutputStream的使用
 */
public class FileOutputStreamDemo {
    public static void main(String[] args) {
        FileOutputStream fileOutputStream=null;

        try {
            //创建对象
            fileOutputStream=new FileOutputStream(OpeTarget.TARGET);
//            fileOutputStream=new FileOutputStream(OpeTarget.TARGET,true);//追加模式，在文件末尾追加写
//            fileOutputStream=new FileOutputStream(new File(OpeTarget.TARGET));
//            fileOutputStream=new FileOutputStream(new File(OpeTarget.TARGET),true);

            //输出
            //将指定的字节写入此文件输出流。
            fileOutputStream.write('H');//char会自动转型为int

            byte[] b=new byte[]{'1','2','3','4'};
            fileOutputStream.write(b);//传入type数组
            fileOutputStream.write(b,0,b.length);//指定起始下标和偏移量

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream!=null)
//                    fileOutputStream.flush();//刷新此输出流并强制任何缓冲的输出字节被写出。

                    fileOutputStream.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

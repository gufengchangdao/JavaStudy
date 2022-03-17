//字节拷贝方式拷贝一个视频
package javastudy.iostream_.processingstream_.buffered_;

import javastudy.iostream_.OpeTarget;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 演示使用缓冲字节流拷贝文件
 */
public class BufferedCopy2 {
    public static void main(String[] args) {
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;

        try {
            inputStream = new BufferedInputStream(new FileInputStream(OpeTarget.SOURCE));
            outputStream = new BufferedOutputStream(new FileOutputStream(OpeTarget.TARGET));

            byte[] b=new byte[1024];
            int readLen;
            while ((readLen=inputStream.read(b))!=-1){
                outputStream.write(b,0,readLen);
            }
            System.out.println("COPY SUCCESS!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream!=null)
                    inputStream.close();
                if (outputStream!=null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

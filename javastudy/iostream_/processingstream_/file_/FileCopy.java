package javastudy.iostream_.processingstream_.file_;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 利用控制字节的读取、输出的类实现图片的拷贝
 */
public class FileCopy {
    public static void main(String[] args) {
        FileInputStream fileInputStream=null;
        FileOutputStream fileOutputStream=null;
        String srcPath="images\\4.jpg";
        String destPath="images\\4(副本).jpg";

        try {
            fileInputStream=new FileInputStream(srcPath);
            fileOutputStream=new FileOutputStream(destPath);

            byte[] b=new byte[1024];
            int len;
            while ((len=fileInputStream.read(b))!=-1){
                fileOutputStream.write(b,0,len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream!=null)
                    fileInputStream.close();
                if (fileOutputStream!=null)
                    fileOutputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}

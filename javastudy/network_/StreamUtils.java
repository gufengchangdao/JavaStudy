package javastudy.network_;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

    /**
     * 传入InputStream对象，以字节形式读取数据并返回一个字节数组
     * @param inputStream
     * @return ByteArray
     * @throws IOException
     */
    public static byte[] streamToByteArray (InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();   //创建输出流对象
        byte[] b=new byte[1024];
        int len;
        while ((len=inputStream.read(b))!=-1){
            baos.write(b,0,len);                                //把读取的数据写入baos
        }
        byte[] array=baos.toByteArray();                            //转换成字节数组，length正好为传入的字节数
        baos.close();
        return array;
    }
}

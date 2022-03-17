/*
InputStreamReader
    是从字节流到字符流的桥：它读取字节，并使用指定的charset将其解码为字符 。 它使用的字符集可以由名称指定，也可以被明确指定，或者可以接受平台的默认字符集
 */
package javastudy.iostream_.processingstream_.transformation_;

import javastudy.iostream_.OpeTarget;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileInputStream;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 演示转换流的使用，
 * 常用于将InputStream转换为Reader，这样就可以使用Reader的readLine()了
 */
public class InputStreamReaderDemo {
    public static void main(String[] args) {
        String filePath= OpeTarget.SOURCE; //这个文件用的是UTF_16的编码格式
        BufferedReader bufferedReader=null;
        try {
            //字节流转指定编码格式的字符流
            //这个是确定一个字符对应多少个字节
            //源文件编码格式为UTF_16，因此输入流的编码格式也应该转换为UTF_16(默认好像是utf-8，不转换会乱码)
            InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_16);

            //处理流包装该字符流
            bufferedReader = new BufferedReader(inputStreamReader);

            //输出
            String readLine;
            while ((readLine=bufferedReader.readLine())!=null){
                System.out.println(readLine);   //输出内容的编码格式为UTF_16
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader!=null) {
                try {
                    bufferedReader.close();//只用关闭最外流就可以了
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

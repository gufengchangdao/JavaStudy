/*
OutputStreamWriter
    可以创建内容为指定编码格式的文本文件
 */
package javastudy.iostream_.processingstream_.transformation_;

import javastudy.iostream_.OpeTarget;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class OutputStreamWriterDemo {
    public static void main(String[] args) throws IOException {
        String filePath= OpeTarget.SOURCE;

        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_16);

        osw.write("这个文本文件的编码格式为UTF-16");
        osw.close();

        System.out.println("成功创建一个编码格式为UTF-16的文件");
    }
}

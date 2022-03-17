/*
PrintStream
    字节打印流


 */
package javastudy.iostream_.processingstream_.print_;

import javastudy.iostream_.OpeTarget;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class PrintStreamDemo {
    public static void main(String[] args) throws IOException {

        //public static final PrintStream out = null;   out对象会在静态方法中用本地方法创建
        PrintStream out = System.out;
        out.print("你好呀");
        //public void print(String s) {
        //        write(String.valueOf(s));
        //}
        //print也是调用write打印的
//        out.write("你好呀".getBytes(StandardCharsets.UTF_8));    //设置指定的编码格式
        out.write("你好呀".getBytes());

        //默认是向显示屏打印的，setOut修改打印的位置
        //PrintStream可以传入打印的位置
        //public static void setOut(PrintStream out) {
        //  checkIO();
        //  setOut0(out);   //native方法，修改了out
        //}
        System.setOut(new PrintStream(OpeTarget.TARGET));
        System.out.write("向该文件打印这段话".getBytes());//内容将输出到指定文件
//        out.write("向该文件打印这段话".getBytes());//还会打印到显示屏上，可以看出setOut0是给out重新new了一个对象

    }
}

/*
BufferedReader
    从字符输入流读取文本，缓冲字符，以提供字符，数组和行的高效读取。
    public class BufferedReader extends Reader {
        private Reader in;
        ...
    }
 */
package javastudy.iostream_.processingstream_.buffered_;

import javastudy.iostream_.OpeTarget;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 演示BufferedReader的使用
 */
public class BufferedReaderDemo {
    public static void main(String[] args) {
        BufferedReader bufferedReader=null;

        try {
            //BufferedReader(Reader in)
            //只要是Reader的子类都可以
            bufferedReader=new BufferedReader(new FileReader(OpeTarget.SOURCE));

//            void flush()刷新流。
//            Stream<String> lines()返回一个 Stream ，其元素是从这个 BufferedReader读取的行。
//            void mark(int readAheadLimit)标记流中的当前位置。
//            boolean markSupported()告诉这个流是否支持mark（）操作。
//            boolean ready()告诉这个流是否准备好被读取。
//            void reset()将流重置为最近的标记。
//            long skip(long n)跳过字符


            //读取操作
//            System.out.println((char) bufferedReader.read());

//            char[] cbuf=new char[50];
//            int len;
//            while ((len=bufferedReader.read(cbuf))!=-1){
//                System.out.print(new String(cbuf,0,len));
//            }

            String line;
            while ((line=bufferedReader.readLine())!=null){
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader!=null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

/*
BufferedWriter
    newLine();//将会自动根据操作系统的不同，选择\r\n或者是\r或者是\n
    \r是回车，回到这一行的最左侧
    \n是换行，到下一行
    \r\n是到下一行的最左侧
    windows系统下默认两个是一个意思
 */

package javastudy.iostream_.processingstream_.buffered_;

import javastudy.iostream_.OpeTarget;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 演示BufferedWriter的使用
 */
public class BufferedWriterDemo {
    public static void main(String[] args) throws IOException{
        BufferedWriter bufferedWriter=null;

//            bufferedWriter=new BufferedWriter(new FileWriter(OpeTarget.TARGET,true));
            bufferedWriter=new BufferedWriter(new FileWriter(OpeTarget.TARGET));

        //void write(int c)
        //write(char[] cbuf, int off, int len)

        String content="这天下，唯我独尊，势必毁灭";
        for (int i = 0; i < 10; i++) {
            bufferedWriter.write(content);
            if (i!=9)
                bufferedWriter.newLine();//将会自动根据操作系统的不同，选择\r\n或者是\r或者是\n
        }
        bufferedWriter.close();

    }
}

/*
利用BufferedReader和BufferedWriter拷贝文本文件
    注意：不可以拷贝二进制文件，否则会导致拷贝到的文件损坏
 */
package javastudy.iostream_.processingstream_.buffered_;

import javastudy.iostream_.OpeTarget;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 演示使用缓冲字符流拷贝文件
 */
public class BufferedCopy {
    public static void main(String[] args) {
        BufferedReader bufferedReader=null;
        BufferedWriter bufferedWriter=null;

        try {
            bufferedReader=new BufferedReader(new FileReader(OpeTarget.SOURCE));
            bufferedWriter=new BufferedWriter(new FileWriter(OpeTarget.TARGET));

            String line;
            while ((line=bufferedReader.readLine())!=null){
                bufferedWriter.write(line);
                bufferedWriter.newLine();   //最后可能会多个换行
            }
            System.out.println("拷贝完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader!=null)
                    bufferedReader.close();
                if (bufferedWriter!=null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}



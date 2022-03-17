/*
FileWriter
    方便课写字符文件
 */
package javastudy.iostream_.nodestream_;

import javastudy.iostream_.OpeTarget;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 演示FileWriter的使用
 */
public class FileWriterDemo {
    public static void main(String[] args) {
        FileWriter fileWriter=null;

        try {
            //在创建文件时，没有该文件会创建新的文件
            fileWriter=new FileWriter(OpeTarget.TARGET);
//            fileWriter=new FileWriter(OpeTarget.TARGET,true);
//            fileWriter=new FileWriter(new File(OpeTarget.TARGET));
//            fileWriter=new FileWriter(new File(OpeTarget.TARGET),true);

            char c='我';
            fileWriter.write(c);

            char[] cbuf=new char[]{'是','个','笨','蛋'};
            fileWriter.write(cbuf,0,2);

            String str="天才不是我";
            fileWriter.write(str,0,2);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter!=null)
                    //注意：如果不关闭或者刷新，输出内容不会保存到文件内的
//                    fileWriter.flush();//刷新
                    fileWriter.close(); //关闭文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

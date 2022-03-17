/*
PrintWriter
    将对象的格式表示打印到文本输出流。可以打印到显示器上，也可以打印到文件上

 */
package javastudy.iostream_.processingstream_.print_;

import javastudy.iostream_.OpeTarget;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 演示PrintWriter的使用
 */
public class PrintWriterDemo {
	public static void main(String[] args) throws IOException {
		PrintWriter printWriter = null;
		// PrintWriter添加了复杂构造器，使得写入操作更简洁，不必再执行所有的装饰工作
		//创建对象
		// printWriter=new PrintWriter(OpeTarget.TARGET);                  //传入字符串
		// printWriter=new PrintWriter(new File(OpeTarget.TARGET));        //传入File
		// printWriter=new PrintWriter(System.out);                //可以把OutputStream子类作为参数
		printWriter = new PrintWriter(new FileWriter(OpeTarget.TARGET));//也可以把Writer子类传入

		printWriter.print("内容");
		printWriter.close();//打印后必须要关闭，因为需要把缓冲池中的内容输出出来
	}
}

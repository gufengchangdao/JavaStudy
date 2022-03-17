/*
Properties
    可以保存键值对到流中或从流中加载。 属性列表中的每个键及其对应的值都是一个字符串
    键值对不许需要有空格，值不需要用引号引起来，默认类型是String
    文件中的汉字会存储为Unicode编码
 */
package javastudy.collection_.properties_;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * 属性映射类Properties演示
 */
public class PropertiesTest01 {
    static String fillPath = "src/javastudy/collection_/properties_/test.properties";
    static Properties properties = null;

    public static void main(String[] args) {
        properties = new Properties();
        //创造properties文件
        // createPropertiesFile();
        //读取properties文件
        readPropertiesFile();

    }

    private static void createPropertiesFile() {
        //TODO (1) 添加集合到HashTable
        properties.setProperty("name", "小白");
        properties.setProperty("sal", "2000");
        properties.setProperty("age", "16");
        properties.setProperty("state", "sleep");

        try {
            // TODO (2) 将此属性列表（键和元素对）写入此 Properties表中
            // properties.store(new FileWriter(fillPath), null);//null表示不添加提示信息
            properties.store(new FileWriter(fillPath), "黑白无双里面的");//comments为配置文件的提示信息，放在数据的前面
            // properties.store(new FileOutputStream(fillPath), "黑白无双里面的"); //OutputStream子类

            //文件不用关闭
            System.out.println("保存配置文件成功~");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readPropertiesFile() {
        try {
            //TODO (3) 从输入字节流读取属性列表（键和元素对），加载配置文件
            properties.load(new FileReader(fillPath));          //load(Reader reader)
//            properties.load(new FileInputStream(fillPath));   //load(InputStream inStream)
            System.out.println("==文件加载成功！下面输出内容==");

            //TODO (4) 返回此属性列表中的一组键
            Set<String> strings = properties.stringPropertyNames();
            for (String x : strings) {
                System.out.println(x);
            }

            // TODO (5) 将此属性列表打印到指定的输出流
            //内容的开头都会有一段提示音：-- listing properties --
            //把k-v打印在控制台上
            properties.list(System.out);    //list(PrintStream out)
            //把k-v打印在指定文件中
//            PrintWriter printWriter=new PrintWriter("d:\\temp.txt");
//            properties.list(printWriter); //list(PrintWriter out)
//            printWriter.close();//注意要关闭该对象流

            //TODO (6) 使用此属性列表中指定的键搜索属性
            System.out.println("这个人叫 " + properties.getProperty("name"));
            // System.out.println("这个人叫 " + properties.getProperty("name","默认值")); //提供一个值不存在时的默认值
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

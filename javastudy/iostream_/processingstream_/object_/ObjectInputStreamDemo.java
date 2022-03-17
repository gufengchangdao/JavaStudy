package javastudy.iostream_.processingstream_.object_;

import javastudy.iostream_.OpeTarget;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 演示ObjectInputStream的使用
 */
public class ObjectInputStreamDemo {
    public static void main(String[] args) {
        ObjectInputStream ois=null;

        try {
            ois=new ObjectInputStream(new FileInputStream(OpeTarget.SOURCE));

            //输出时数据从左到右输出到文件中，读取也是从左到右读取，因此类型不能读取错
            int date1=ois.readInt();
            char date2=ois.readChar();
            boolean date3=ois.readBoolean();
            double date4=ois.readDouble();
            String date5=ois.readUTF();

            //读取是按照存储的对象对应的类的信息，想要调用其方法的话，不能用另外的类引用来接收，就算内容完全一样但是位置不同的类也不行
            try {
                Dog dog=(Dog) ois.readObject();
                //
                System.out.println(dog);//Dog{name='萌王', age=20, color='null', nation='null'}
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ois!=null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

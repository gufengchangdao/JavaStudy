package javastudy.errorProcess.exception_;

import java.io.IOException;
import java.util.Arrays;

/**
 * try-catch-finally的使用细节
 */
public class TryCatchFinally {
    /**
     * TODO finally主要用于清理资源，不要把改变控制流的语句(return、throw、break、continue放进finally中)
     * 在下面的例子中，try中的return执行后再方法返回前会先执行finally中的语句，如果finally中有return，就会覆盖掉原来的返回值
     * 甚至还会吞掉方法本来应该返回的异常
     */
    private static int test01() {
        try {
            return Integer.parseInt("zero");
        } finally {
            System.out.println("抛出异常"); //如果没有 return 0 的话就会先抛出异常在打印这句话
            return 0; //最后方法返回0
        }
    }

    /**
     * 使用Throwable类的addSuppressed()来解决异常覆盖问题
     */
    private static void test02() {
        //两层try语句模拟try和finally的异常都无法处理必须要抛出的情况
        try {
            IOException myException = null; //先准备一个用来抛出的异常
            try {
                throw new IOException("try中抛出的异常");
            } catch (IOException e) {
                myException = e;
            } finally {
                try {
                    throw new IOException("finally中也抛出了异常"); //try和finally中同时抛出异常，如果不处理的话，try中的异常就会被覆盖掉
                } catch (IOException e) {
                    if (myException == null)
                        myException = e;
                    else
                        myException.addSuppressed(e); //把新的异常加入进来
                }
                if (myException != null) //最后抛出
                    throw myException;
            }

        } catch (Exception e) { //表示外围的方法处理该异常
            e.printStackTrace(); //在输入异常时会打印所有的异常
            // TODO 使用getSuppressed()只能处理被抑制的异常
            // for (Throwable se : e.getSuppressed())
            //     se.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // test01();
        test02();
    }
}

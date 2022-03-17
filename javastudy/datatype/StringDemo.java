package javastudy.datatype;

import java.util.Date;

public class StringDemo {
    public static void main(String[] args) {

        // 使用 %数字$转换符 的格式来实现一个日期多次使用的功能，这样就不用多个参数需要提供多个日期
        System.out.printf("%1$s %2$tB %2$te %2$tY","Due date",new Date());


    }
}

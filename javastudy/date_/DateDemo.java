package javastudy.date_;

import java.util.Date;

public class DateDemo {
    public static void main(String[] args) {

        //创建对象
        Date date=null;
        date = new Date(); //当前时间
        long poiTime=(long) Math.pow(10,12);
        date = new Date(poiTime); //指定时间

        System.out.println(date.before(new Date()));
        System.out.println(date.after(new Date()));

        //重新设置时间
        date.setTime(poiTime);

        System.out.println(date.toString());//Sun Sep 09 09:46:40 CST 2001

    }
}

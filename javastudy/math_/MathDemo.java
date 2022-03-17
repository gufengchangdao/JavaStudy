package javastudy.math_;

public class MathDemo {
    public static void main(String[] args) {
        System.out.println(Math.PI);//3.141592653589793
        System.out.println(Math.E);//2.718281828459045

        System.out.println(Math.abs(-5.5));//5.5

        //将以弧度测量的角度转换为以度为单位的近似等效角度
        double degree=Math.toDegrees(Math.PI);
        //角度转弧度
        double radians=Math.toRadians(360D);
        System.out.println(Math.cos(Math.PI));//-1.0
        System.out.println(Math.acos(-1));//3.141592653589793
        System.out.println(Math.tan(Math.toRadians(45)));//0.9999999999999999

        //返回大于或等于参数的最小（最接近负无穷大） double值
        System.out.println(Math.ceil(5.5D));
        //返回小于或等于参数的最大（最接近正无穷大） double值
        System.out.println(Math.floor(5.50));//5.0
        //返回与参数最接近值的 double值，并且等于数学整数
        System.out.println(Math.rint(5.51));//6.0

        System.out.println(Math.exp(1));//2.718281828459045
        System.out.println(Math.log(Math.E));//1.0
        System.out.println(Math.log10(10));//1.0
        System.out.println(Math.pow(2,0));//
        //返回值为 double值为正号，大于等于 0.0 ，小于 1.0 。
        System.out.println(Math.random());//0.29632816254896843
        System.out.println(Math.max(1,5));//5
        System.out.println(Math.min(1,5));//1




    }
}

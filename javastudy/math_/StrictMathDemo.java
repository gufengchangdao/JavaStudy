package javastudy.math_;

public class StrictMathDemo {
    public static void main(String[] args) {
        // StrictMath类实现了可自由分发的数学库的算法，确保在所有平台上得到相同的结果
        // Math类也有很多方法是直接调用的StrictMath方法，而StrictMath方法都为native方法，是使用C/C++实现的底层方法
        System.out.println(StrictMath.PI);
        System.out.println(StrictMath.abs(-5));
    }
}

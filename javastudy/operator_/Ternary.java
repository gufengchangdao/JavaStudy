/*
 * 三元运算符
 */
package javastudy.operator_;

public class Ternary {
    public static void main(String[] args) {

        //三元运算符是一个整体
        Float a;
//        a=true?15F:10D;   //报错，虽然为true，但是存在double型数据，15F会被转换成15D

        //三元运算符可以充当if-else
        a = 10F;
        a = (a < 5) ? 10F : (a < 10 ? 10F : 20F);
        System.out.println(a);//20.0
    }
}

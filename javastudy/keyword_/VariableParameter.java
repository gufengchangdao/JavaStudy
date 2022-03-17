/*
java使用省略号代替多参数（参数类型... 参数名）
    1. 用数组包裹实参
        首先，为这个方法定义一个数组型的参数；然后在调用时，生成一个包含了所有要传递的实参的数组；最后，把这个数组作为一个实参传递过去。有效的达到“让方法可
            以接受个数可变的参数”的目的，只是调用时的形式不够简单。
    2. 定义实参个数可变的方法
        (1) 一个实参个数可变的方法
            private static int sumUp( int... values) {}
            注意：只有最后一个形参才能被定义成“能和不确定个实参相匹配”的。因此，一个方法里只能有一个这样的形参
        (1) 实参个数可变的方法的秘密形态
            定义了上面的方法就不能定义该方法了，private static int sumUp( int[] values) {}
        (1) 实例：
            ans=test.sumUp(1,2,3,4,5,6);                长度为6的参数数组
            ans=test.sumUp(new int[]{1, 2, 3, 4});      长度为4的参数数组，但这样写有些冗余
            ans=test.sumUp(null);                       参数数组为null
            ans=test.sumUp();                           参数数组长度为0，注意不为null
*/
package javastudy.keyword_;

/**
 * 可变参数的使用
 */
public class VariableParameter {
    public static void main(String[] args) {
        VariableParameter test = new VariableParameter();
        int ans;
        ans=test.sumUp(1,2,3,4,5,6);
        // ans=test.sumUp(new int[]{1, 2, 3, 4}); //可以把数组传入
        // ans=test.sumUp(null);
        // ans=test.sumUp();

        System.out.println("参数之和为 "+ans);
    }
    private int sumUp( int... values) {
        int len=(values==null)?0:values.length;
        int sum=0;
        for (int i = 0; i < len; i++) {
            sum+=values[i];
        }
        return sum;
    }
}

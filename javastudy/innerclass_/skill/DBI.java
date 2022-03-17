package javastudy.innerclass_.skill;

import java.util.ArrayList;

/**
 * 演示内部类语法中的双括号初始化
 */
public class DBI {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>() {{
            add(50);
            add(100);
        }};
        // TODO 外层括号建立了ArrayLIst的一个匿名子类，内层括号则是一个对象初始化块
    }
}

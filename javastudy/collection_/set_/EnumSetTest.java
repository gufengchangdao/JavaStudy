package javastudy.collection_.set_;

import java.util.EnumSet;

/**
 * 演示枚举集的使用
 */
public class EnumSetTest {
    public static void main(String[] args) {
        // TODO 创建枚举集
        // 指定枚举类的所有实例
        EnumSet<WeekDay> always = EnumSet.allOf(WeekDay.class);
        // 空的枚举集
        EnumSet<WeekDay> never = EnumSet.noneOf(WeekDay.class);
        // 指定范围
        EnumSet<WeekDay> range = EnumSet.range(WeekDay.MONDAY, WeekDay.FRIDAY);
        // 分别指定
        EnumSet<WeekDay> monday = EnumSet.of(WeekDay.MONDAY, WeekDay.TUESDAY, WeekDay.WEDNESDAY);

    }

    private enum WeekDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}

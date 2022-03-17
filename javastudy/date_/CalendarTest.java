package javastudy.date_;

import java.time.*;

/**
 * 使用LocalDate类编写一个日历程序
 * @version 1.5 2015-05-08
 * @author Cay Horstmann
 */
public class CalendarTest
{
   public static void main(String[] args)
   {
      LocalDate date = LocalDate.now();
      int month = date.getMonthValue(); //当前月数
      int today = date.getDayOfMonth(); //本月有多少天

      date = date.minusDays(today - 1); // 减去today-1天，即得到这个月的第一天
      DayOfWeek weekday = date.getDayOfWeek(); //本周多少天，计算从1号开始这一周还有多少天
      int value = weekday.getValue(); // 计算今天是星期几，值为1-7

      System.out.println("Mon Tue Wed Thu Fri Sat Sun");
      for (int i = 1; i < value; i++)
         System.out.print("    ");
      while (date.getMonthValue() == month) //遍历本月的所有天数
      {
         System.out.printf("%3d", date.getDayOfMonth());
         if (date.getDayOfMonth() == today) //今天
            System.out.print("*");
         else
            System.out.print(" ");
         date = date.plusDays(1); //天数加一
         if (date.getDayOfWeek().getValue() == 1) System.out.println(); //到了周一
      }
      if (date.getDayOfWeek().getValue() != 1) System.out.println();
   }
}

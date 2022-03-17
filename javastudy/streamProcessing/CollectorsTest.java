/*
Collectors类

(1) Collectors类为收集器类，可以将Stream流对象进行各种各样的封装、归集、分组等操作
(2) 类似于mysql中的聚合函数
*/
package javastudy.streamProcessing;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 演示收集器类的使用
 */
public class CollectorsTest {
    public static void main(String[] args) {
        Stream<Employee> stream = Employee.getEmpList().stream();

        // TODO averagingDouble计算double类型流元素的平均值
        // Double ave = stream.collect(Collectors.averagingDouble(n -> (double) n.getSalary()));
        // System.out.println("员工平均薪水："+ave);

        // TODO counting统计元素个数，和stream的count差不多
        // System.out.println("员工总人数："+stream.collect(Collectors.counting()));

        // TODO maxBy、minBy 求符合最值条件的元素，跟stream的max和min很像
        // Optional<Employee> collect = stream.collect(Collectors.maxBy(Comparator.comparing(Employee::getAge)));
        // Optional<Employee> collect = stream.max(Comparator.comparing(Employee::getAge));

        // TODO summingDouble计算double类型数据的总和
        // Double collect = stream.collect(Collectors.summingDouble(Employee::getSalary)); //计算员工总薪水
        // Double collect = stream.mapToDouble(Employee::getSalary).sum(); //先映射为薪水的DoubleStream类型流对象，再求和

        // TODO summarizingDouble创建统计对象，利用summarizingDouble()的方法获取员工的各方面的统计数据(每一个员工的double类型的字段)
        // DoubleSummaryStatistics collect = stream.collect(Collectors.summarizingDouble(Employee::getSalary));
        // System.out.println("员工最高薪水为"+collect.getMax()+"，最低薪水为"+collect.getMin());
        // System.out.println("员工个数为"+collect.getCount()+"，员工总薪水为"+collect.getSum()+"，平均薪水为"+collect.getAverage());

        // TODO joining()将指定属性用分隔符拼接起来
        // String names = stream.map(Employee::getName).collect(Collectors.joining("，"));
        // System.out.println("所有员工为 "+names);

        // TODO groupingBy()将对象进行分组
        // 一级分组，按性别分组
        // Function<Employee, Character> sexFunc = Employee::getSex; //获取这个方法
        // 这个分组返回一个map，集合的键是对应字段值，值就是分组结果
        // Map<Character, List<Employee>> collect = stream.collect(Collectors.groupingBy(sexFunc));

        //多级分组，先按性别分组，再按名字长度分组
        Function<Employee, Character> sexFunc = Employee::getSex;                           //先按性别分组
        Function<Employee, Integer> nameLenFunc=employee -> employee.getName().length();    //再按名字长度分组
        Map<Character, Map<Integer, List<Employee>>> collect = stream.collect(
                Collectors.groupingBy(sexFunc, Collectors.groupingBy(nameLenFunc))); //甚至可以无限套娃
        //方法返回的是嵌套的map，注意groupingBy()方法的第二个参数是另一个groupingBy() --> 当前分组数据包含在其他分组的结果中
    }
}

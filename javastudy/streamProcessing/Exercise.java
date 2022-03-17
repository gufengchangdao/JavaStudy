package javastudy.streamProcessing;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 流处理的练习和总结
 */
public class Exercise {
    public static void main(String[] args) {
        /*
        * 总结：
        *  1. Function、Predicate等这些是函数式接口，可以先写好后作为参数传入
        *  2. Stream就像sql语句一样，对数据进行操作，方法的参数可以是new一个函数对象或者lambda表达式或者Function、Predicate这些函数式接口的对象
        *  3. Collectors提供了很多静态方法，类似sql语句的聚合函数
        *  4. 对数据进行查询、筛选、分组等都会用到lambda表达式，虽然传入对象也可以，但是lambda表达式最简洁
        */

        List<Employee> empList = Employee.getEmpList();
        // 查询男员工的人数
        System.out.println(empList.stream().filter(employee -> employee.getSex()=='男').count());
        System.out.println(empList.stream().filter(employee -> employee.getSex()=='男').collect(Collectors.counting()));

        // 查询薪水大于5000的女员工的数量
        System.out.println(empList.stream().filter(employee -> {
            return employee.getSex()=='女'&&employee.getSalary()>5000;
        }).count());

    }
}
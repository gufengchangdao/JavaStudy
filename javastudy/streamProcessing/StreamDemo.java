/*
流处理
1. Stream是Java8新增的一个接口，允许以声明性方式处理数据集合。Stream不是一个集合类型不保存数据，可以把它看作是遍历数据集合的高级迭代器(Iterator)。
2. Steam的很多方法都是只能使用一次并且会返回Stream对象，可以做到像ThinkPHP对数据操作的链式查询一样，形成一条流水线。
3. 保护数据源，对Stream中任何元素的修改都不会导致数据源被修改，比如过滤删除流中的一个元素，再次遍历该数据源依然可以获取该元素
4. Stream与lambda表达式密不可分，很多方法参数既支持(接口的)匿名函数，又支持lambda表达式，而且往往lambda更简短
5. 不同于集合遍历元素使用的外部迭代，流遍历使用内部迭代，这样的优点总结有
    + 项目可以透明地并行处理，你就不用再编写复杂的同步代码
    + 以更优化的顺序进行处理，stream库的内部迭代可以自动选择一种适合你硬件的数据表示和并行实现
*/
package javastudy.streamProcessing;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 演示Stream流对象的多个方法
 */
public class StreamDemo {
    public static void main(String[] args) {
        // TODO 创建Stream实例
        //(1) 使用指定值创建Stream实例
        // Stream<String> stream = Stream.of("Hello", "你好","🚀");
        // Stream<Integer> stream = Stream.of(1, 2, 3);

        //(2)使用数组创建Stream实例
        // int[] arr={1,2,3};
        // IntStream stream = Arrays.stream(arr);


        //(2)使用集合创建Stream实例(常用方式)
        // 获取员工数据集合
        List<Employee> employees = Employee.getEmpList();
        // 获取集合流对象
        Stream<Employee> stream = employees.stream();

        // Stream接口常用方法
        //TODO 终端操作(消费流，使用过后流对象就不能执行其他操作了)

        // TODO count获取流中元素个数
        // System.out.println(stream.count());

        // TODO forEach遍历流中每一个元素，执行action方法
        // stream.forEach(System.out::println);    //静态引用写法，打印出来
        // stream.forEach(n->{                     //lambda表达式
        //     System.out.println(n);
        // });

        // 根据比较器规则，获取流中最大元素
        //查找薪水最大的数据
        // Optional<Employee> maxSal = stream.max((o1, o2) -> (int) (o1.getSalary() - o2.getSalary() + 0.5));
        // System.out.println(maxSal);

        // TODO sorted根据比较器规则，对流中元素进行排序，返回一个新的流对象
        //薪水由低到高排序
        // Stream<Employee> sorted = stream.sorted((o1, o2) -> (int) (o1.getSalary() - o2.getSalary() + 0.5));

        // TODO 中间操作(中间操作会返回另外一个流，多个中间操作可以连接起来形成一个查询)
        //  中间操作有惰性，如果流上没有一个终端操作，那么中间操作是不会做任何处理的。

        // TODO map是将输入流中每一个元素映射为另一个元素形成输出流，需要传入一个Function函数
        //将Employee对象映射为String对象了
        Stream<String> stringStream = stream.map(Employee::getName);
        // DoubleStream doubleStream = stream.mapToDouble(Employee::getSalary);

        // TODO distinct去除流流中重复对象
        // Stream<Employee> distinct = stream.distinct();

        // TODO filter对流中数据进行筛选
        // Stream<Employee> employeeStream = stream.filter(employee-> employee.getSex() == '女');

        //流的过滤和封装链式操作
        // System.out.println(stream.filter(n->n.getSex()=='女').collect(Collectors.toList()));

        // TODO limit返回流中前n个数据
        // Stream<Employee> limit = stream.limit(3);

        // TODO skip忽略前n个元素
        // List<Employee> list = stream.skip(4).collect(Collectors.toList());//跳过了前4个数据
        // for (Employee employee : list)
        //     System.out.println(employee);

        // TODO 数据查找
        // TODO allMatch判断是否所有元素都符合条件，返回boolean
        // boolean result = stream.allMatch(employee -> employee.getAge() > 18);
        // System.out.println("所有员工年龄是否都大于18？ "+result);

        // TODO noneMatch判断是否所有元素都不符合条件
        // boolean result = stream.noneMatch(employee -> employee.getSalary() < 4000);
        // System.out.println("是否所有员工薪水都不小于4000？ "+result);

        // TODO findFirst返回流对象中的第一个元素，很像sql语句中的find()，返回类型为Optional
        Optional<Employee> first = stream.findFirst();
        first.ifPresent(System.out::println); //数据存在就会执行参数里面的表达式
    }
}


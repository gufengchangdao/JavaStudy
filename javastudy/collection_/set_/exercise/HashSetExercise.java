package javastudy.collection_.set_.exercise;

import java.util.HashSet;
import java.util.Objects;

/**
 * 课后练习要求：
 * 创建 3 个 Employee 对象放入HashSet中
 * 只有当name和birthday的值相同的时候才认为是相同员工，不能添加到HashSet集合中去
 */
public class HashSetExercise {
    public static void main(String[] args) {
        /*解读：
            因为HashMap的putVal方法判断的依据是 哈希值、equals(== 就不说了)
            所以要让hashCode值依据name和birthday的值设置，过了第一关，再让equals依据name和birthday的值判断
         */

        HashSet<Employee> hashSet = new HashSet();

        hashSet.add(new Employee("Tom",5000,new MyDate(2021,5,12)));    //T
        hashSet.add(new Employee("Aria",5000,new MyDate(2021,5,12)));   //T
        hashSet.add(new Employee("Tom",5000,new MyDate(2021,5,12)));    //F
        hashSet.add(new Employee("Tom",5000,new MyDate(2021,12,12)));   //T
        hashSet.add(new Employee("Tom",9000,new MyDate(2021,5,12)));    //F

        System.out.println(hashSet);

    }
}

class Employee{
    private String name;
    private int sal;
    private MyDate birthday;

    public Employee(String name, int sal, MyDate birthday) {
        this.name = name;
        this.sal = sal;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "[" + name + "," + sal +","+ birthday +
                "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        //Objects.equals方法会调用参数的equals方法的
        return Objects.equals(name, employee.name) && Objects.equals(birthday, employee.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday);    //这里不能加sal
    }
}
class MyDate{
    private int year;
    private int month;
    private int day;

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public String toString() {
        return year + "," + month + "," + day;
    }
    //因为Employee的equals方法和hashCode方法要根据birthday属性来判断，所以MyDate的两个方法也要重写
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //同一对象
        if (o == null || getClass() != o.getClass()) return false;  //不是同一个类
        MyDate myDate = (MyDate) o;
        return year == myDate.year && month == myDate.month && day == myDate.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day); //让hashCode的值只year, month, day有关，三者都相同的时候，hashCode也一定相同
    }
}



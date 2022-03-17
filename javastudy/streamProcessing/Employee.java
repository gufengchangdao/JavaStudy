package javastudy.streamProcessing;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用，提供一个getEmpList()方法来获得一些员工数据
 */
public class Employee {
    private String name;
    private int age;
    private float salary;
    private char sex;

    public Employee() {
    }

    public Employee(String name, int age, float salary, char sex) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", sex=" + sex +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public float getSalary() {
        return salary;
    }

    public char getSex() {
        return sex;
    }

    public static List<Employee> getEmpList() {
        ArrayList<Employee> list = new ArrayList<>();
        list.add(new Employee("琪亚娜", 20, 6700,'女'));
        list.add(new Employee("浮华", 20, 8000,'女'));
        list.add(new Employee("姬子上校", 20, 12000,'女'));
        list.add(new Employee("绯木修", 20, 9700,'男'));
        list.add(new Employee("哪类多", 25, 6800,'男'));
        list.add(new Employee("空", 18, 4200,'男'));
        list.add(new Employee("桐人", 20, 7000,'男'));
        return list;
    }
}
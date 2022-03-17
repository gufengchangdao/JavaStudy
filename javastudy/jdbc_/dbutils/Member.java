package javastudy.jdbc_.dbutils;

import java.util.Date;

/**
 * 该类用来存放collapse数据库中member表中的数据
 * 每个对象对应一行数据，我称之为数据对象化(模型类)
 */
public class Member {
    //储存数据的类要求:
    // 类中属性名要和对应数据的字段名相等，否则不会赋值
    // 对于想要赋值的属性需要定义set..()方法，无论该属性是否是public的，没有赋值的属性就是默认的值
    // 数据库中的char数据用String类型替代，不能使用char属性，但是可以使用int、float...，会自动转型，但是最好直接写成Integer、Float...
    // date数据就用Date对象替代
    private String id;
    private String name;
    private String department;
    private Integer salary;
    private String job;
    private Integer skin_num;
    private Date create_time;
    // private String create_time;


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setSkin_num(Integer skin_num) {
        this.skin_num = skin_num;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                ", job='" + job + '\'' +
                ", skin_num=" + skin_num +
                ", createTime=" + create_time +
                '}';
    }
}

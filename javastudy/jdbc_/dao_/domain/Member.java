package javastudy.jdbc_.dao_.domain;

import java.util.Date;

/**
 * 该类用来存放collapse数据库中member表中的数据
 * 每个对象对应一行数据，我称之为数据对象化(模型类)
 */
public class Member {
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public Integer getSalary() {
        return salary;
    }

    public String getJob() {
        return job;
    }

    public Integer getSkin_num() {
        return skin_num;
    }

    public Date getCreate_time() {
        return create_time;
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

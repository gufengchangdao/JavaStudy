package javastudy.jdbc_.dao_.domain;

/**
 * 对应数据库collapse中的Department表
 */
public class Department {
    private String id;
    private String department;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}

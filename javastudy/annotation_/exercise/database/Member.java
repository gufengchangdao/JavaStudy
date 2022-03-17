//: annotations/database/Member.java
package javastudy.annotation_.exercise.database;

import javastudy.annotation_.exercise.database.type.*;

/**
 * 用于生成数据表的模板，这里只使用它的字段
 */
@DBTable(name = "member")
public class Member {
	@SQLInteger()
	private int id;
	@SQLChar(value = 10)
	private String name;
	@SQLString(value = 255)
	private String department;
	@SQLInteger(typeName = "MEDIUMINT")
	private int salary;
	@SQLText()
	private String job;
	@SQLInteger(typeName = "TINYINT")
	private int skin_num;
	@SQLTimestamp()
	private long create_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getSkin_num() {
		return skin_num;
	}

	public void setSkin_num(int skin_num) {
		this.skin_num = skin_num;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
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
				", create_time=" + create_time +
				'}';
	}
} ///:~

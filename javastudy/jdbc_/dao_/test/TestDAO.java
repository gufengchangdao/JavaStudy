package javastudy.jdbc_.dao_.test;

import javastudy.jdbc_.dao_.dao.DepartmentDAO;
import javastudy.jdbc_.dao_.dao.MemberDAO;
import javastudy.jdbc_.dao_.domain.Department;
import javastudy.jdbc_.dao_.domain.Member;
import org.junit.Test;

import java.util.List;

/**
 * 测试类
 */
public class TestDAO {
    @Test
    public void test01() {
        MemberDAO memberDAO = new MemberDAO();
        //查询多行
        String sql = "select * from member";
        // Class<?> aClass = Class.forName("javastudy.jdbc_.dao_.domain.Member");//获得Member的类对象
        List<Member> members = memberDAO.queryMulti(sql, Member.class);//查询语句中没有 ? ，也就不用传参数
        for (Member member : members) {
            System.out.println(member);
        }

        //查询单行
        sql = "select * from member where `name`=?";
        Member member = memberDAO.querySingle(sql, Member.class, "雷电芽衣");
        System.out.println(member);
    }

    @Test
    public void test02() {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        String sql = "select * from department";
        List<Department> departments = departmentDAO.queryMulti(sql, Department.class);
        for (Department x : departments) {
            System.out.println(x);
        }

    }
}

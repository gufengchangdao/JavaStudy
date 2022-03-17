package javastudy.errorProcess.assert_;

/**
 * 断言的使用
 */
public class AssertTest {
    public static void main(String[] args) {
        // new Object(){}.getClass().getEnclosingClass().getClassLoader()
        AssertTest.class.getClassLoader().setDefaultAssertionStatus(false);

        int a = 0;

        assert a != 0; //断言失败就抛出异常
        // assert a != 0 : "你好垃圾"; //断言失败就抛出异常并输出 : 后面的内容

        /*
        开启断言后编译后的代码：
        try {
            assert a != 0;
        } catch (Exception var3) {
            var3.printStackTrace();
        }
        */

        System.out.println(a);
    }
}


package javastudy.errorProcess.exception_;

/**
 * 重新抛出异常
 */
public class Rethrowing {
	public static void f() throws Exception {
		throw new Exception("thrown from f()");
	}

	public static void g() throws Exception {
		try {
			f();
		} catch (Exception e) {
			System.out.println("Inside g(),e.printStackTrace()");
			e.printStackTrace(System.out);
			throw e;
		}
	}

	public static void h() throws Exception {
		try {
			f();
		} catch (Exception e) {
			System.out.println("Inside h(),e.printStackTrace()");
			e.printStackTrace(System.out);
			// TODO: 2022/2/15 0015 fillInStackTrace()从当前位置重新抛出异常
			throw (Exception) e.fillInStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			g();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		System.out.println("---------------------------------");
		try {
			h();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
}
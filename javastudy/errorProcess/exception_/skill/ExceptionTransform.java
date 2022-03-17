package javastudy.errorProcess.exception_.skill;

import java.io.FileNotFoundException;

/**
 * 转换异常对象，将检查型异常转为非检查型异常抛出
 */
public class ExceptionTransform extends Parent {
	@Override
	public void cctv() throws RuntimeException {
		try {
			throw new FileNotFoundException();
		} catch (FileNotFoundException e) {
			// TODO 子类重写方法时捕获检查型异常不能处理又不能直接抛出可以转换为运行时异常抛出
			// throw new RuntimeException(e.getMessage());//会损失原始异常的轨迹
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		try {
			new ExceptionTransform().cctv();
		} catch (RuntimeException e) {
			e.getCause().printStackTrace(); //使用getCause()得到原本异常对象
		}
	}
}

class Parent {
	public void cctv() {
	}
}

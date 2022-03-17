package javastudy.errorProcess.exception_;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * 简单的自定义异常类
 */
public class LoggingException extends Exception {
	private static Logger logger = Logger.getLogger("javastudy.errorProcess.exception_.LoggingException");

	public LoggingException() {
		StringWriter trace = new StringWriter();
		printStackTrace(new PrintWriter(trace)); //传入输出流，栈轨迹信息会写入进去
		logger.severe(trace.toString()); //生成日志
	}

	public static void main(String[] args) {
		try {
			throw new LoggingException();
		} catch (LoggingException e) {
			System.err.println("捕获异常: " + e);
		}
	}
}


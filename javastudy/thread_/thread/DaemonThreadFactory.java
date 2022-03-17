package javastudy.thread_.thread;

import java.util.concurrent.ThreadFactory;

/**
 * 实现ThreadFactory接口生成线程工厂类，以便便捷获取守护线程类
 */
public class DaemonThreadFactory implements ThreadFactory {
	// TODO: 2022/3/5 ThreadFactory接口提供的newThread()可以帮助获取指定样式的线程类，像初始化，优先级，线程名，守护线程等
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	}
}

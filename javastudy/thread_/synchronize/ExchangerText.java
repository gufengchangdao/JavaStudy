package javastudy.thread_.synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ExchangerProducer implements Runnable {
	private Exchanger<List<Integer>> exchanger;
	private List<Integer> produce;
	private int count; //每次的生产上限

	public ExchangerProducer(Exchanger<List<Integer>> exchanger, List<Integer> list, int count) {
		this.exchanger = exchanger;
		this.produce = list;
		this.count = count;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				for (int i = 0; i < count; i++) {
					System.out.println("生产对象 " + i);
					produce.add(i);
				}
				// 等待另一个线程到达此交换点，然后将给定对象传输给它，接收其对象作为回报。如果没有线程到达交换点会阻塞在这里
				produce = exchanger.exchange(produce);//每次要等消费者调用exchange()
			}
		} catch (InterruptedException e) {
			System.out.println("停止生产对象");
		}
	}
}

class ExchangerConsumer implements Runnable {
	private Exchanger<List<Integer>> exchanger;
	private List<Integer> consume;

	public ExchangerConsumer(Exchanger<List<Integer>> exchanger, List<Integer> list) {
		this.exchanger = exchanger;
		this.consume = list;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				if (consume.isEmpty()) //只有队列中没有可消费的元素才交换
					consume = exchanger.exchange(consume);
				Integer integer = consume.remove(0);
				System.out.println("消费对象 " + integer);
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("停止消费对象");
		}
	}
}

/**
 * 演示Exchanger的使用，模拟创建对象和消费对象同时进行
 */
public class ExchangerText {
	/*
	Exchanger
	1. Exchanger是可以在两个任务之间交换对象的栅栏。每个线程在输入exchange方法时提供一些对象，与合作者线程匹配，并在返回时接收其合作伙伴的对象。
	   交换器可以被视为一个的双向形式SynchronousQueue 。
	 */
	public static void main(String[] args) throws InterruptedException {
		Exchanger<List<Integer>> exchanger = new Exchanger<>();
		// 创建两个队列，一个队列用于给生产者线程生产对象，一个队列用于消费者线程消费
		// 使用CopyOnWriteArrayList可以在遍历的时候对列表进行修改，不过这里直接调用的remove()，不是在遍历的时候删除，用不着
		// CopyOnWriteArrayList<Integer>
		// 		productList = new CopyOnWriteArrayList<>(),
		// 		consumeList = new CopyOnWriteArrayList<>();
		ArrayList<Integer>
				productList = new ArrayList<>(),
				consumeList = new ArrayList<>();
		ExchangerProducer producer = new ExchangerProducer(exchanger, productList, 10);
		ExchangerConsumer consumer = new ExchangerConsumer(exchanger, consumeList);
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.execute(producer);
		pool.execute(consumer);
		TimeUnit.SECONDS.sleep(5);
		pool.shutdownNow();
	}
}

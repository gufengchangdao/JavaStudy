package javastudy.thread_.exercise.emulation;
// Using queues and multithreading.
// {Args: 5}

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 顾客，只读对象，不需要同步
class Person {
	private final int serviceTime;

	public Person(int tm) {
		serviceTime = tm;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public String toString() {
		return "[" + serviceTime + "]";
	}
}

// 待服务的顾客列表
class CustomerLine extends ArrayBlockingQueue<Person> {
	public CustomerLine(int maxLineSize) {
		super(maxLineSize);
	}

	public String toString() {
		if (this.size() == 0)
			return "[Empty]";
		StringBuilder result = new StringBuilder();
		for (Person customer : this)
			result.append(customer);
		return result.toString();
	}
}

// 随机的向顾客列表中添加顾客
class CustomerGenerator implements Runnable {
	private CustomerLine customers;
	private static Random rand = new Random(47);

	public CustomerGenerator(CustomerLine cq) {
		customers = cq;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(rand.nextInt(300));
				customers.put(new Person(rand.nextInt(1000)));
			}
		} catch (InterruptedException e) {
			System.out.println("CustomerGenerator interrupted");
		}
		System.out.println("CustomerGenerator terminating");
	}
}

// 出纳员，用于服务顾客
class Teller implements Runnable, Comparable<Teller> {
	private static int counter = 0;
	private final int id = counter++;
	private int customersServed = 0; //服务的顾客计数
	private CustomerLine customers;
	private boolean servingCustomerLine = true; //是否在服务顾客

	public Teller(CustomerLine cq) {
		customers = cq;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				Person customer = customers.take(); //服务一个顾客
				TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
				synchronized (this) {
					customersServed++;
					while (!servingCustomerLine) //做其他事情时不会终止run()，而是挂起
						wait();
				}
			}
		} catch (InterruptedException e) {
			System.out.println(this + "interrupted");
		}
		System.out.println(this + "terminating");
	}

	// 停止服务顾客
	public synchronized void doSomethingElse() {
		customersServed = 0;
		servingCustomerLine = false;
	}

	// 开始服务顾客
	public synchronized void serveCustomerLine() {
		assert !servingCustomerLine : "already serving: " + this;
		servingCustomerLine = true;
		notifyAll(); // 其实就只是唤醒一个线程
	}

	public String toString() {
		return "Teller " + id + " ";
	}

	public String shortString() {
		return "T" + id;
	}

	// 用于优先队列
	public synchronized int compareTo(Teller other) {
		return Integer.compare(customersServed, other.customersServed);
	}
}

class TellerManager implements Runnable {
	private ExecutorService exec;
	private CustomerLine customers;
	// 正在工作的，优先使用服务数量最少的出纳员服务顾客
	private PriorityQueue<Teller> workingTellers = new PriorityQueue<Teller>();
	// 正在做其他事情的
	private Queue<Teller> tellersDoingOtherThings = new LinkedList<Teller>();
	private int adjustmentPeriod;
	private static Random rand = new Random(47);

	public TellerManager(ExecutorService e, CustomerLine customers, int adjustmentPeriod) {
		exec = e;
		this.customers = customers;
		this.adjustmentPeriod = adjustmentPeriod;
		// 初始时只有一个出纳员
		Teller teller = new Teller(customers);
		exec.execute(teller);
		workingTellers.add(teller);
	}

	/**
	 * 重点方法：根据顾客数量调整出纳员数量
	 */
	public void adjustTellerNumber() {
		// This is actually a control system. By adjusting
		// the numbers, you can reveal stability issues in
		// the control mechanism.
		// 如果顾客太多，让未工作的出纳员工作或增加出纳员
		if (customers.size() / workingTellers.size() > 2) {
			// If tellers are on break or doing
			// another job, bring one back:
			if (tellersDoingOtherThings.size() > 0) {
				Teller teller = tellersDoingOtherThings.remove();
				teller.serveCustomerLine();
				workingTellers.offer(teller);
				return;
			}
			// Else create (hire) a new teller
			Teller teller = new Teller(customers);
			exec.execute(teller);
			workingTellers.add(teller);
			return;
		}
		// 如果顾客太少，减少工作的出纳员
		if (workingTellers.size() > 1 && customers.size() / workingTellers.size() < 2)
			reassignOneTeller();
		// 如果没有顾客，那么只需要一个出纳员
		if (customers.size() == 0)
			while (workingTellers.size() > 1)
				reassignOneTeller();
	}

	// 让一个出纳员做其他工作或休息
	private void reassignOneTeller() {
		Teller teller = workingTellers.poll();
		teller.doSomethingElse();
		tellersDoingOtherThings.offer(teller);
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
				adjustTellerNumber();
				System.out.print(customers + " { ");
				for (Teller teller : workingTellers)
					System.out.print(teller.shortString() + " ");
				System.out.println("}");
			}
		} catch (InterruptedException e) {
			System.out.println(this + "interrupted");
		}
		System.out.println(this + "terminating");
	}

	public String toString() {
		return "TellerManager ";
	}
}

/**
 * 银行出纳员仿真
 * 程序线程梳理：
 *  主线程：             开启其他线程和中断所有线程
 *  随机生成顾客的线程：   随机生成顾客并添加到阻塞队列中
 *  出纳员管理线程：      开启出纳员线程，并不断根据顾客数量调整收纳员工作人数
 *  数个出纳员服务线程：   服务顾客
 */
public class BankTellerSimulation {
	static final int MAX_LINE_SIZE = 50;
	static final int ADJUSTMENT_PERIOD = 1000; //调整收纳员工作人数的周期

	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		// If line is too long, customers will leave:
		CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
		exec.execute(new CustomerGenerator(customers));
		// Manager will add and remove tellers as necessary:
		exec.execute(new TellerManager(exec, customers, ADJUSTMENT_PERIOD));
		if (args.length > 0) // Optional argument
			TimeUnit.SECONDS.sleep(Integer.parseInt(args[0]));
		else {
			System.out.println("Press 'Enter' to quit");
			System.in.read();
		}
		exec.shutdownNow();
	}
} /* Output: (Sample)
[429][200][207] { T0 T1 }
[861][258][140][322] { T0 T1 }
[575][342][804][826][896][984] { T0 T1 T2 }
[984][810][141][12][689][992][976][368][395][354] { T0 T1 T2 T3 }
Teller 2 interrupted
Teller 2 terminating
Teller 1 interrupted
Teller 1 terminating
TellerManager interrupted
TellerManager terminating
Teller 3 interrupted
Teller 3 terminating
Teller 0 interrupted
Teller 0 terminating
CustomerGenerator interrupted
CustomerGenerator terminating
*///:~

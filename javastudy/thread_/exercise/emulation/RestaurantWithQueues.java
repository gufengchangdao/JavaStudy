//: concurrency/restaurant2/RestaurantWithQueues.java
// {Args: 5}
package javastudy.thread_.exercise.emulation;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

// 食谱
interface Food {
	enum Appetizer implements Food { //继承自己后可以使用Food引用，多个枚举达到枚举分组的效果
		SALAD, SOUP, SPRING_ROLLS;
	}

	enum MainCourse implements Food {
		LASAGNE, BURRITO, PAD_THAI,
		LENTILS, HUMMOUS, VINDALOO;
	}

	enum Dessert implements Food {
		TIRAMISU, GELATO, BLACK_FOREST_CAKE,
		FRUIT, CREME_CARAMEL;
	}

	enum Coffee implements Food {
		BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
		LATTE, CAPPUCCINO, TEA, HERB_TEA;
	}
}

enum Course {
	APPETIZER(Food.Appetizer.class),
	MAINCOURSE(Food.MainCourse.class),
	DESSERT(Food.Dessert.class),
	COFFEE(Food.Coffee.class);
	private Food[] values;

	private static Random random = new Random();

	Course(Class<? extends Food> kind) {
		values = kind.getEnumConstants();
	}

	public Food randomSelection() {
		return values[random.nextInt(values.length)];
	}
}

// 订单，由服务员创建，交给厨师
class Order { // (A data-transfer object)
	private static int counter = 0;
	private final int id = counter++;
	private final Customer customer;
	private final WaitPerson waitPerson;
	private final Food food;

	public Order(Customer cust, WaitPerson wp, Food f) {
		customer = cust;
		waitPerson = wp;
		food = f;

	}

	public Food item() {
		return food;
	}

	public Customer getCustomer() {
		return customer;
	}

	public WaitPerson getWaitPerson() {
		return waitPerson;
	}

	public String toString() {
		return "Order: " + id + " item: " + food +
				" for: " + customer +
				" served by: " + waitPerson;
	}
}

// 一盘菜，由厨师创建
class Plate {
	private final Order order;
	private final Food food;

	public Plate(Order ord, Food f) {
		order = ord;
		food = f;
	}

	public Order getOrder() {
		return order;
	}

	public Food getFood() {
		return food;
	}

	public String toString() {
		return food.toString();
	}
}

// 顾客，由餐厅随机生成
class Customer implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final WaitPerson waitPerson;
	// 同一时刻只能上一道菜
	private SynchronousQueue<Plate> placeSetting = new SynchronousQueue<Plate>();

	public Customer(WaitPerson w) {
		waitPerson = w;
	}

	// 用于服务员上菜
	public void deliver(Plate p) throws InterruptedException {
		// Only blocks if customer is still
		// eating the previous course:
		placeSetting.put(p);
	}

	// 顾客随机点餐
	public void run() {
		for (Course course : Course.values()) {
			Food food = course.randomSelection();
			try {
				waitPerson.placeOrder(this, food);
				// Blocks until course has been delivered:
				System.out.println(this + "吃 " + placeSetting.take());
			} catch (InterruptedException e) {
				System.out.println(this + "waiting for " + course + " interrupted");
				break;
			}
		}
		System.out.println(this + "finished meal, leaving");
	}

	public String toString() {
		return "顾客 " + id + " 号 ";
	}
}

// 服务员，由餐厅创建
class WaitPerson implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final Restaurant restaurant;
	BlockingQueue<Plate> filledOrders = new LinkedBlockingQueue<Plate>();

	public WaitPerson(Restaurant rest) {
		restaurant = rest;
	}

	// 处理顾客点菜
	public void placeOrder(Customer cust, Food food) {
		try {
			// 这里不会阻塞，因为Linked没有大小限制
			restaurant.orders.put(new Order(cust, this, food));
		} catch (InterruptedException e) {
			System.out.println(this + " placeOrder interrupted");
		}
	}

	// 端菜并端给顾客
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// 阻塞直到菜做好为止
				Plate plate = filledOrders.take();
				System.out.println(this + "收到 " + plate + " 端给 " + plate.getOrder().getCustomer());
				plate.getOrder().getCustomer().deliver(plate);
			}
		} catch (InterruptedException e) {
			System.out.println(this + " interrupted");
		}
		System.out.println(this + " off duty");
	}

	public String toString() {
		return "服务员 " + id + " 号 ";
	}
}

// 厨师
class Chef implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final Restaurant restaurant;
	private static Random rand = new Random(47);

	public Chef(Restaurant rest) {
		restaurant = rest;
	}

	// 获取订单，做菜
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// 阻塞直到获取到订单
				Order order = restaurant.orders.take();
				Food requestedItem = order.item();
				TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
				Plate plate = new Plate(order, requestedItem);
				order.getWaitPerson().filledOrders.put(plate);
			}
		} catch (InterruptedException e) {
			System.out.println(this + " interrupted");
		}
		System.out.println(this + " off duty");
	}

	public String toString() {
		return "Chef " + id + " ";
	}
}

// 餐厅
class Restaurant implements Runnable {
	private List<WaitPerson> waitPersons = new ArrayList<WaitPerson>();
	private List<Chef> chefs = new ArrayList<Chef>();
	private ExecutorService exec;
	private static Random rand = new Random(47);
	BlockingQueue<Order> orders = new LinkedBlockingQueue<Order>();

	public Restaurant(ExecutorService e, int nWaitPersons, int nChefs) {
		exec = e;
		for (int i = 0; i < nWaitPersons; i++) {
			WaitPerson waitPerson = new WaitPerson(this);
			waitPersons.add(waitPerson);
			exec.execute(waitPerson); //服务员开始工作
		}
		for (int i = 0; i < nChefs; i++) {
			Chef chef = new Chef(this);
			chefs.add(chef);
			exec.execute(chef); //厨师开始工作
		}
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// A new customer arrives; assign a WaitPerson:
				WaitPerson wp = waitPersons.get(rand.nextInt(waitPersons.size()));
				Customer c = new Customer(wp);
				exec.execute(c); //召唤顾客
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("Restaurant interrupted");
		}
		System.out.println("Restaurant closing");
	}
}

/**
 * 仿真饭店
 * 1. 使用线程安全的集合：
 * LinkedBlockingQueue 存取订单和菜
 * SynchronousQueue    处理顾客点的菜，这个集合没有存储功能，模拟顾客每次只能点一次菜
 * 2. 程序线程梳理：
 * 主线程：开启其他线程和中断所有线程
 * 餐厅线程：开启服务员和厨师线程，随机生成顾客
 * 顾客线程：每组食物都点一遍
 * 服务员线程：端菜并交给顾客
 * 厨师线程：获取订单并做菜
 * 3. 这个类只使用队列在任务间通信，极大地简化编程
 */
public class RestaurantWithQueues {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		Restaurant restaurant = new Restaurant(exec, 5, 2);
		exec.execute(restaurant);
		if (args.length > 0) // Optional argument
			TimeUnit.SECONDS.sleep(Integer.parseInt(args[0]));
		else {
			System.out.println("Press 'Enter' to quit");
			System.in.read();
		}
		exec.shutdownNow();
	}
} /* Output: (Sample)
WaitPerson 0 received SPRING_ROLLS delivering to Customer 1
Customer 1 eating SPRING_ROLLS
WaitPerson 3 received SPRING_ROLLS delivering to Customer 0
Customer 0 eating SPRING_ROLLS
WaitPerson 0 received BURRITO delivering to Customer 1
Customer 1 eating BURRITO
WaitPerson 3 received SPRING_ROLLS delivering to Customer 2
Customer 2 eating SPRING_ROLLS
WaitPerson 1 received SOUP delivering to Customer 3
Customer 3 eating SOUP
WaitPerson 3 received VINDALOO delivering to Customer 0
Customer 0 eating VINDALOO
WaitPerson 0 received FRUIT delivering to Customer 1
...
*///:~

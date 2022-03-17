package javastudy.thread_.exercise.emulation;//: concurrency/CarBuilder.java
// A complex example of tasks working together.

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

class Car {
	private final int id;
	private boolean engine = false, driveTrain = false, wheels = false;

	public Car(int idn) {
		id = idn;
	}

	// Empty Car object:
	public Car() {
		id = -1;
	}

	public synchronized int getId() {//car的方法都设置了同步，这看起来多余，但是是为了更规范，防止在car需要同步的代码上出错
		return id;
	}

	public synchronized void addEngine() {
		engine = true;
	}

	public synchronized void addDriveTrain() {
		driveTrain = true;
	}

	public synchronized void addWheels() {
		wheels = true;
	}

	public synchronized String toString() {
		return "Car " + id + " [" + " engine: " + engine
				+ " driveTrain: " + driveTrain
				+ " wheels: " + wheels + " ]";
	}
}

class CarQueue extends LinkedBlockingQueue<Car> {
}

// 还没修饰的车
class ChassisBuilder implements Runnable {
	private CarQueue carQueue;
	private int counter = 0;

	public ChassisBuilder(CarQueue cq) {
		carQueue = cq;
	}

	// 制造原始车
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(500);
				// Make chassis:
				Car c = new Car(counter++);
				System.out.println("ChassisBuilder created " + c);
				// Insert into queue
				carQueue.put(c);
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupted: ChassisBuilder");
		}
		System.out.println("ChassisBuilder off");
	}
}

// 装配工
class Assembler implements Runnable {
	private CarQueue chassisQueue, finishingQueue;
	private Car car;
	private CyclicBarrier barrier = new CyclicBarrier(4); //用于车的四个配件装配完成
	private RobotPool robotPool;

	public Assembler(CarQueue cq, CarQueue fq, RobotPool rp) {
		chassisQueue = cq;
		finishingQueue = fq;
		robotPool = rp;
	}

	public Car car() {
		return car;
	}

	public CyclicBarrier barrier() {
		return barrier;
	}

	// 将原始车交给机器装配零件
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until chassis is available:
				car = chassisQueue.take();
				// 雇佣四个机器去工作
				robotPool.hire(EngineRobot.class, this);
				robotPool.hire(DriveTrainRobot.class, this);
				robotPool.hire(WheelRobot.class, this);
				barrier.await(); // 直到四个装配都完成
				finishingQueue.put(car);
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting Assembler via interrupt");
		} catch (BrokenBarrierException e) {
			// This one we want to know about
			throw new RuntimeException(e);
		}
		System.out.println("Assembler off");
	}
}

// 车的消费者，用于打印完成车信息
class Reporter implements Runnable {
	private CarQueue carQueue;

	public Reporter(CarQueue cq) {
		carQueue = cq;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println(carQueue.take());
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting Reporter via interrupt");
		}
		System.out.println("Reporter off");
	}
}

class RobotPool {
	// Quietly prevents identical entries:
	private Set<Robot> pool = new HashSet<Robot>();

	public synchronized void add(Robot r) {
		pool.add(r);
		notifyAll();
	}

	// 装配工分配任务
	public synchronized void hire(Class<? extends Robot> robotType, Assembler d) throws InterruptedException {
		for (Robot r : pool)
			if (r.getClass().equals(robotType)) {
				pool.remove(r);
				r.assignAssembler(d);
				r.engage();
				return;
			}
		wait(); // None available
		hire(robotType, d); // Try again, recursively
	}

	public synchronized void release(Robot r) {
		add(r);
	}
}

abstract class Robot implements Runnable {
	private RobotPool pool;

	public Robot(RobotPool p) {
		pool = p;
	}

	protected Assembler assembler;

	public Robot assignAssembler(Assembler assembler) {
		this.assembler = assembler;
		return this;
	}

	private boolean engage = false;

	public synchronized void engage() {
		engage = true;
		notifyAll();
	}

	// The part of run() that's different for each robot:
	abstract protected void performService();

	public void run() {
		try {
			powerDown(); // Wait until needed
			while (!Thread.interrupted()) {
				performService();
				assembler.barrier().await(); // Synchronize
				// We're done with that job...
				powerDown();
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting " + this + " via interrupt");
		} catch (BrokenBarrierException e) {
			// This one we want to know about
			throw new RuntimeException(e);
		}
		System.out.println(this + " off");
	}

	private synchronized void powerDown() throws InterruptedException {
		engage = false;
		assembler = null; // Disconnect from the Assembler
		// Put ourselves back in the available pool:
		pool.release(this);
		while (!engage)  // Power down
			wait();
	}

	public String toString() {
		return getClass().getName();
	}
}

class EngineRobot extends Robot {
	public EngineRobot(RobotPool pool) {
		super(pool);
	}

	protected void performService() {
		System.out.println(this + " installing engine");
		assembler.car().addEngine();
	}
}

class DriveTrainRobot extends Robot {
	public DriveTrainRobot(RobotPool pool) {
		super(pool);
	}

	protected void performService() {
		System.out.println(this + " installing DriveTrain");
		assembler.car().addDriveTrain();
	}
}

class WheelRobot extends Robot {
	public WheelRobot(RobotPool pool) {
		super(pool);
	}

	protected void performService() {
		System.out.println(this + " installing Wheels");
		assembler.car().addWheels();
	}
}

/**
 * 仿真汽车组装
 */
public class CarBuilder {
	public static void main(String[] args) throws Exception {
		CarQueue chassisQueue = new CarQueue(), //未组装的
				finishingQueue = new CarQueue(); //组装好的
		ExecutorService exec = Executors.newCachedThreadPool();
		RobotPool robotPool = new RobotPool(); //
		exec.execute(new EngineRobot(robotPool));
		exec.execute(new DriveTrainRobot(robotPool));
		exec.execute(new WheelRobot(robotPool));
		exec.execute(new Assembler(chassisQueue, finishingQueue, robotPool));
		exec.execute(new Reporter(finishingQueue));
		// Start everything running by producing chassis:
		exec.execute(new ChassisBuilder(chassisQueue));
		TimeUnit.SECONDS.sleep(7);
		exec.shutdownNow();
	}
} /* (Execute to see output) *///:~

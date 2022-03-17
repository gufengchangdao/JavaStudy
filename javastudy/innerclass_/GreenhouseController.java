package javastudy.innerclass_;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示内部类使用的一个案例，控制温室的运作。
 * 控制灯、水和温度的类作为内部类相当于多重继承，既继承了Event类，有事件的属性和方法，又继承了GreenhouseControls类，共享处理事件的集合和方法
 */
public class GreenhouseController {
	public static void main(String[] args) {
		GreenhouseControls gc = new GreenhouseControls();
		// TODO 可以从文本中读取事件的信息
		gc.addEvent(gc.new Bell(1000));
		Event[] eventList = {
				gc.new ThermostatNight(0),
				gc.new LightOn(200),
				gc.new LightOff(400),
				gc.new WaterOn(600),
				gc.new WaterOff(800),
				gc.new ThermostatDay(1400)
		};
		gc.addEvent(gc.new Restart(2000, eventList));
		if (args.length == 1)
			gc.addEvent(new GreenhouseControls.Terminate(Integer.parseInt(args[0])));
		gc.run();
	}
}
/* Output:
Bing!
Thermostat on night setting
Light is on
Light is off
Greenhouse water is on
Greenhouse water is off
Thermostat on day setting
Restarting system
Terminating
*///:~

/**
 * 事件
 */
abstract class Event {
	private long eventTime;
	protected final long delayTime;

	public Event(long delayTime) {
		this.delayTime = delayTime;
	}

	// start方法没有写在构造器中，这样就可以重复启动计时器
	public void start() {
		eventTime = System.currentTimeMillis() + delayTime;
	}

	public boolean ready() {
		return System.currentTimeMillis() >= eventTime;
	}

	// action表示要执行的操作
	public abstract void action();
}

/**
 * 管理并触发事件的控制框架
 */
class Controller {
	private List<Event> eventList = new ArrayList<>();

	public void addEvent(Event c) {
		eventList.add(c);
	}

	public void run() {
		while (eventList.size() > 0) {
			// TODO: 2022/2/7 0007 注意这里每次循环都new一下，然后填充进去，这是因为循环中修改集合，迭代器会抛出并发修改的异常
			for (Event event : new ArrayList<>(eventList)) {
				if (event.ready()) {
					System.out.println(event);
					event.action();
					eventList.remove(event);
				}
			}
		}
	}
}

/**
 * 控制温室的运作，使用Controller控制框架和继承Event的内部类
 */
class GreenhouseControls extends Controller {
	private boolean light = false;
	private boolean water = false;
	private String thermostat = "Day";

	// 开灯事件
	public class LightOn extends Event {
		public LightOn(long delayTime) {
			super(delayTime);
		}

		public void action() {
			light = true;
		}

		public String toString() {
			return "Light is on";
		}
	}

	// 关灯事件
	public class LightOff extends Event {
		public LightOff(long delayTime) {
			super(delayTime);
		}

		public void action() {
			light = false;
		}

		public String toString() {
			return "Light is off";
		}
	}

	// 开水事件
	public class WaterOn extends Event {
		public WaterOn(long delayTime) {
			super(delayTime);
		}

		public void action() {
			water = true;
		}

		public String toString() {
			return "Greenhouse water is on";
		}
	}

	// 关水事件
	public class WaterOff extends Event {
		public WaterOff(long delayTime) {
			super(delayTime);
		}

		public void action() {
			water = false;
		}

		public String toString() {
			return "Greenhouse water is off";
		}
	}

	// 夜晚设置温度事件
	public class ThermostatNight extends Event {
		public ThermostatNight(long delayTime) {
			super(delayTime);
		}

		public void action() {
			thermostat = "Night";
		}

		public String toString() {
			return "Thermostat on night setting";
		}
	}

	// 白天设置温度事件
	public class ThermostatDay extends Event {
		public ThermostatDay(long delayTime) {
			super(delayTime);
		}

		public void action() {
			// Put hardware control code here.
			thermostat = "Day";
		}

		public String toString() {
			return "Thermostat on day setting";
		}
	}

	// 响铃事件，每隔一段时间执行一次
	public class Bell extends Event {
		public Bell(long delayTime) {
			super(delayTime);
			start();
		}

		public void action() {
			addEvent(new Bell(delayTime)); //创建一个新的同间隔的对象
			start();
		}

		public String toString() {
			return "Bing!";
		}
	}

	// 循环运行事件
	public class Restart extends Event {
		private Event[] eventList;

		public Restart(long delayTime, Event[] eventList) {
			super(delayTime);
			this.eventList = eventList;
			for (Event e : eventList)
				addEvent(e);
		}

		public void action() {
			for (Event e : eventList) {
				e.start(); // Rerun each event
				addEvent(e);
			}
			start(); // Rerun this Event
			addEvent(this); // TODO: 2022/2/7 0007 反复运行数组中的所有事件
		}

		public String toString() {
			return "Restarting system";
		}
	}

	// 终止程序事件
	public static class Terminate extends Event {
		public Terminate(long delayTime) {
			super(delayTime);
		}

		public void action() {
			System.exit(0);
		}

		public String toString() {
			return "Terminating";
		}
	}
}
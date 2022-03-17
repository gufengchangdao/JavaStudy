package javastudy.thread_.exercise.emulation;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class Toast {
	public enum Status {
		DRY("干吐司"),
		BUTTERED("黄油吐司"),
		JAMMED("黄油果酱吐司"),
		GODMOTER("老干妈黄油吐司");
		private String description;

		Status(String description) {
			this.description = description;
		}

		public String toString() {
			return description;
		}
	}

	private Status status = Status.DRY;
	private final int id;

	public Toast(int id) {
		this.id = id;
	}

	public void butter() {
		status = Status.BUTTERED;
	}

	public void jam() {
		status = Status.JAMMED;
	}

	public void godmother() {
		status = Status.GODMOTER;
	}

	public Status getStatus() {
		return status;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return "Toast " + id + ": " + status;
	}
}

class ToastQueue extends LinkedBlockingQueue<Toast> {
}

// 做吐司
class Toaster implements Runnable {
	private ToastQueue toastQueue;
	private int count = 0;
	private Random rand = new Random(47);

	public Toaster(ToastQueue toastQueue) {
		this.toastQueue = toastQueue;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
				Toast t = new Toast(count++);
				System.out.println(t);
				toastQueue.put(t);
			}
		} catch (InterruptedException e) {
			System.out.println("Toaster 被中断");
		}
		System.out.println("Toaster 做吐司结束");
	}
}

// 抹黄油
class Butterer implements Runnable {
	private ToastQueue dryQueue, butteredQueue;

	public Butterer(ToastQueue dryQueue, ToastQueue butteredQueue) {
		this.dryQueue = dryQueue;
		this.butteredQueue = butteredQueue;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast t = dryQueue.take();
				t.butter(); //抹黄油
				System.out.println(t);
				butteredQueue.put(t);
			}
		} catch (InterruptedException e) {
			System.out.println("Butterer 被中断");
		}
		System.out.println("Butterer 抹黄油结束");
	}
}

// 上果酱
class Jammer implements Runnable {
	private ToastQueue butteredQueue, finishedQueue;

	public Jammer(ToastQueue buttered, ToastQueue finished) {
		butteredQueue = buttered;
		finishedQueue = finished;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast t = butteredQueue.take();
				t.jam(); // 上果酱
				System.out.println(t);
				finishedQueue.put(t);
			}
		} catch (InterruptedException e) {
			System.out.println("Jammer 被中断");
		}
		System.out.println("Jammer 上果酱结束");
	}
}

// 加老干妈
class Godmother implements Runnable {
	private ToastQueue butteredQueue, finishedQueue;

	public Godmother(ToastQueue butteredQueue, ToastQueue finishedQueue) {
		this.butteredQueue = butteredQueue;
		this.finishedQueue = finishedQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast take = butteredQueue.take();
				take.godmother();
				System.out.println(take);
				finishedQueue.put(take);
			}
		} catch (InterruptedException e) {
			System.out.println("Godmother 被中断");
		}
		System.out.println("Godmother 加老干妈结束");
	}
}

// 消费吐司
class Eater implements Runnable {
	private ToastQueue finishedQueue;
	private int counter = 0;

	public Eater(ToastQueue finished) {
		finishedQueue = finished;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast t = finishedQueue.take();
				// 验证吐司是否按顺序排列，并且所有部分都被卡住了
				if (t.getId() != counter++ || (t.getStatus() != Toast.Status.JAMMED && t.getStatus() != Toast.Status.GODMOTER)) {
					System.out.println(">>>> Error: " + t);
					System.exit(1);
				} else
					System.out.println("吃它! " + t);
			}
		} catch (InterruptedException e) {
			System.out.println("Eater 被中断");
		}
		System.out.println("Eater 吃完了");
	}
}

/**
 * 使用BlockingQueue模拟制作吐司的过程，
 * 做吐司、抹黄油、加老干妈、上果酱、吃吐司同时开工
 */
public class ToastOMatic {
	public static void main(String[] args) throws Exception {
		ToastQueue dryQueue = new ToastQueue(),
				butteredQueue = new ToastQueue(),
				finishedQueue = new ToastQueue();
		ExecutorService exec = Executors.newCachedThreadPool();

		exec.execute(new Toaster(dryQueue));
		exec.execute(new Butterer(dryQueue, butteredQueue));
		exec.execute(new Godmother(butteredQueue, finishedQueue));
		exec.execute(new Jammer(butteredQueue, finishedQueue));
		exec.execute(new Eater(finishedQueue));
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}

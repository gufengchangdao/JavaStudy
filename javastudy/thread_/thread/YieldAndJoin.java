/*
yield
    线程的礼让，当资源紧缺的时候，让出CPU，让其他线程执行。礼让的时间不确定，也不定能成功(像资源充足的时候)
join
    线程的插队。一旦插入成功，会在插入的地方先执行完(不管插入的线程是否正在执行)插入的线程所有的任务

 */
package javastudy.thread_.thread;

public class YieldAndJoin {
    public static void main(String[] args) {
        QueueUp child = new QueueUp();
        child.start();
        for (int i = 0; i < 10; i++) {
            System.out.println("主线程循环了 " + i + "秒");
            try {
                Thread.sleep(1000);

                if (i == 4) {
                    System.out.println("主线程让子线程插队了！");
                    child.join();       //子线程插队，该线程会卡在这里直到插队的线程结束
//                    QueueUp.yield();  //子线程礼让，一般不成功
                    System.out.println("子线程结束了！");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class QueueUp extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("子线程循环了 " + i + "秒");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
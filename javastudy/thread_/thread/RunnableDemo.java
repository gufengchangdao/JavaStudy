/*
Runnable
    创建一个线程是声明实现类Runnable接口。 那个类然后实现了run方法。 然后可以分配类的实例，在创建Thread时作为参数传递，并启动。
    底层使用了代理模式
    实现Runnable接口方式更加适合多个线程共享一个资源的情况，并且避免了单继承了限制

    public class Thread implements Runnable {
        private Runnable target;
        ...
        public Thread(Runnable target){}
        ...
    }
    Thread里面有储存Runnable对象的target，可以调用构造器传入实现其接口的类，通过Thread对象调用重写后的run方法
 */
package javastudy.thread_.thread;

public class RunnableDemo {
    public static void main(String[] args) {
        Dog dog = new Dog();
        //同一个对象的run方法，不同的线程调用
        Thread thread1 = new Thread(dog);
        Thread thread2 = new Thread(dog);

        thread1.start();
        thread2.start();

    }
}

class Dog implements Runnable {
    private int counts = 0; //因为是同一个对象，因此不同的线程都执行了 ++counts

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + "运行第 " + (++counts) + " 次");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (counts==6)
                break;
        }
    }
}

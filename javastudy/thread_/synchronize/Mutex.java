/*
TODO 互斥锁Mutex
    1. 每个对象都对应于一个可称为“互斥锁”的标记，这个标记用来保证在任意时刻，只能有一个线程访问该对象
    2. 关键词 synchronized 来与对象的互斥锁联系，当某个对象用synchronized修饰时，表明该对象在任意时刻只能由一个线程访问
    3. 同步的局限性：导致程序的执行效率较低。因此：尽量使用synchronized代码块，并且代码块的内容尽可能少，执行代码块的时间尽可能少
    4. 同步方法(非静态的)的锁可以是this，也可以是其他对象(要求是同一个对象)
    5. 同步方法(静态的)的锁为当前类本身
    6. 这个锁会管理试图进入synchronized方法的线程。这个锁内部有一个条件，这个条件可以管理调用了wait的线程
    7. 注意：多个线程争一把锁时，当一个线程抢到锁运行完代码块后还可以继续争夺(公平竞争)，因此在循环中存在同步代码块时，可能有一个进程连续执行了该代码块多次

TODO 线程的死锁
    1. 多个线程都占用了对方的锁资源，但不肯想让，导致了死锁，一定要避免这个
    2. 发生死锁时，在编译器左下角找一个叫转储线程的功能，会列出所有线程，告诉你每一条线程当前在哪里阻塞
 */
package javastudy.thread_.synchronize;

/**
 * 演示互斥锁(synchronized)的使用
 */
public class Mutex {
    public static void main(String[] args) {


    }
}

//在多线程中，各个线程中Thread子类对象是不同的，因此this不是指的同一个对象，达不到同步的效果
class Clock01 extends Thread{
    private final Object object=new Object();   //设置为 final
    private final static Object object1=new Object();   //设置为 final static

    @Override
    public void run() {
        clo03();
        clo04();
        clo05();
        clo06();
    }

    public synchronized void clo01(){}    //锁在this对象上
    public void clo02(){
        synchronized (this){}             //锁在this对象上
    }
      public void clo03(){
        synchronized (object){}             //锁在Thread子类的静态属性上，这样也可以使得多个子类对象共享一把锁，不过也可以在调用的时候再创建
    }

    public synchronized static void clo04(){}   //锁在Clock01.class上
    public static void clo05(){
        synchronized (Clock01.class){}          //锁在Clock01.class上
    }
    public static void clo06(){
        synchronized (object1){}                //锁在Clock01.class上，静态代码块需要的是静态对象
    }
}

//在多线程中，多个线程中使用的 实现Runnable的类对象 可以是同一个对象，因此可以用this作为锁
class Clock02 implements Runnable{
    @Override
    public void run() {
        clo01();
        clo02();

    }
    public synchronized void clo01(){}    //锁在this对象上，使用的是同一个对象的话，就可以同步
    public void clo02(){
        synchronized (this){}             //锁在this对象上
    }
}

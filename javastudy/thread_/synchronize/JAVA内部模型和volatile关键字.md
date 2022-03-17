Java 内存模型的三个特征
======================

### 可见性:

可见性，是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的。也就是一个线程修改的结果。另一个线程马上就能看到。比如：用volatile修饰的变
量，就会具有可见性。volatile修饰的变量不允许线程内部缓存和重排序，即直接修改内存。所以对其他线程是可见的

在 Java 中 volatile、synchronized 和 final 实现可见性。

### 原子性:

原子是世界上的最小单位，具有不可分割性。比如 a=0；（a非long和double类型） 这个操作是不可分割的。再比较：a++; 这个操作实际上是a=a+1; 先计算再赋 值，所以它不是一个原子操作。

非原子操作都会存在线程安全问题，需要我们使用同步技术（synchronized）来让它变成一个原子操作。一个操作是原子操作，那么我们称它具有原子性。

java的concurrent包下提供了一些原子类，比如：AtomicInteger、AtomicLong、AtomicReference等

在 Java 中 synchronized 和在 lock、unlock 中操作保证原子性。

### 有序性:

Java 语言提供了 volatile 和 synchronized 两个关键字来保证线程之间操作的有序性，volatile 是因为其本身包含“禁止指令重排序”的语义， synchronized
是由“一个变量在同一个时刻只允许一条线程对其进行 lock 操作”这条规则获得的，此规则决定了持有同一个对象锁的两个同步块只能串行执行。

****
****
Volatile原理
=============

1. 保证此变量对所有的线程的可见性。当一个线程修改了这个变量的值，volatile 保证了新值能立即同步到主内存，以及每次使用前立即从主内存刷新。但普通变 量做不到这点，普通变量的值在线程间传递均需要通过主内存来完成。
2. 禁止指令重排序优化。（什么是指令重排序：是指CPU采用了允许将多条指令不按程序规定的顺序分开发送给各相应电路单元处理）。
3. 在访问volatile变量时不会执行加锁操作，因此也就不会使执行线程阻塞，因此volatile变量是一种比synchronized关键字更轻量级的同步机制。
4. volatile 的读性能消耗与普通变量几乎相同，但是写操作稍慢，因为它需要在本地代码中插入许多内存屏障指令来保证处理器不发生乱序执行。
5. 多线程中存在写入操作的字段就应该加volatile修饰


```java
class VolatileTest {
    private volatile boolean done;

    // 当done的值改变了，其他线程调用该方法可以立马看到
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done; //直接赋值是原子性操作
    }

    public void flipDone() {
        done = !done; //该操作不是原子性操作，不能保证读取、翻转和写入不被中断
    }
}
```

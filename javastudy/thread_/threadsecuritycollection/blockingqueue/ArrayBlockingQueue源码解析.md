```java
/**
 * ArrayBlockingQueue的主要方法解析
 */
public class ArrayBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, java.io.Serializable {

    /**存储队列元素的数组，是个循环数组*/
    final Object[] items;

    /**拿数据的索引，用于take，poll，peek，remove方法*/
    int takeIndex;

    /**放数据的索引，用于put，offer，add方法*/
    int putIndex;

    /**元素个数*/
    int count;

    /*
    并发控制使用双条件算法
     */

    /**可重入锁*/
    final ReentrantLock lock;

    /**notEmpty条件对象，由lock创建，等待拿取*/
    private final Condition notEmpty;

    /**notFull条件对象，由lock创建，等待存放*/
    private final Condition notFull;

    /**当前活动的共享迭代器，如果不存在则为 null。允许队列操作更新迭代器状态。*/
    transient Itrs itrs;

    // 内部辅助函数

    /**
     * 自增i并且取模modulus
     * 前置条件和后置条件: 0 <= i < modulus.
     */
    static final int inc(int i, int modulus) {
        if (++i >= modulus) i = 0;
        return i;
    }

    /**
     * 自减i并且取模modulus
     * 前置条件和后置条件: 0 <= i < modulus.
     */
    static final int dec(int i, int modulus) {
        if (--i < 0) i = modulus - 1;
        return i;
    }

    /**
     * 返回索引为i的元素
     */
    @SuppressWarnings("unchecked")
    final E itemAt(int i) {
        return (E) items[i];
    }

    /**
     * 返回数组索引为i的元素。这是对泛型的轻微滥用，被javac接受
     */
    @SuppressWarnings("unchecked")
    static <E> E itemAt(Object[] items, int i) {
        return (E) items[i];
    }

    /**
     * 在当前放置位置(putIndex下标处)插入元素。仅在持有锁时调用。
     */
    private void enqueue(E e) {
        final Object[] items = this.items;
        items[putIndex] = e;
        if (++putIndex == items.length) putIndex = 0;
        count++;
        notEmpty.signal();
    }

    /**
     * 将队首元素出队，不会检测队列是否为空
     * 仅在持有锁时调用
     */
    private E dequeue() {
        final Object[] items = this.items;
        @SuppressWarnings("unchecked")
        E e = (E) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length) takeIndex = 0;
        count--;
        if (itrs != null)
            itrs.elementDequeued();
        notFull.signal();   // 使用条件对象notFull通知，比如使用put方法放数据的时候队列已满，被阻塞。
        // 这个时候消费了一条数据，队列没满了，就需要调用signal进行通知
        return e;
    }

    /**
     * 删除指定下标元素
     * 通过 remove(Object) 和 iterator.remove来调用
     * 仅在持有锁时调用
     */
    void removeAt(final int removeIndex) {
        final Object[] items = this.items;
        // 要删除元素是队首元素
        if (removeIndex == takeIndex) {
            items[takeIndex] = null;
            if (++takeIndex == items.length) takeIndex = 0;
            count--;
            if (itrs != null) //拥有迭代器
                itrs.elementDequeued();
        } else {

            for (int i = removeIndex, putIndex = this.putIndex; ; ) {
                int pred = i;
                if (++i == items.length) i = 0;
                if (i == putIndex) { //遍历到队尾，移动putIndex指针
                    items[pred] = null;//putIndex所在下标元素为null，用items[pred] = items[putIndex];也行
                    this.putIndex = pred;
                    break;
                }
                items[pred] = items[i];
            }
            count--;
            if (itrs != null) //拥有迭代器
                itrs.removedAt(removeIndex);
        }
        notFull.signal(); // 使用条件对象notFull通知，比如使用put方法放数据的时候队列已满，被阻塞。这个时候消费了一条数据，队列没满了，就
        // 需要调用signal进行通知
    }

    /**
     * 用给定的容量大小和默认的访问方式创建ArrayBlockingQueue对象
     *
     * @param capacity 队列容量
     * @throws IllegalArgumentException 如果 capacity < 1
     */
    public ArrayBlockingQueue(int capacity) {
        this(capacity, false);
    }

    /**
     * 用给定的容量大小和给定的访问方式创建ArrayBlockingQueue对象
     *
     * @param capacity 队列容量
     * @param fair     创建重入锁(false)还是公平锁(true)
     * @throws IllegalArgumentException 如果 capacity < 1
     */
    public ArrayBlockingQueue(int capacity, boolean fair) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.items = new Object[capacity];
        lock = new ReentrantLock(fair); //创建重入锁还是公平锁
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    /**
     * 用给定的容量大小和给定的访问方式创建ArrayBlockingQueue对象，并把集合中的元素作为队列的初始值
     * 按集合迭代器的遍历顺序添加
     *
     * @param capacity 队列容量
     * @param fair     if {@code true} then queue accesses for threads blocked
     *                 on insertion or removal, are processed in FIFO order;
     *                 if {@code false} the access order is unspecified.
     * @param c        最初包含的元素集合
     * @throws IllegalArgumentException 如果 capacity 小于集合中元素数量(c.size()) 或者小于 1
     * @throws NullPointerException     如果集合为null或其中任意一个元素为null
     */
    public ArrayBlockingQueue(int capacity, boolean fair, Collection<? extends E> c) {
        this(capacity, fair);

        final ReentrantLock lock = this.lock;
        lock.lock(); // 锁定仅用于可见性，而不是互斥
        try {
            final Object[] items = this.items;
            int i = 0;
            try {
                for (E e : c) //使用迭代器的方式遍历添加元素
                    items[i++] = Objects.requireNonNull(e);
            } catch (ArrayIndexOutOfBoundsException ex) { //数组长度小于集合元素个数
                throw new IllegalArgumentException();
            }
            count = i;
            putIndex = (i == capacity) ? 0 : i;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 将元素放置到队尾，不允许插入null值，
     * 该方法会调用AbstractQueue的add()方法
     *
     * @param e 要插入的元素
     * @return 成功返回true
     * @throws IllegalStateException 如果队列满了
     * @throws NullPointerException  如果元素e为null
     */
    public boolean add(E e) {
        return super.add(e);
    }

    /**
     * 插入一个元素到队尾，插入成功就返回true，失败就返回false，在多线程中通常这个方法要比add()更好，因为前者插入失败就会抛异常，多线程可能会
     * 频繁出现队列满的情况
     *
     * @throws NullPointerException 如果e为null的话
     */
    public boolean offer(E e) {
        Objects.requireNonNull(e);// 不允许元素为空
        final ReentrantLock lock = this.lock;
        lock.lock();// 加锁，保证调用offer方法的时候只有1个线程
        try {
            if (count == items.length)// 如果队列已满
                return false;// 直接返回false，添加失败
            else {
                enqueue(e); // 数组没满的话调用enqueue方法
                return true; // 返回true，添加成功
            }
        } finally {
            lock.unlock();// 释放锁
        }
    }

    /**
     * 插入指定元素到队尾，如果队列为满会一直阻塞在这里
     *
     * @throws InterruptedException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public void put(E e) throws InterruptedException {
        Objects.requireNonNull(e); // 不允许元素为空
        final ReentrantLock lock = this.lock; // 加锁
        lock.lockInterruptibly();
        try {
            while (count == items.length)// 如果队列满了，阻塞当前线程，并加入到条件对象notFull的等待队列里
                notFull.await(); //线程阻塞在这里，等待程序再次调用出队操作
            enqueue(e); //入队
        } finally {
            lock.unlock();
        }
    }

    /**
     * 插入指定元素到队尾, 如果队列已满，则等待指定的等待时间以使空间可用
     *
     * @throws InterruptedException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        Objects.requireNonNull(e);
        long nanos = unit.toNanos(timeout); //得到等待时长(以毫秒为单位)
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length) {
                if (nanos <= 0L) //nanos小于0说明long型数太大，溢出了
                    return false;
                nanos = notFull.awaitNanos(nanos); //等待指定的时间，直到 等待的时间到 或 被signal唤醒 再或者 被中断
            }
            enqueue(e);
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 移除并返回队首元素，如果队列为空返回null
     *
     * @return 队首元素或null
     */
    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return (count == 0) ? null : dequeue();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 移除并返回队首元素，如果队列为空返回null
     *
     * @return 队首元素
     * @throws InterruptedException
     */
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly(); // 加锁，保证调用take方法的时候只有1个线程
        try {
            while (count == 0) // 如果队列空，阻塞当前线程，并加入到条件对象notEmpty的等待队列里
                notEmpty.await();
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 移除并返回队首元素，如果队列为空，则等待指定的等待时间以使队列有元素
     *
     * @param timeout 等待时长
     * @param unit    时间单位
     * @return 队首元素
     * @throws InterruptedException 中断异常
     */
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                if (nanos <= 0L)
                    return null;
                nanos = notEmpty.awaitNanos(nanos);
            }
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 返回队首元素，不会删除队首元素，如果队列为空就返回null
     *
     * @return 队首元素或null
     */
    public E peek() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return itemAt(takeIndex);
        } finally {
            lock.unlock();
        }
    }

    // this doc comment is overridden to remove the reference to collections
    // greater in size than Integer.MAX_VALUE

    /**
     * 返回阻塞队列元素个数
     *
     * @return 队列元素个数
     */
    public int size() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    // this doc comment is a modified copy of the inherited doc comment,
    // without the reference to unlimited queues.

    /**
     * 返回队列的剩余容量
     * <p>
     * 请注意，您不能总是通过检查remainingCapacity容量来判断插入元素的尝试是否会成功，因为可能存在另一个线程即将插入或删除元素的情况。
     */
    public int remainingCapacity() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return items.length - count;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 删除指定元素
     * 但是在循环数组中删除中间元素既低效率又危险，不推荐使用
     *
     * @param o
     * @return 如果该方法
     */
    public boolean remove(Object o) {
        if (o == null) return false;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (count > 0) {
                final Object[] items = this.items;
                // 很优雅的遍历方式
                for (int i = takeIndex, end = putIndex, to = (i < end) ? end : items.length; ; i = 0, to = end) {
                    for (; i < to; i++)
                        if (o.equals(items[i])) {
                            removeAt(i);
                            return true;
                        }
                    if (to == end) break;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 检测队列中是否含有(至少含有一个)指定元素
     *
     * @param o 在队列中要检测的对象
     * @return {@code true} 如果队列中含有指定的元素
     */
    public boolean contains(Object o) {
        if (o == null) return false;
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (count > 0) {
                final Object[] items = this.items;
                for (int i = takeIndex, end = putIndex, to = (i < end) ? end : items.length; ; i = 0, to = end) {
                    for (; i < to; i++)
                        if (o.equals(items[i]))
                            return true;
                    if (to == end) break;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 按正确的顺序返回包含此队列中所有元素的数组。
     * 数组和集合中的元素的引用是同一对象
     * 此方法充当基于数组和基于集合的 API 之间的桥梁。
     * @return 包含此队列中所有元素的数组
     */
    public Object[] toArray() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object[] items = this.items;
            final int end = takeIndex + count;
            final Object[] a = Arrays.copyOfRange(items, takeIndex, end);
            if (end != putIndex)
                System.arraycopy(items, 0, a, items.length - takeIndex, putIndex);
            return a;
        } finally {
            lock.unlock();
        }
    }
    
    // ----------------------------- - 剩下的还有很多方法，就不写了 ------------------------------  
}

```
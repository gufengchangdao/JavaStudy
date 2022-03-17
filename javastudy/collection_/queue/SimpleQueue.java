package javastudy.collection_.queue;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 循环数组实现的简单队列
 * 该类继承了AbstractQueue，没有扩容操作，超出容量就会抛异常
 *
 * @param <E> 元素类型
 */
public class SimpleQueue<E> extends AbstractQueue<E> {
    private final Object[] elementData;
    private int front;
    private int rear;
    private int size; //虽然有了front和rear就不可以不用加size了，但是我喜欢

    /**
     * 队列总容量，该队列没有扩容操作，容量超出会抛异常
     *
     * @param maxLength 最大长度
     */
    public SimpleQueue(int maxLength) {
        elementData = new Object[maxLength];
        front = rear = 0;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIte();
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * 添加元素进入队尾，没有扩容操作，队列满就会返回false
     *
     * @param e 要添加进队尾的元素
     * @return 成功就true，失败就false
     */
    @Override
    public boolean offer(E e) {
        //队列满了，虽然我这里没有抛异常，但是如果调用的是add()，add()会调用该方法，如果返回false就会抛出队列已满异常
        if (size == elementData.length)
            return false;
        // 注意这里要是先加1再赋值的，因此Front对应的数据一个无效元素，Front+1才是第一个元素
        rear = (rear + 1) % elementData.length;
        elementData[rear] = e;
        size++;
        return true;
    }


    // 获取队首元素并删除
    @SuppressWarnings("unchecked")
    public E poll() {
        // 同样，当队列为空时，如果调用remove()会抛出异常，而调用poll()只是返回null
        if (size == 0) //队列为空时返回null
            return null;
        size--;
        front = (front + 1) % elementData.length;
        return (E) elementData[front];
    }

    // 获取队首元素但是不删除
    @SuppressWarnings("unchecked")
    public E peek() {
        if (size == 0) //队列为空时返回null
            return null;
        return (E) elementData[(front + 1) % elementData.length];
    }

    // 自定义迭代器
    private class MyIte implements Iterator<E> {
        int index = 0;

        @Override
        public boolean hasNext() {
            return index != size;
        }

        /**
         * 获取下一个元素，遍历到队尾还遍历就会抛出异常
         *
         * @return 下一个元素
         * @throws NoSuchElementException 已经遍历到头了
         */
        @SuppressWarnings("unchecked")
        public E next() {
            if (!hasNext()) //遍历到头了
                throw new NoSuchElementException();

            return (E) elementData[(front + (++index)) % elementData.length];
        }
    }
}

class MyQueueTest {
    public static void main(String[] args) {
        SimpleQueue<Integer> queue = new SimpleQueue<Integer>(10);
        // TODO 方法没有启效果时，第一个会抛异常，第二个只是返回null
        //  第一个是AbstractQueue已经实现好的方法(调用自己实现的方法)，第二个是需要自己实现的方法
        queue.add(10);
        queue.offer(10);

        queue.element();
        queue.peek();

        queue.remove();
        queue.poll();

    }
}
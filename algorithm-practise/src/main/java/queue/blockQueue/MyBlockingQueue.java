package queue.blockQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-06-24
 */
public class MyBlockingQueue<E> {
    private int count;
    private int capacity;
    private final List<E> containor;

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public MyBlockingQueue(int capacity) {
        containor = new ArrayList<E>(capacity);
        this.count = 0;
        this.capacity = capacity;
    }

    public void put(E data) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == capacity) {
                notFull.await();
            }
            containor.add(data);
            this.count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            this.count--;
            notFull.signal();
            E node = containor.get(0);
            containor.remove(0);
            return node;
        } finally {
            lock.unlock();
        }
    }
}

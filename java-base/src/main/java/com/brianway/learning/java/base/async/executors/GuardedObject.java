package com.brianway.learning.java.base.async.executors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @author liuyu05 <liuyu05@kuaishou.com> 2022-06-14
 */
public class GuardedObject<T> {

    //保存所有GuardedObject
    private static final Map<Object, GuardedObject> GOS = new ConcurrentHashMap<>();

    //受保护的对象
    private T obj;
    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();
    private final int timeout = 2;

    //静态方法创建GuardedObject
    static <K> GuardedObject create(K key) {
        GuardedObject go = new GuardedObject();
        GOS.put(key, go);
        return go;
    }

    static <K, T> void fireEvent(K key, T obj) {
        GuardedObject go = GOS.remove(key);
        if (go != null) {
            go.onChanged(obj);
        }
    }

    //获取受保护对象
    T get(Predicate<T> p) {
        lock.lock();
        try {
            //MESA管程推荐写法
            while (!p.test(obj)) {
                done.await(timeout, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        //返回非空的受保护对象
        return obj;
    }

    //事件通知方法
    void onChanged(T result) {
        lock.lock();
        try {
            this.obj = result;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }

}

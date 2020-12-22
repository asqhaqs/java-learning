package com.asqhaqs.atomicity;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  使用 AtomicBoolean 实现一个 显示锁
 */
public class TryLock {
    private final AtomicBoolean ab = new AtomicBoolean(false);
    // 线程保险箱，用于存放与线程上下文相关联的数据副本，确保只有获取锁的线程才能释放锁
    private final ThreadLocal<Boolean> threadLocal = ThreadLocal.withInitial(()->false);


    public boolean tryLock() {
        //只有在未加锁时候（false）才能正确设置
        boolean result = ab.compareAndSet(false, true);
        if (result) {
            threadLocal.set(true);
        }
        return result;
    }

    public boolean release() {
        // 只有成功获取锁的线程才能释放锁
        if (threadLocal.get()) {
            threadLocal.set(false);
            return ab.compareAndSet(true, false);
        } else {
            return false;
        }
    }
}

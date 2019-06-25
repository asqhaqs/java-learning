package queue.blockQueue;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-06-24
 */

public class GlobalKey {

    private static AtomicInteger key = new AtomicInteger(1);

    public static int get() {
        return key.getAndIncrement();
    }
}

package queue.blockQueue;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomUtils;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-06-24
 */

public class Consumer implements Runnable {
    private MyBlockingQueue<NodeItem> queue;
    public Consumer(MyBlockingQueue<NodeItem> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(RandomUtils.nextInt(0, 5));
                NodeItem node = queue.take();
                System.out.println("consume a node" + node);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

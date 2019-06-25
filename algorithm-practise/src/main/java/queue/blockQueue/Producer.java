package queue.blockQueue;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomUtils;

public class Producer implements Runnable {
    private MyBlockingQueue<NodeItem> queue;

    public Producer(MyBlockingQueue<NodeItem> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(RandomUtils.nextInt(0, 5));
                NodeItem node = new NodeItem();
                node.setKey(GlobalKey.get());
                System.out.println("produce a node" + node);
                queue.put(node);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
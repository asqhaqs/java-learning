package queue.blockQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-06-24
 */


public class ProviderService {
    public static void main(String[] args) {

        final MyBlockingQueue blockingQueue = new MyBlockingQueue(3);

        ExecutorService exec = Executors.newCachedThreadPool();

        exec.submit(new Producer(blockingQueue));
        exec.submit(new Consumer(blockingQueue));
    }
}

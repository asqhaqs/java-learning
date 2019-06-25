package queue.blockQueue;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-06-24
 */
public class NodeItem {

    private int key;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "NodeItem{" +
                "key=" + key +
                '}';
    }
}

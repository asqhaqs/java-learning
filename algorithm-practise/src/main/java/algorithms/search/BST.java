package algorithms.search;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-06-13
 */
public class BST<Key extends Comparable<Key>, Value> {
    //二叉查找树的根节点
    private Node root;

    private class Node{
        private Key key;
        private Value value;
        private Node left, right; //指向子树的链接
        private int N;  // 以该节点为根节点的子树中的节点总数

        public Node(Key key, Value value, int N){
            this.key = key;
            this.value = value;
            this.N = N;
        }

    }


    public int size(){
        return size(root);
    }

    public int size(Node node){
        if( node == null )
            return 0;
        else
            return node.N;
    }


    public Value get (Key key){
        return get(root, key);
    }

    public Value get(Node x, Key key){
        // 在以x为根节点的子树中查找并返回Key所对应的值
        // 如果找不到则返回null
        if(x == null)
            return  null;
        int cmp = key.compareTo(x.key);
        if( cmp < 0 ){
            return get(x.left, key);
        }else if(cmp > 0){
            return get(x.right, key);
        }else {
            return x.value;
        }
    }

    public void put (Key key, Value value){
        put(root, key, value);
    }

    public Node put(Node x, Key key, Value value){
        // 如果key 存在于 以 x 为根节点的子树中则更新它的值
        // 否则将以key 和 value 为键值对的新节点插入到该子树中
        if(x == null) return new Node(key, value, 1);
        int cmp = key.compareTo(x.key);
        if(cmp > 0){
            return x.right = put(x.right, key, value);
        }else if(cmp < 0){
            return x.left = put(x.left, key, value);
        }else {
            x.value = value;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    // todo max()/ min()/ floor() / ceiling() / select() / rank() / delete() / deleteMin() / deleteMax() / keys()

}

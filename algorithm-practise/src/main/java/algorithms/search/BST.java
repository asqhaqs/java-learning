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

    private int size(Node node){
        if( node == null )
            return 0;
        else
            return node.N;
    }


    public Value get (Key key){
        return get(root, key);
    }

    private Value get(Node x, Key key){
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

    private Node put(Node x, Key key, Value value){
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

    public Key min(){
        return min(root).key;
    }

    private Node min(Node x){
        if(x.left == null){
            return x;
        }else {
            return min(x.left);
        }
    }

    public Key floor(Key key){
        //小于等于 key 的 键值
        Node floor_node = floor(root, key);
        if(floor_node == null){
            return null;
        }else {
            return floor_node.key;
        }
    }

    private Node floor(Node x, Key key){
        if( x == null ) return null;
        int cmp = key.compareTo(x.key);
        if( cmp == 0 ) return x;
        else if( cmp < 0 ) return floor(x.left, key);
        Node right_node = floor(x.right, key);
        if( right_node == null ) return x;
        else return right_node;
    }

    public Key select(int k){
        // 找到 二叉树中排名 k 的键
        Node node = select(root, k);
        if(node == null) return null;
        else return node.key;
    }

    private Node select(Node x, int k){
        if ( x == null ) return null;
        int t = size(x.left);
        if(t > k) return select(x.left, k);
        else if(t < k) return select(x.right, k-t-1);
        else return x;
    }

    public int rank(Key key){
        // 返回以x为根节点的子树中键小于key的数量
        return rank(key, root);
    }

    private int rank(Key key, Node x){
        if(x == null) return 0;
        int t = size(x.left);
        int cmp = key.compareTo(x.key);
        if(cmp < 0){
            return rank(key, x.left);
        }else if(cmp > 0){
            return t+1+rank(key, x.right);
        }else {
            return t;
        }
    }

    public void deleteMin(){
        // 删除二叉树中键值最小的
        root = deleteMin(root);
    }

    private Node deleteMin(Node x){
        if(x.left == null) return x.right;
        x.left =  deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key){
        root =  delete(root, key);
    }

    private Node delete(Node x, Key key){

        return x;
    }

    // todo max()/ ceiling() / delete() / deleteMin() / deleteMax() / keys()

}

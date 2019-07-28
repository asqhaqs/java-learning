package algorithms.graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class BreadthFirstPaths implements Paths{
    private boolean[] marked; //到达该顶点的最短路径已知吗
    private int[] edgeTo; //到达该顶点的已知路径上最后一个顶点
    private final int s; //起点

    public BreadthFirstPaths(Graph G, int s){
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s){
        Queue<Integer> queue = new Queue<Integer>();
        marked[s] = true; // 标记起点
        queue.enqueue(s); // 将它加入队列
        while(!queue.isEmpty()){
            int v = queue.dequeue(); //从队列里删除下一顶点
            for(int w : G.adj(v)){
                if(!marked[w]){ //对于每个未被标记的相邻顶点
                    edgeTo[w] = v; // 保存最短路径的最后一条边
                    marked[w] = true; //标记该边，因为最短路径已知
                    queue.enqueue(w); //并将它添加至队列中
                }
            }
        }
    }

    public boolean hasPathTo(int v){
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v){
        //与深度优先实现相同
        if(!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for(int x = v; x != s; x = edgeTo[x]){
            path.push(x);
        }
        path.push(s);
        return path;
    }

    public static void main(String[] args) {
        Graph G = new Graph(new In("tinyCG.txt"));
        int s = Integer.parseInt("0");
        BreadthFirstPaths breadthFirstPaths = new BreadthFirstPaths(G, s);
        for(int v = 0; v < G.V(); v++){
            StdOut.print(s + "to" + v + ": ");
            if(breadthFirstPaths.hasPathTo(v)){
                for(int x: breadthFirstPaths.pathTo(v)){
                    if(x == s) StdOut.print(x);
                    else StdOut.print("-" + x);
                }
            }
            StdOut.println();
        }
    }
}

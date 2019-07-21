package algorithms.graph;
public class DepthFristSearch implements Search{
    private boolean[] marked;
    private int count;

    public DepthFristSearch(Graph G, int s){
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    //深度优先搜索
    private void dfs(Graph G, int v){
        marked[v] = true;
        count++;
        for(int w: G.adj(v)){
            if(!marked[w]) dfs(G, w);
        }
    }
    // v和 s 是连通的吗
    public boolean marked(int w){
        return marked[w];
    }

    //计算与s联通的顶点个数
    public int count(){
        return count;
    }
}

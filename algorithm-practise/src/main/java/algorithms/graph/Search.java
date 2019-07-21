package algorithms.graph;

public interface Search {

    // 找到和起点s 连通的所有顶点

    // v 和 s 是连通的吗
    public boolean marked(int v);

    // 与s 连通的顶点总数
    public int count();
}

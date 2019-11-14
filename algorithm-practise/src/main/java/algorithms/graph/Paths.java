package algorithms.graph;

public interface Paths {
    //在图G中找出所有起点为 s 的路径

    // 是否存在从s到v的路径
    public boolean hasPathTo(int v);

    // s 到 v 的路径，如果不存在则返回null
    Iterable<Integer> pathTo(int v);
}

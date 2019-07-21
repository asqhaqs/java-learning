package algorithms.graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TestSearch {
    public void testmain(String[] args) {
        Graph G = new Graph(new In(args[0]));
        int s = Integer.parseInt(args[1]);
        Search search = new DepthFristSearch(G, s);

        for(int v = 0; v < G.V(); v++){
            if(search.marked(v)){
                StdOut.print(v + " ");
            }
        }
        StdOut.println();

        if(search.count() != G.V()){
            StdOut.print("Not connected");
        }else {
            StdOut.println("connected");
        }
    }

    public static void main(String[] args) {
        TestSearch testSearch = new TestSearch();
        String[] argsList = {"tinyG.txt","10"};
        testSearch.testmain(argsList);
    }
}

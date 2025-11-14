import java.util.*;

public class B_08_03 {

    private HashMap<Integer, List<Integer>> adj = new HashMap<>();

    public void addVertex(int v) {
        adj.putIfAbsent(v, new ArrayList<>());
    }

    public void removeVertex(int v) {
        if (!adj.containsKey(v)) return;
        for (int u : adj.get(v)) {
            adj.get(u).remove((Integer) v);
        }
        adj.remove(v);
    }

    public void addEdge(int v1, int v2) {
        addVertex(v1);
        addVertex(v2);
        if (!adj.get(v1).contains(v2)) adj.get(v1).add(v2);
        if (!adj.get(v2).contains(v1)) adj.get(v2).add(v1);
    }

    public void removeEdge(int v1, int v2) {
        if (adj.containsKey(v1)) adj.get(v1).remove((Integer) v2);
        if (adj.containsKey(v2)) adj.get(v2).remove((Integer) v1);
    }

    public void printGraph() {
        for (Map.Entry<Integer, List<Integer>> e : adj.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }

    public static void main(String[] args) {
        B_08_03 g = new B_08_03();
        g.addVertex(1);
        g.addVertex(2);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addVertex(4);
        g.addEdge(2, 4);

        g.printGraph();
        System.out.println();

        g.removeEdge(1, 2);
        g.removeVertex(3);

        g.printGraph();
    }
}

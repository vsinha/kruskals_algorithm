/**
 * User: Viraj Sinha (vsinha)
 * Date: 11/4/13
 */

public class EdgeWeightedGraph {
    private final int V;        // number of vertices
    private int E;              // number of edges
    private Bag<Edge>[] adj; // adjacency lists

    public EdgeWeightedGraph(int V) {
        this.V = V; // edge numbering starts at 1 for whatever reason...
        this.E = 0;

        init();
    }

    @SuppressWarnings("unchecked")
    private void init() {
        adj = (Bag<Edge>[]) new Bag[V]; //create array of lists

        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Edge>();
        }
    }

    public void addEdge(Edge e) {

        //System.out.println("adding edge: " + e);

        int v = e.either();
        int w = e.other(v);

        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public void deleteEdge(Edge deleteMe) {
        //System.out.println("deleting edge: " + deleteMe);

        int v = deleteMe.either();
        int w = deleteMe.other(v);
        adj[v].delete(deleteMe); //delete() will simply do nothing if the edge isn't there
        adj[w].delete(deleteMe);
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Bag<Edge> b = new Bag<Edge>();

        for (int v = 0; v < V; v++) {
            for (Edge e : adj[v]) {
                if (e.other(v) > v) {
                    b.add(e);
                }
            }
        }
        return b;
    }

    public int getV() {
        return V;
    }

    public int getE() {
        return E;
    }
}
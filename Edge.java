/**
 * User: Viraj Sinha (vsinha)
 * Date: 11/4/13
 */

public class Edge implements Weighted, Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v-1; // pretend vertex indexing starts at 0 like it should
        this.w = w-1;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other (int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Inconsistent edge");
    }

    public int compareTo(Edge that)
    {
        if      (this.weight() < that.weight()) return -1;
        else if (this.weight() > that.weight()) return +1;
        else                                    return  0;
    }

    public String toString() {
        return String.format("%d %d %.2f", v+1, w+1, weight); // print +1 so it seems like vertex indexing starts at 1
    }
}
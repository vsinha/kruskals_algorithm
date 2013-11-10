import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

/**
 * User: Viraj Sinha (vsinha)
 * Date: 11/4/13
 */

public class MSTKruskal {

    public static Queue<Edge> min_st(EdgeWeightedGraph graph) {
        Queue<Edge> mst = new Queue<Edge>();
        UnionFind disjointSet = new UnionFind(graph.getV()); // initialize disjoint set data structure
        MinPriorityQueue<Edge> minpq = new MinPriorityQueue<Edge>(graph.getE()); // generate priority queue for edge costs

        for (Edge edge : graph.edges()) { //fill the priority queue
            //System.out.println("inserting edge: " + edge);
            minpq.insert(edge);
        }

        while (!minpq.isEmpty() && mst.size() < graph.getV() - 1) { // keep going until we span the graph
            Edge min = minpq.delMin();
            //System.out.println("found edge " + min);
            int v = min.either();
            int w = min.other(v);
            if (disjointSet.connected(v, w)) {
                continue;
            }
            //System.out.println("enqueueing: " + min);
            mst.enqueue(min);
            disjointSet.union(v, w);
        }

        return mst;
    }

    public static Queue<Edge>[] two_msts(EdgeWeightedGraph graph) {
        @SuppressWarnings("unchecked")
        Queue<Edge> msts[] = new Queue[2];   // to hold 2 MSTs
        msts[0] = new Queue<Edge>();

        msts[0] = min_st(graph); //this will give us the best MST found using kruskal's alg


        // now we put the second best in mst[1] if we can find one
        Edge edgeToDelete;
        for (int i = msts[0].size() - 1; i >= 0; i--) {
            edgeToDelete = msts[0].getRelativeToTop(i); //get the bottommost (ie has highest weight)

            msts[1] = msts[0];
            graph.deleteEdge(edgeToDelete); //delete the max edge from the queue's linked list
            msts[1] = min_st(graph);//and form a new mst in mst[1]

            //now compare the weights of the new trees
            //System.out.println("weight msts[0]: " + msts[0].getWeight() + " weight msts[1]: " + msts[1].getWeight());
            if (msts[0].getWeight() >= msts[1].getWeight()) { //if the weights are equal or better, we've found 2 MSTs
                return msts;
            } else { //weight of second tree is worse, just set it to null and keep trying
                msts[1] = null;
            }

            //replace the edge we deleted and try deleting another
            graph.addEdge(edgeToDelete);
        }

        return msts;
    }


    public static void main (String[] args) {
        Scanner scan;
        String nextLine;
        ArrayList<EdgeWeightedGraph> graphs;
        // defaults to scanning from stdin
        // if any input file argument is given, we will read from the file
        scan = new Scanner(System.in);

        graphs = new ArrayList<EdgeWeightedGraph>(); //initialize ArrayList of graphs
        int graphIndex; // keep track of which graph we're reading in to

        // read first 2 lines,
        int numVertices = Integer.parseInt(scan.nextLine());
        int numEdges = Integer.parseInt(scan.nextLine());
        //System.out.println(numVertices + " " + numEdges);

        // add a graph to the list
        graphs.add(new EdgeWeightedGraph(numVertices));
        graphIndex = 0; //index of graph are we currently reading into

        while (scan.hasNextLine()) { //keep scanning lines
            nextLine = scan.nextLine();
            //System.out.println("line: " + nextLine);

            // check for flags 0 or 1
            try {
                if (Integer.parseInt(nextLine) == 0) {
                    //System.out.println("0 spotted");
                    numVertices = Integer.parseInt(scan.nextLine());
                    numEdges = Integer.parseInt(scan.nextLine());

                    graphs.add(new EdgeWeightedGraph(numVertices));
                    graphIndex += 1;
                    //System.out.println("added a new graph");
                    continue;
                } else if (Integer.parseInt(nextLine) == 1) { // if we see a 1, we're done
                    break;
                }
            } catch (NumberFormatException e) {
                // ignore the exception
            }


            String[] graphInputs = nextLine.split(" ");
            //System.out.println("graphIndex: " + graphIndex + " " + graphs.size());
            graphs.get(graphIndex).addEdge(new Edge(Integer.parseInt(graphInputs[0]), Integer.parseInt(graphInputs[1]), Double.parseDouble(graphInputs[2])));
        }

        scan.close(); //we're done scanning inputs


        // call two_msts and print everything nicely
        for (int i = 0; i < graphs.size(); i++) {
            EdgeWeightedGraph g = graphs.get(i);
            Queue<Edge>[] msts = two_msts(g);

            for (int ii = 0; ii < 2; ii++) { //do this for multiple MSTs returned by two_mst
                if (msts[ii] == null) {
                    break;
                } else if (ii >= 1) {
                    System.out.println("2"); // '2' flag to signify we're printing a second MST for the same graph
                } else { //only print the weight the first time
                    System.out.println(msts[ii].getWeight());
                }

                Iterator<Edge> itr = msts[ii].iterator();
                ArrayList<String> strings = new ArrayList<String>();

                while (itr.hasNext()) { //put all edges in an array of strings so we can sort them
                    Edge e = itr.next();
                    strings.add(e.toString());
                }

                Collections.sort(strings); // sorted!
                for (int j = 0; j < strings.size(); j++) {
                    System.out.println(strings.get(j));
                }
            }

            if (i == graphs.size() - 1) {
                System.out.println("1"); // we're done, print a 1
            } else {
                System.out.println("0"); //we have more to print
            }
        }

        System.exit(0);
    }
}

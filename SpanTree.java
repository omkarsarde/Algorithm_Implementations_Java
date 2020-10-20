import java.util.Scanner;



/**
 * This program finds a minimum spanning tree such that
 * it contains subset F of edges E
 */

public class SpanTree {

    /**
     * Driver Function
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        linkedList graphWithF1 = new linkedList();
        linkedList graphWithF0 = new linkedList();//edge list, not adjacency list
        Scanner scanner = new Scanner(System.in);
        int numVertices = scanner.nextInt();
        int numEdges = scanner.nextInt();
        while (scanner.hasNext()) {
            int vert1 = scanner.nextInt();
            int vert2 = scanner.nextInt();
            int edgeWeight = scanner.nextInt();
            int fValue = scanner.nextInt();
            // if F = 1
            if (fValue == 1) {
                graphWithF1.add(new Edge(vert1, vert2, edgeWeight, fValue));
            } else {
                graphWithF0.add(new Edge(vert1, vert2, edgeWeight, fValue));
            }
        }

        SpanTree tree = new SpanTree();
        Edge[] fArray = graphWithF1.toArray();
        Edge[] wArray = graphWithF0.toArray();
        tree.sort(fArray, 0, fArray.length - 1);
        tree.sort(wArray, 0, wArray.length - 1);
        linkedList edgeList = new linkedList();
        for (int i = wArray.length - 1; i >= 0; i--) {
            edgeList.add(wArray[i]);
        }
        for (int i = fArray.length - 1; i >= 0; i--) {
            edgeList.add(fArray[i]);
        }
        Edge[] check = edgeList.toArray();
        System.out.println(tree.kruskalMST(check, numVertices));

    }

    /**
     * Helper method for sorting the weights
     *
     * @param edge edge from graoh
     * @param l    left value
     * @param m    mid value
     * @param r    right value
     */
    void merge(Edge[] edge, int l, int m, int r) {
        {
            int n1 = m - l + 1;
            int n2 = r - m;

            Edge L[] = new Edge[n1];
            Edge R[] = new Edge[n2];

            for (int i = 0; i < n1; ++i)
                L[i] = edge[l + i];
            for (int j = 0; j < n2; ++j)
                R[j] = edge[m + 1 + j];

            int i = 0, j = 0;

            int k = l;
            while (i < n1 && j < n2) {
                if (L[i].weight <= R[j].weight) {
                    edge[k] = L[i];
                    i++;
                } else {
                    edge[k] = R[j];
                    j++;
                }
                k++;
            }

            while (i < n1) {
                edge[k] = L[i];
                i++;
                k++;
            }

            while (j < n2) {
                edge[k] = R[j];
                j++;
                k++;
            }
        }
    }

    /**
     * This method sorts edges in increasing order of their weights
     *
     * @param edge edge from graph
     * @param l    left
     * @param r    right
     */
    void sort(Edge[] edge, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;

            sort(edge, l, m);
            sort(edge, m + 1, r);

            merge(edge, l, m, r);
        }
    }

    /**
     * This method performs Kruskal's algortihm
     * to find the minimum spanning tree
     * which also contains the subset F of edges E
     *
     * @param graphEdges edge array
     * @param nodeCount  number of vertices
     * @return minimum weight of spanning tree
     */
    public int kruskalMST(Edge[] graphEdges, int nodeCount) {

        linkedList tree = new linkedList();

        DisjointSet set = new DisjointSet(nodeCount + 1);

        for (int i = 0; i < graphEdges.length && tree.size < (nodeCount - 1); i++) {
            Edge currentEdge = graphEdges[i];
            int root1 = set.find(currentEdge.getVertex1());
            int root2 = set.find(currentEdge.getVertex2());
            if (root1 != root2) {
                tree.add(currentEdge);
                set.union(root1, root2);
            } else if (currentEdge.isF()) {
                return -1;
            }
        }

        int boss = -1;

        int mstTotalEdgeWeight = 0;
        for (Edge edge : tree.toArray()) {
            mstTotalEdgeWeight += edge.getWeight();
            if (boss == -1) {
                boss = set.find(edge.getVertex1());
            } else if (boss != set.find(edge.getVertex1())) {
                return -1;
            }
        }

        return mstTotalEdgeWeight;
    }
}

/**
 * Class for sets : union and find method
 */
class DisjointSet {
    private int[] set;

    public DisjointSet(int numElements) {
        set = new int[numElements];
        for (int i = 0; i < set.length; i++) {
            set[i] = -1;
        }
    }

    /**
     * Method for union of sets
     *
     * @param vert1 vertex 1
     * @param vert2 vertex 2
     */
    public void union(int vert1, int vert2) {
        if (set[vert2] < set[vert1]) {
            set[vert1] = vert2;
        } else {
            if (set[vert1] == set[vert2]) {
                set[vert1]--;
            }
            set[vert2] = vert1;
        }
    }

    /**
     * Method to find value
     * from set
     *
     * @param x value to find
     * @return
     */
    public int find(int x) {
        if (set[x] < 0) {
            return x;
        }
        int next = x;
        while (set[next] > 0) {
            next = set[next];
        }
        return next;
    }

}

/**
 * Class for all edge parameters
 */
class Edge {
    public int vertex1;
    public int vertex2;
    public int weight;
    public int checkifF;

    public Edge() {

    }

    public Edge(int vertex1, int vertex2, int weight, int f) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
        this.checkifF = f;
    }

    public int getVertex1() {
        return vertex1;
    }

    public int getVertex2() {
        return vertex2;
    }

    public int getWeight() {
        return weight;
    }

    /**
     * Method to check if F value is
     * 1 or 0
     *
     * @return
     */
    public boolean isF() {
        if (checkifF == 1) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Edge (" + getVertex1() + ", " + getVertex2() + ") weight=" + getWeight();
    }
}

/**
 * Class for linked list implementation
 */
class linkedList {
    node head;
    int size;

    void add(Edge edge) {
        if (head == null) {
            head = new node(edge);
            size++;
            return;
        }
        node newNode = new node(edge);
        newNode.next = head;
        head = newNode;
        size++;
        return;
    }

    Edge[] toArray() {
        Edge[] returnArray = new Edge[size];
        node current = head;
        int i = 0;
        while (current != null) {
            returnArray[i] = current.thisEdge;
            current = current.next;
            i++;
        }
        return returnArray;
    }
}

/**
 * Node class
 */
class node {
    node next;
    Edge thisEdge;

    public node(Edge edge) {
        thisEdge = edge;
        next = null;
    }
}

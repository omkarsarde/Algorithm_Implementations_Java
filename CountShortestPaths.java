import java.util.Scanner;


/**
 *  This program counts the shortest paths from all vertices
 *  by modifying djikstra's algorithm
 */
public class CountShortestPaths {
    Graph graph;
    PQ pq;
    int dist[], path[], finalpath[];
    int source, numVert;

    /**
     * Constructor
     * @param numVert
     * @param source
     */
    public CountShortestPaths(int numVert, int source) {
        graph = new Graph(numVert + 1);
        pq = new PQ((numVert + 1) * 99999);
        dist = new int[numVert + 1];
        path = new int[numVert + 1];
        finalpath = new int[numVert + 1];
        initDist();
        this.numVert = numVert;
        this.source = source;
    }

    /**
     * Driver function
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numVert = scanner.nextInt();
        int numEdges = scanner.nextInt();
        int source = scanner.nextInt();
        CountShortestPaths csp = new CountShortestPaths(numVert, source);
        while (scanner.hasNext()) {
            int v1 = scanner.nextInt();
            int v2 = scanner.nextInt();
            int cost = scanner.nextInt();
            csp.graph.addToGraph(v1, v2, cost);
        }
        csp.djikstras();
        for (int i = 1; i < csp.dist.length; i++) {
            if (csp.path[i] == 0) {
                System.out.println("inf 0");
            } else {
                System.out.println(csp.dist[i] + " " + csp.path[i]);
            }
        }
    }

    /**
     * Initialising all distances to max value
     */
    void initDist() {
        for (int i = 1; i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
    }

    /**
     * Function for djikstra's implementation
     */
    void djikstras() {
        dist[source] = 0;
        path[source] = 1;
        update(source);
        while (pq.size >= 0) {
            LinkNode u = pq.delete();
            update(u.vert);
        }
    }

    /**
     * Modified the update function
     * wto handle scenarios where same length paths are found
     * @param u
     */
    void update(int u) {
        LinkNode nbrsOfU[] = graph.adjList[u].nbrs();
        for (int i = 0; i < nbrsOfU.length; i++) {
            LinkNode vNode = nbrsOfU[i];
            int v = vNode.vert;
            int cost = vNode.cost;
            // one more path found of same length
            if (dist[v] == dist[u] + cost) {
                path[v] = path[v] + path[u];
                graph.predList[v].add(new LinkNode(u, cost + dist[u]));
            } else if (dist[v] > dist[u] + cost) {
                dist[v] = dist[u] + cost;
                path[v] = path[u];
                graph.predList[v].add(new LinkNode(u, cost + dist[u]));

                if (pq.contains(v) == false) {
                    pq.add(vNode);
                }
            }
        }
    }
}

/**
 * Class for graph
 */
class Graph {
    LList adjList[];
    LList predList[];
    LList current[];

    public Graph(int numVert) {
        adjList = new LList[numVert];
        predList = new LList[numVert];
        current = new LList[numVert];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new LList(i);
            predList[i] = new LList(i);
            current[i] = new LList(i);
        }
    }

    void addToGraph(int vert1, int vert2, int cost) {
        adjList[vert1].add(new LinkNode(vert2, cost));
    }

    void printGraph() {
        for (int i = 1; i < adjList.length; i++) {
            System.out.println("List for: " + i);
            adjList[i].print();
        }
    }
}

/**
 * Priority Queue class
 */
class PQ {
    LinkNode heap[];
    int size;

    public PQ(int size) {
        heap = new LinkNode[size + 1];
    }

    void add(LinkNode node) {
        size++;
        heap[size] = node;
        heapifyUp(size);
    }

    LinkNode delete() {
        LinkNode min = heap[1];
        heap[1] = heap[size];
        size--;
        heapifyDown(1);
        return min;
    }

    void heapifyUp(int index) {
        while (index > 1 && heap[index].cost < heap[getParent(index)].cost) {
            LinkNode temp = heap[getParent(index)];
            heap[getParent(index)] = heap[index];
            heap[index] = temp;
            index = getParent(index);
        }
    }

    void heapifyDown(int index) {
        int n = size;
        int j = 0;
        while ((getLeftChild(index) <= n && heap[index].cost > heap[getLeftChild(index)].cost) ||
                (getRightChild(index) <= n && heap[index].cost > heap[getRightChild(index)].cost)) {
            if (heap[getLeftChild(index)].cost < heap[getRightChild(index)].cost) {
                j = getLeftChild(index);
            } else {
                j = getRightChild(index);
            }
            LinkNode temp = heap[index];
            heap[index] = heap[j];
            heap[j] = temp;
            index = j;
        }
    }

    boolean contains(int v) {
        for (int i = 1; i < size; i++) {
            if (heap[i].vert == v) {
                return true;
            }
        }
        return false;
    }

    int getParent(int index) {
        return (index) / 2;
    }

    int getLeftChild(int index) {
        return 2 * index;
    }

    int getRightChild(int index) {
        return (2 * index) + 1;
    }

    void print() {
        for (int i = 1; i <= size; i++) {
            System.out.println(heap[i]);
        }
    }
}

/**
 * Linked List Class
 */
class LList {
    LinkNode head;
    int size;
    int selfVert;

    public LList(int i) {
        selfVert = i;
    }

    void add(LinkNode node) {
        if (size == 0) {
            head = node;
            size++;
            return;
        }
        node.next = head;
        head = node;
        size++;
        return;
    }

    LinkNode[] nbrs() {
        LinkNode returnArray[] = new LinkNode[size];
        LinkNode current = head;
        int index = 0;
        while (current != null) {
            returnArray[index] = current;
            current = current.next;
            index++;
        }
        return returnArray;
    }

    void print() {
        LinkNode current = head;
        while (current != null) {
            System.out.print(current + " ");
            current = current.next;
        }
        System.out.println("");
    }
}

/**
 * Node class
 */
class LinkNode {
    int vert;
    int cost;
    LinkNode next;

    public LinkNode(int vert, int cost) {
        this.vert = vert;
        this.cost = cost;
    }

    public String toString() {
        return " vert: " + vert + " cost: " + cost;
    }
}
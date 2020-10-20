import java.util.Scanner;



/**
 * This program finds if two vertices which are previously not connected
 * can be connected,
 * and if so, connects them using modification of Edmonds-Karp algorithm
 */
public class NetworkConnect {

    /**
     * Driver function
     * @param args
     */
    public static void main(String[] args)  {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            int e = scanner.nextInt();
            int s = scanner.nextInt() - 1;
            int t = scanner.nextInt() - 1;

            Graph graph = new Graph(n, e, s, t);


            int count = e;
            while (count > 0) {
                int u = scanner.nextInt() - 1;
                int v = scanner.nextInt() - 1;
                int w = scanner.nextInt();
                graph.addEdge(u, v, w);
                count--;
            }
            scanner.close();
            graph.edmondsKarp(s, t);
            graph.mergingSourceSink();
            graph.connectVertices();
    }

}


/***
 * The Graph Class
 *
 */
class Graph {

    private final int V;
    private final int E;
    private final int S;
    private final int T;
    private LinkedList[] g;
    private int[][] edge;
    private int[] sourceResidues;
    private int[] sinkResidues;

    /**
     * Constructor
     */
    public Graph(int V, int E, int S, int T) {

        this.V = V;
        this.E = E;

        this.S = S;
        this.T = T;

        g = new LinkedList[V];
        for (int v = 0; v < V; v++)
            g[v] = new LinkedList();

        edge = new int[V][V];
        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++)
                edge[i][j] = Integer.MAX_VALUE;
    }

    /***
     * Adds the weighted forward and backward edges on this graph
     * All edges are bi-directed, so helps to create the residual graph
     * @param v one vertex
     * @param w the other vertex
     */
    public void addEdge(int u, int v, int w) {
        g[u].add(v);
        g[v].add(u);
        edge[u][v] = w;
        edge[v][u] = 0;
    }

    /***
     * This function is used for printing out the vertices
     * from two set that are in lexicographically order
     */
    public void connectVertices() {
        int flag = 0;
        for (int i = 0; i < sourceResidues.length; i++) {
            if (flag == 0) {
                for (int j = 0; j < sinkResidues.length; j++) {
                    if (!isEdge(sourceResidues[i], sinkResidues[j])) {
                        flag = 1;
                        System.out.println((sourceResidues[i] + 1) + " " + (sinkResidues[j] + 1));
                        break;
                    }
                }
            }
        }
    }

    /***
     * Checks if 2 vertices are connected to form edge
     * @param v One vertex
     * @param w Another vertex
     * @return
     */
    public boolean isEdge(int v, int w) {
        return (g[v].checkIfValuePresent(w) && g[w].checkIfValuePresent(v));
    }

    /***
     * The function gets 2 groups of vertices which we get from source and sink and
     * then merge them in increasing order
     */
    public void mergingSourceSink() {
        sourceResidues = bfsSource(S);
        mergeSort(sourceResidues, 0, sourceResidues.length - 1);
        sinkResidues = bfsSink(T);
        mergeSort(sinkResidues, 0, sinkResidues.length - 1);

    }

    /***
     * This function uses BFS search to mark all vertices from sink vertex in
     * final residual graph
     * @param start The starting vertex
     * @return The array of a vertices' set.
     */
    public int[] bfsSink(int start) {
        LinkedList list = new LinkedList();
        boolean seen[] = new boolean[V];
        Queue queue = new Queue();
        seen[start] = true;
        queue.enqueue(start);
        list.add(start);
        while (queue.count() != 0) {

            start = queue.dequeue().value;

            LinkedList neighbors = g[start];

            for (int v = 0; v < neighbors.count(); v++) {
                int n = neighbors.get(v);
                // The search stops at a vertex that has backward edge equal to 0
                if (!seen[n] && edge[n][start] > 0) {
                    seen[n] = true;
                    queue.enqueue(n);
                    list.add(n);
                }
            }
        }
        int[] residuesFromSink;
        residuesFromSink = new int[list.count()];
        for (int i = 0; i < list.count(); i++) {
            residuesFromSink[i] = list.get(i);
        }
        return residuesFromSink;
    }

    /***
     * The function uses BFS search to mark all vertices from source vertex in
     * final residual graph
     * @param s source / start vertex
     * @return array of vertices
     */
    public int[] bfsSource(int s) {
        LinkedList list = new LinkedList();

        boolean seen[] = new boolean[V];

        Queue queue = new Queue();
        seen[s] = true;
        queue.enqueue(s);
        list.add(s);
        while (queue.count() != 0) {
            s = queue.dequeue().value;

            LinkedList neighbors = g[s];

            for (int v = 0; v < neighbors.count(); v++) {
                int n = neighbors.get(v);

                // The search stops at a vertex that has forward edge equal to 0
                if (!seen[n] && edge[s][n] > 0) {
                    seen[n] = true;
                    queue.enqueue(n);
                    list.add(n);
                }
            }
        }

        int[] residuesFromSource;
        residuesFromSource = new int[list.count()];
        for (int i = 0; i < list.count(); i++) {
            residuesFromSource[i] = list.get(i);
        }
        return residuesFromSource;
    }

    /**
     * The function uses Edmonds-Karp algorithm to
     * find the minimum s-t cut
     * @param s source
     * @param t sink
     */
    public void edmondsKarp(int s, int t) {
        int maxflow;
        int parent[] = new int[V];
        for (int v = 0; v < V; v++) {
            parent[v] = -1;
        }

        maxflow = findPath(s, t, parent);

        while (maxflow != -1) {
            getResidualGraph(s, t, maxflow, parent);
            for (int v = 0; v < V; v++) {
                parent[v] = -1;
            }
            maxflow = findPath(s, t, parent);
        }
    }

    /***
     * This function is used for getting the residual graph each time for Edmonds-Karp Algorithm.
     * @param s source
     * @param t sink
     * @param maxFlow max flow of current path
     * @param predecessors The array representing the path
     */
    public void getResidualGraph(int s, int t, int maxFlow, int[] predecessors) {
        int index = t;
        while (predecessors[index] != -1) {
            edge[predecessors[index]][index] -= maxFlow;
            edge[index][predecessors[index]] += maxFlow;
            index = predecessors[index];
        }
    }

    /***
     * The function uses BFS to find an augment path
     * and return the max flow from this path
     * @param s source
     * @param t sink
     * @param parent The array to store the path
     * @return the max flow
     */
    public int findPath(int s, int t, int[] parent) {

        boolean flag = false;
        boolean seen[] = new boolean[V];
        int dist[] = new int[V];
        for (int v = 0; v < V; v++) {
            dist[v] = Integer.MAX_VALUE;
        }

        Queue queue = new Queue();
        seen[s] = true;
        dist[s] = 0;
        queue.enqueue(s);

        while (queue.count() != 0) {
            s = queue.dequeue().value;

            // Ends when path reaches sink vertex
            if (s == t) {
                flag = true;
                break;
            }
            LinkedList neighbors = g[s];

            for (int v = 0; v < neighbors.count(); v++) {
                int n = neighbors.get(v);

                if (!seen[n] && edge[s][n] > 0) {
                    seen[n] = true;
                    queue.enqueue(n);

                    parent[n] = s;
                }
            }
        }
        if (flag) {
            int index = t;
            int maxFlow = 10000;
            while (parent[index] != -1) {

                if (maxFlow > edge[parent[index]][index]) {
                    maxFlow = edge[parent[index]][index];
                }
                index = parent[index];
            }
            return maxFlow;
        }
        else return -1;
    }

    /***
     * Function for merge sort
     * @param array
     * @param l
     * @param m
     * @param r
     */
    void merge(int[] array, int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;

        int left[] = new int [n1];
        int right[] = new int [n2];

        for (int i=0; i<n1; ++i)
            left[i] = array[l + i];
        for (int j=0; j<n2; ++j)
            right[j] = array[m + 1+ j];

        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2)
        {
            if (left[i] <= right[j])
            {
                array[k] = left[i];
                i++;
            }
            else
            {
                array[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < n1)
        {
            array[k] = left[i];
            i++;
            k++;
        }

        while (j < n2)
        {
            array[k] = right[j];
            j++;
            k++;
        }
    }

    /**
     * This function works for array of edges in increasing order
     * @param array The array of Edge instances
     * @param l starting index
     * @param r ending index
     */
    void mergeSort(int[] array, int l, int r)
    {
        if (l < r)
        {
            int m = (l+r)/2;
            mergeSort(array, l, m);
            mergeSort(array , m+1, r);

            merge(array, l, m, r);
        }
    }
}


/***
 * Class for node
 *
 */
class Node {
    int value;
    Node next;
    Node(int value){
        this.value = value;
        this.next = null;
    }
}

/**
 * Class for Queue
 */
class Queue {
    Node front, rear;
    public Queue() {
        this.front =  null;
        this.rear = null;
    }

    public void enqueue(int value) {
        Node temp = new Node(value);

        if (rear == null) {
            rear = temp;
            front = temp;
            return;
        }
        rear.next = temp;
        rear = temp;
    }
    public Node dequeue() {
        if (front == null) return null;
        Node temp = front;
        front = front.next;
        if (front == null) rear = null;
        return temp;
    }
    public void printList() {
        Node current = front;
        if (current == null) {
            System.out.println("Null LinkedList!");
            return;
        }
        while(current != null) {
            System.out.println(current.value);
            current = current.next;
        }
    }

    /**
     * Returns count of of nodes in linked list.
     * @return
     */
    public int count() {
        Node temp = front;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }
}
/***
 * LinkedList class
 *
 */
class LinkedList {
    Node head;
    Node next;
    Node tail;

    public LinkedList() {}

    /***
     * Append the node with value of new_data at the tail of the linked list
     * @param value
     */
    public void add(int value) {

        Node new_node = new Node(value);
        new_node.next = null;

        if (head == null) {
            head = new_node;
            tail = new_node;
            return;
        }

        Node last = head;
        while(last.next != null) last = last.next;

        last.next = new_node;
        tail = new_node;
        return;
    }
    /**
     * Returns count of of nodes in linked list.
     * @return
     */
    public int count() {
        Node temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    /***
     * Take index as an argument and return the data if index-th element exists.
     * @param index
     * @return
     */
    public int get(int index) {
        Node current = head;
        int count = 0;

        while (current != null) {
            if (count == index)
                return current.value;
            count++;
            current = current.next;
        }
        return -1;
    }

    public String toString() {
        String str = new String();
        Node current = head;
        if (current == null) {
            str += "Null LinkedList!";
            return str;
        }
        while(current != null) {
            str += Integer.toString(current.value + 1) + "  ";
            current = current.next;
        }
        return str;
    }

    /***
     * Check whether the value is present in linked list.
     * @param value
     * @return
     */
    public boolean checkIfValuePresent(int value) {
        Node current = head;
        while (current != null) {
            if (current.value == value)
                return true;
            current = current.next;
        }
        return false;
    }

    public void printList() {
        Node current = head;
        if (current == null) {
            System.out.println("Null LinkedList!");
            return;
        }
        while(current != null) {
            System.out.println(current.value + " ");
            current = current.next;
        }
    }
}
import java.util.Scanner;

/**
 * This program uses breadth-first search to traverse
 * through a maze with 3 colors.
 */
public class ColorfulMaze {

    /**
     * Driver function
     * @param args
     * @throws Exception
     */
    public static void main(String[] args){
        ColorfulMaze test = new ColorfulMaze();
        Scanner scanner = new Scanner(System.in);
        int numVertices = scanner.nextInt() + 1;
        int edgesNum = scanner.nextInt();
        int startVertex = scanner.nextInt();
        int endVertex = scanner.nextInt();
        GNode[] gNodes = new GNode[numVertices];
        for (int i = 1; i < numVertices; i++) {
            gNodes[i] = new GNode(i);
        }
        while (scanner.hasNext()) {
            int vert1 = scanner.nextInt();
            int vert2 = scanner.nextInt();
            int color = scanner.nextInt();
            gNodes[vert1].adjList.add(vert2, color);
            gNodes[vert2].adjList.add(vert1, color);
        }
        test.bfs(gNodes,startVertex,endVertex);
    }

    /**
     * The bfs method which handles 3 queues for
     * 3 colors
     * @param gNodes graph nodes
     * @param start start vertex
     * @param end end vertex
     */
    void bfs(GNode[] gNodes, int start, int end) {
        int n = gNodes.length;
        // queues for handling the 3 colors
        queue que = new queue();
        queue red = new queue();
        queue yellow = new queue();
        queue blue = new queue();
        queue dist = new queue();

        red.add(start);
        dist.add(0);
        // 2D matrix for storing incoming and outgoing edges
        int[][] matrix = new int[n][n];
        GNode curr;
        int temp=-1;
        while (!red.isEmpty() || !yellow.isEmpty() || !blue.isEmpty()) {


            if (!red.isEmpty() && blue.isEmpty()) {
                int qVer = red.remove();
                int distance = dist.remove();
                curr = gNodes[qVer];
                if(qVer == end){
                    temp = distance;
                    break;
                }
                for (int i = 0; i < curr.adjList.nbrs().length; i++) {
                    int vert = curr.adjList.nbrs()[i].selfV;
                    int clrV = curr.adjList.nbrs()[i].clr;
                    if (matrix[qVer][vert] != 1) {
                        // condition for red color
                        if (clrV == 1) {
                            yellow.add(vert);
                            dist.add(distance + 1);
                            matrix[qVer][vert] = 1;
                        }
                    }

                }
            }
            if (!yellow.isEmpty() && red.isEmpty()) {
                int qVer = yellow.remove();
                int distance = dist.remove();
                curr = gNodes[qVer];
                for (int i = 0; i < curr.adjList.nbrs().length; i++) {
                    int vert = curr.adjList.nbrs()[i].selfV;
                    int clrV = curr.adjList.nbrs()[i].clr;
                    if (matrix[qVer][vert] != 1) {
                        // for yellow color
                        if (clrV == 2) {
                            blue.add(vert);
                            dist.add(distance + 1);
                            matrix[qVer][vert] = 1;
                        }
                    }

                }
            }
            if (!blue.isEmpty() && yellow.isEmpty()) {
                int qVer = blue.remove();
                int distance = dist.remove();
                curr = gNodes[qVer];
                for (int i = 0; i < curr.adjList.nbrs().length; i++) {
                    int vert = curr.adjList.nbrs()[i].selfV;
                    int clrV = curr.adjList.nbrs()[i].clr;
                    if (matrix[qVer][vert] != 1) {
                        // for blue color
                        if (clrV == 3) {
                            red.add(vert);
                            dist.add(distance + 1);
                            matrix[qVer][vert] = 1;
                        }
                    }

                }
            }
        }
        System.out.println(temp);
    }
}


/**
 * Class GNode for handling graph nodes
 */
class GNode {
    int verNum;
    GNode pred, succ;
    Linkedlist adjList;

    public GNode(int vertexNumber) {
        this.verNum = vertexNumber;
        this.adjList = new Linkedlist(vertexNumber);

    }

}

/**
 * Linked List class
 */
class Linkedlist {
    Node head;
    int size, listFor;

    public Linkedlist(int i) {
        listFor = i;
    }

    void add(int ver2, int clr) {
        Node newNode = new Node(ver2, clr);
        // System.out.println("node: "+newNode.vertex+ "adding node to: " +vertexNumber);
        if (head == null) {
            head = newNode;
            size++;
            return;
        }
        newNode.next = head;
        head = newNode;
        size++;
        return;
    }

    Node[] nbrs() {
        Node current = head;
        Node[] returnArray = new Node[size];
        //int[] returnArray = new int[size];
        int i = 0;
        while (current != null) {
            returnArray[i] = current;
            current = current.next;
            i++;
        }
        return returnArray;
    }

    void print() {
        Node curr = head;
        System.out.print("adjFOR: " + listFor + " ->");
        while (curr != null) {
            System.out.print("v2: " + curr.selfV + " clr: " + curr.clr + "||");
            curr = curr.next;
        }
        System.out.println("");
    }
}

/**
 * Node class
 */
class Node {
    int selfV, clr;
    Node next;

    public Node(int vert, int clr) {
        selfV = vert;
        this.clr = clr;
        next = null;
    }
}

class queue{
    qNode front,rear;
    int size;
    void add(int vertex){
        qNode node = new qNode(vertex);
        if(rear==null){
            front = node;
        }else {
            rear.next = node;
            node.prev = rear;
        }
        rear = node;
        size++;
    }
    void print() {
        qNode current = front;
        while (current != null) {
            System.out.print("-> " + current.vertex);
            current = current.next;
        }
    }

    int remove(){
        qNode removedNode = front;
        if(isEmpty()){
            return -1;
        }
        if(front.next==null){
            rear = null;
        }else {
            front.next.prev = null;
        }
        front = front.next;
        size--;
        return removedNode.vertex;
    }
    boolean isEmpty(){return size==0;}

}
class qNode{
    int vertex;
    qNode next;
    qNode prev;
    public qNode(int vertex){
        this.vertex = vertex;
        this.next = null;
        this.prev = null;
    }
}
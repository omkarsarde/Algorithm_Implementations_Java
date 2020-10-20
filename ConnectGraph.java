import java.io.FileReader;
import java.util.Scanner;


/**
 * This program finds the smallest number of
 * edges required to make the graph connected
 */
public class ConnectGraph {
    AList[] adjacencyList;
    boolean[] seen;
    int countPath[];

    /**
     * Constructor
     * @param adjacencyList
     */
    public ConnectGraph(AList[] adjacencyList) {
        this.adjacencyList = adjacencyList;
        seen = new boolean[adjacencyList.length];
        countPath = new int[adjacencyList.length];
    }

    /**
     * Driver Function
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new FileReader("nums.txt"));
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        //LList[] adjLL = new LList[n + 1];
        AList[] adjAA = new AList[n + 1];
        for (int i = 1; i < adjAA.length; i++) {
            //adjLL[i] = new LList(i);
            adjAA[i] = new AList(i,n);
        }

        while (scanner.hasNext()) {
            int vert = scanner.nextInt();
            int connectedTo = scanner.nextInt();
            //adjLL[vert].add(connectedTo);
            //adjLL[connectedTo].add(vert);
            adjAA[vert].add(connectedTo);
            adjAA[connectedTo].add(vert);
        }
        scanner.close();
       // double time = System.nanoTime();
        ConnectGraph test = new ConnectGraph(adjAA);
        test.dfsRun();
       // double end = System.nanoTime();
       // double total = (end-time);
       //  System.out.println(total + " time");
    }

    /**
     * Method runs depth-first on the adjacency list
     * and returns the count of edges that are needed to
     * connect the graph
     */
    void dfsRun() {
        int count = 0;
        for(int i =1;i<adjacencyList.length;i++){
            if(!seen[i]){
                dfs(i);
            }
        }
        if(count!=0) {
            System.out.println(count - 1);
        }
    }

    /**
     * This method runs Depth-first traversal
     * @param start
     */
    void dfs(int start) {
        seen[start] = true;
        for (int i =0;i<adjacencyList[start].neighbors().length;i++) {
            int vert = adjacencyList[start].neighbors()[i];
            if (!seen[vert]) {
                dfs(vert);
            }
        }
    }
}

/**
 * Linked list representation
 */
class LList{
    node head;
    int listFor;
    int size;
    public LList(int vertex){
        listFor = vertex;
    }

    void add(int toAdd){
        if(toAdd==listFor){
            return;
        }
        node newNode = new node(toAdd);
        if (head == null){
            head = newNode;
            size++;
            return;
        }
        newNode.next = head;
        head = newNode;
        size++;
        return;
    }
    Integer[] neighbors(){
        Integer [] returnArray = new Integer[size];
        node current = head;
        int i = 0;
        while (current!=null) {
            returnArray[i] = current.vertexNumber;
            current = current.next;
            i++;
        }
        return returnArray;
    }
    void printNeighbors(){
        for(int i:neighbors()){
            System.out.print(i +" ");
        }
    }
    void print(){
        node current = head;
        System.out.println("************ in list for: "+listFor+ " **********");
        while (current!=null){
            System.out.print(current.vertexNumber+" ");
            current = current.next;
        }
        System.out.println();
    }

}

/**
 * Node class for linked list
 */
class node{
    int vertexNumber;
    node next;
    public node(int vertexNumber){
        this.vertexNumber = vertexNumber;
        this.next = null;
    }
}

/**
 * Class for adjacency list
 */
class AList{
    int vertNumber;
    Integer[] adjacenyList;
    int size;
    public AList(int vertNumber, int n){
        this.vertNumber = vertNumber;
        adjacenyList = new Integer[n+1];
    }
    void add(int num){
        if(num!=vertNumber) {
            adjacenyList[num] = num;
            size++;
        }
    }
    Integer[] neighbors(){
        Integer[] returnArray = new Integer[size];
        int counter = 0;
        for(Integer i: adjacenyList){
            if(i!=null){
                returnArray[counter] = i;
                counter++;
            }
        }
        return returnArray;
    }
    void print(){
        System.out.println("************ in list for: "+vertNumber+ " **********");
        for(Integer i : adjacenyList){
            if(i ==null){
                continue;
            }
            System.out.print(i+ " ");
        }
        System.out.println();
    }
}
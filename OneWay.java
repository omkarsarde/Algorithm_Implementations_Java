import java.util.Scanner;


/**
 * This program finds the Strongly Connected Components
 * and determines if the problem is solvable or not
 */


public class OneWay {
    linkedList[] adajencyList, reversedList;
    int count;

    public OneWay(linkedList[] adajencyList, linkedList[] reversedList) {
        this.adajencyList = adajencyList;
        this.reversedList = reversedList;
    }


    /**
     * Driver function
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numVert = scanner.nextInt();
        linkedList[] adjacencyList = new linkedList[numVert + 1];
        linkedList[] reversedList = new linkedList[numVert + 1];
        for (int i = 1; i < numVert + 1; i++) {
            adjacencyList[i] = new linkedList(i);
            reversedList[i] = new linkedList(i);
        }
        for (int i = 1; i < numVert + 1; i++) {
            int toAdd = scanner.nextInt();
            while (toAdd != 0) {
                adjacencyList[i].add(toAdd);
                reversedList[toAdd].add(i);
                toAdd = scanner.nextInt();
            }
        }
        // method call
        OneWay test = new OneWay(adjacencyList, reversedList);
        test.scc();
    }

    /**
     * Method for DFS
     *
     * @param adjacencyList linked list of neighbours
     * @param start         start vertex
     * @param visited       visited nodes
     * @param stack         stack
     */
    void stackDfs(linkedList[] adjacencyList, int start, int[] visited, stack stack) {
        visited[start] = 1;
        for (int i = 0; i < adjacencyList[start].neighbors().length; i++) {
            int vert = adjacencyList[start].neighbors()[i];
            if (visited[vert] == 0) {
                stackDfs(adjacencyList, vert, visited, stack);
            }
        }
        stack.push(start);
    }

    /**
     * Dfs traversal
     *
     * @param adjacencyList linked list of neighbours
     * @param start         start vertex
     * @param visited       visited array of nodes
     * @param count         count of scc
     */
    void dfs(linkedList[] adjacencyList, int start, int[] visited, int count) {
        if (visited[start] == 0) {
            visited[start] = 1 + count;
        }
        for (int i = 0; i < adjacencyList[start].neighbors().length; i++) {
            int vert = adjacencyList[start].neighbors()[i];
            if (visited[vert] == 0) {
                dfs(adjacencyList, vert, visited, count);
            }
        }
    }

    /**
     * Method to find scc
     */
    void scc() {
        int n = this.adajencyList.length;
        stack stack = new stack();
        int[] visited = new int[n];
        for (int i = 1; i < n; i++) {
            if (visited[i] == 0) {
                stackDfs(this.adajencyList, i, visited, stack);
            }
        }
        for (int i = 0; i < visited.length; i++) {
            visited[i] = 0;
        }
        int v = 0;
        while (!stack.isEmpty()) {
            v = stack.pop();
            if (visited[v] == 0) {
                dfs(reversedList, v, visited, this.count);
                this.count++;
            }
        }
        int[] indegree = new int[count + 1];
        int[] outdegree = new int[count + 1];
        int[] current = new int[count + 1];

        int v0 = 0, v1 = 0;
        for (int i = 1; i < n; i++) {
            int[] tempList = adajencyList[i].neighbors();
            if (current[visited[i] - 1] == 0) {
                current[visited[i] - 1] = i + 1;
            }
            for (int j = 0; j < tempList.length; j++) {
                int vert = tempList[j];
                if (visited[vert] != visited[i]) {
                    indegree[visited[vert] - 1]++;
                    outdegree[visited[i] - 1]++;
                }
            }
        }
        int flag = 0;
        for (int i = 0; i < count; i++) {
            if (outdegree[i] == 0) {
                if (v0 == 0) {
                    v0 = current[i];
                } else {
                    System.out.println("NO");
                    flag = 1;
                    break;
                }
            }
            if (indegree[i] == 0) {
                if (v1 == 0) {
                    v1 = current[i];
                } else {
                    System.out.println("NO");
                    flag = 1;
                    break;
                }
            }
        }
        if (flag == 0) {
            System.out.println("YES");
            System.out.println((v0 - 1) + " " + (v1 - 1));
        }

    }
}

/**
 * Class for stack
 */
class stack {
    node top;
    int size;

    public stack() {
    }

    void push(int toPush) {
        node newNode = new node(toPush);
        if (top == null) {
            top = newNode;
            size++;
            return;
        } else {
            newNode.next = top;
            top = newNode;
            size++;
            return;
        }
    }

    int pop() {
        node holder = top;
        top = top.next;
        size--;
        return holder.vertexNumber;
    }

    boolean isEmpty() {
        return size == 0;
    }
}

/**
 * Class for linked list
 */
class linkedList {
    node head;
    int listFor;
    int size;

    public linkedList(int vertex) {
        listFor = vertex;
    }

    void add(int toAdd) {
        if (toAdd == listFor) {
            return;
        }
        node newNode = new node(toAdd);
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

    int[] neighbors() {
        int[] returnArray = new int[size];
        node current = head;
        int i = 0;
        while (current != null) {
            returnArray[i] = current.vertexNumber;
            current = current.next;
            i++;
        }
        return returnArray;
    }

    void printNeighbors() {
        for (int i : neighbors()) {
            System.out.print(i + " ");
        }
    }

    void print() {
        node current = head;
        System.out.println("************ in list for: " + listFor + " **********");
        while (current != null) {
            System.out.print(current.vertexNumber + " ");
            current = current.next;
        }
        System.out.println();
    }

}

/**
 * Node class
 */
class node {
    int vertexNumber;
    node next;

    public node(int vertexNumber) {
        this.vertexNumber = vertexNumber;
        this.next = null;
    }
}
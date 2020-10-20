import java.util.Scanner;



/**
 * This program finds the maximum cost of items
 * which we fit in 2 knapsacks
 */

public class DoubleKnapsackWithSolution {
    /**
     * Driver function
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String[] initials =  scanner.nextLine().split(" ");
        item[] items = new item[Integer.parseInt(initials[0])];
        int W1 = Integer.parseInt(initials[1]), W2=Integer.parseInt(initials[1]);
        int counter = 0;
        while (scanner.hasNext()){
            item temp = new item(scanner.nextInt(),scanner.nextInt());
            items[counter] = temp;
            counter++;
        }
        scanner.close();
        int[][][]matrix = solve(items,W1,W2);
        solve2(matrix,items, W1, W2);
    }

    /**
     * This method checks if max cost is attained
     * when item is in either of 1 bags or
     * @param items array of items
     * @param w1
     * @param w2
     * @return
     */
    private static int[][][] solve(item[] items,int w1, int w2){
        int n = items.length;
        item currItem ;
        int[][] previous;
        int[][][] matrix = new int[n+1][w1+1][w2+1];
        for(int k = 0;k<n+1;k++){
            for(int y= 0;y<=w1;y++) {
                for (int z = 0; z <=w2; z++) {
                    if (k == 0) {
                        matrix[k][y][z] = 0;
                    }
                    else {
                        currItem = items[k - 1];
                        previous = matrix[k - 1];
                        if (currItem.weight <= y && currItem.weight <= z) {
                            matrix[k][y][z] = Math.max(previous[y][z],
                                    Math.max(currItem.cost + previous[y - currItem.weight][z],
                                            currItem.cost + previous[y][z - currItem.weight]));
                        }
                        else if (currItem.weight <= y) {
                            matrix[k][y][z] = Math.max(previous[y][z], currItem.cost + previous[y - currItem.weight][z]);
                        }
                        else if (currItem.weight <= z) {
                            matrix[k][y][z] = Math.max(previous[y][z], currItem.cost + previous[y][z - currItem.weight]);
                        }
                        else {
                            matrix[k][y][z] = previous[y][z];
                        }
                    }
                }
            }
        }
        System.out.println(matrix[n][w1][w2]);
        return matrix;
    }

    /**
     * This method checks if item goes in both bags,
     * and if so, adds item to bag with max cost
     * @param matrix
     * @param items array of items
     * @param W1 weight of bag 1
     * @param W2 weight of bag 2
     */
    private static void solve2(int[][][] matrix,item[] items, int W1, int W2){
        int y = W1, z = W2;
        LL bag1 = new LL(), bag2 = new LL();
        for(int i = items.length-1;i>=0;i--){
            item underConsideration = items[i];
            int current = matrix[i+1][y][z];
            int[][] previous = matrix[i];
            if(y>=underConsideration.weight &&
                    current == underConsideration.cost + previous[y-underConsideration.weight][z]){
                bag1.add(i+1);
                y -= underConsideration.weight;
            } else if(z>=underConsideration.weight &&
                    current == underConsideration.cost + previous[y][z-underConsideration.weight]){
                bag2.add(i+1);
                z -= underConsideration.weight;
            }
        }
        bag1.print();
        bag2.print();
    }
}

/**
 * Class for items
 */
class item{
    int weight;
    int cost;
    public item(int weight,int cost){
        this.weight = weight;
        this.cost = cost;
    }
}

/**
 * Class for linked list
 */
class LL{
    node head;
    int size;
    public LL(){
        this.head=null;
    }

    /**
     * Adding to link list
     * @param newNodeData
     */
    public void add(int newNodeData){
        node toAdd = new node(newNodeData);
        if(head == null){
            head = toAdd;
            size++;
            return;
        }
        toAdd.next = head;
        head = toAdd;
        size++;
        return;
    }

    /**
     * Printing linked list
     */
    public void print(){
        node current = head;
        while(current != null){
            System.out.print(current+" ");
            current = current.next;
        }
        System.out.println();
    }

}

/**
 * Node class
 */
class node{
    node next;
    int data;
    public node(int data){
        this.data = data;
        this.next = null;
    }
    public String toString(){
        return  ""+ this.data;
    }
}
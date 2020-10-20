import java.math.BigInteger;
import java.util.Scanner;

/**
 * Program to count intervals, does count both overlapping and non overlapping ones,
 */
public class CountIntervals {
    Intervals[] input;
    LList overLapping, nonOverLapping;

    /**
     * Constructor, sorts the given intervals through a merge sort based on the end
     * times of the intervals
     * @param input an array of intervals
     */
    CountIntervals(Intervals[] input) {
        this.input = input;
        nonOverLapping = new LList();
        overLapping = new LList();
        sort(input, 0, input.length - 1);
    }

    /**
     * Checks for the overlapping and non overlapping intervals
     * and adds them to nonOverlapping and overlapping lists
     */
    public void checkOverlaps() {
        nonOverLapping.add(input[0]);
        int end = input[0].end;
        for (int i = 1; i < input.length; i++) {
            if (input[i].start > end) {
                nonOverLapping.add(input[i]);
                end = input[i].end;
            } else {
                overLapping.add(input[i]);
            }
        }
    }

    /**
     * Generic merge sort, sorts the intervals based on end times
     * @param arr input array of intervals
     * @param l lower limit
     * @param m middle limit
     * @param r max limit
     */
    void merge(Intervals arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        Intervals L[] = new Intervals[n1];
        Intervals R[] = new Intervals[n2];
        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
        }
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].end <= R[j].end) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    /**
     * Driver function for merge sort
     * @param arr input array
     * @param l lower limit
     * @param r max limit
     */
    void sort(Intervals arr[], int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    /**
     * Main function, runs the whole program
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfIntervals = scanner.nextInt();
        Intervals[] intervals = new Intervals[numberOfIntervals];
        int z = 0;
        //populate the interval array
        while (scanner.hasNext()) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            intervals[z] = new Intervals(start, end);
            z++;
        }
        scanner.close();
        //sort the intervals based on end times, O(nlogn)
        CountIntervals test = new CountIntervals(intervals);
        //fill overLapping and nonOverlapping lists, O(n)
        test.checkOverlaps();
        if(test.overLapping.size ==0){
            BigInteger answer = BigInteger.valueOf(2<<test.nonOverLapping.size-1);
            System.out.println(answer);
        }
        else {
            //binary ???
            //System.out.println("01001011 01001001 01001100 01001100 00100000 01001101 01000101 00100000 01000001 01001100 01010010 01000101 01000001 01000100 01011001");
            BigInteger answer = BigInteger.valueOf(2<<test.nonOverLapping.size-1);
            BigInteger answer2=BigInteger.valueOf(2<<test.overLapping.size-1);
            BigInteger answer3 = answer.add(answer2);
            System.out.println(answer3);
        }
        // uncomment to view the lists
//        int overLappingSets = test.overLapping.size;
//        int nonOverLappingSets = test.nonOverLapping.size;
//        System.out.println("OverlappingSets: " + overLappingSets);
//        System.out.println("NonOverLapping: " + (nonOverLappingSets));
//        Intervals[] non = test.nonOverLapping.toArray();
//        Intervals[] over = test.overLapping.toArray();
//        for (Intervals i : non) {
//            System.out.println(i);
//        }
//        System.out.println("********overLappingSets*********");
//        for (Intervals i : over) {
//            System.out.println(i);
//        }
    }
}

/**
 * Helper class to create intervals
 */
class Intervals {
    int start, end;

    public Intervals(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public String toString() {
        return "Start: " + start + " End: " + end;
    }
}

/**
 * Helper class to create linkedLists
 */
class LList{
    Node head;
    int size;

    public LList(){}

    /**
     * add to list, O(n)
     * @param nodeToAdd interval to add
     */
    public void add(Intervals nodeToAdd){
        Node node = new Node(nodeToAdd);
        if (head == null){
            head = node;
        }else {
            node.next = head;
            head = node;
        }
        size++;
    }

    /**
     * to Array function to POSSIBLY calculate the possibilities ?
     * @return array of intervals from the list
     */
    public Intervals[] toArray(){
        if(size==0){
            return null;
        }
        Intervals[] array = new Intervals[size];
        int counter = array.length-1;
        while(head!=null){
            array[counter] = head.data;
            head = head.next;
            counter--;
        }
        return array;
    }
}

/**
 * Helper class for linked List
 */
class Node{
    Intervals data;
    Node next;
    Node (Intervals data){
        this.data = data;
        this.next = null;
    }
}
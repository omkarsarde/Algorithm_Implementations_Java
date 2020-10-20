import java.util.Scanner;



public class MaxRectangle {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        long numPoints = scanner.nextLong();
        RectNode[] points = new RectNode[(int) numPoints];
        long n = 0L;
        while (scanner.hasNext()) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points[(int) n] = new RectNode(x, y);
            n++;
        }
        scanner.close();
        MaxRectangle test = new MaxRectangle();
        System.out.println(test.getMaxArea(test.dataParser(points), test.dataParser(points).length));
    }

    /**
     * Converts the Rectangular coordinates into an array of single sized rectangular bars
     * @param rectNodeArray the rectangular coordinates in array format
     * @return
     */
    public int[] dataParser(RectNode[] rectNodeArray) {
        LList tempList = new LList();
        for (int i = 1; i < rectNodeArray.length - 1; i++) {
            if (rectNodeArray[i].y == rectNodeArray[i + 1].y) {
                int differenceX = rectNodeArray[i + 1].x - rectNodeArray[i].x;
                for (int j = 0; j < differenceX; j++) {
                    tempList.add(rectNodeArray[i].y);
                }
            }
        }
        int[] returnArray = tempList.toArray();
        return returnArray;
    }

    /**
     * Returns the maxArea in linear time
     * @param hist the rectangular bars
     * @param n size of the rectangular bar array
     * @return
     */

    public int getMaxArea(int hist[], int n) {
        Stck s = new Stck(n);

        int max_area = 0;
        int tp;
        int area_with_top;

        int i = 0;
        while (i < n) {

            if (s.isEmpty() || hist[s.peek()] <= hist[i]) {
                s.push(i++);
            } else {
                tp = s.peek();
                s.pop();
                area_with_top = hist[tp] * (s.isEmpty() ? i :
                        i - s.peek() - 1);
                if (max_area < area_with_top) {
                    max_area = area_with_top;
                }
            }
        }
        while (s.isEmpty() == false) {
            tp = s.peek();
            s.pop();
            area_with_top = hist[tp] * (s.isEmpty() ? i :
                    i - s.peek() - 1);

            if (max_area < area_with_top) {
                max_area = area_with_top;
            }
        }

        return max_area;
    }
}

/**
 * Helper class for forming the stack, backed by an array
 */

class Stck {
    int[] arr;
    int top, capacity;

    /**
     * Constructor for Stack
     * @param size size of expected Stack
     */
    public Stck(int size) {
        arr = new int[size];
        capacity = size;
        this.top = -1;
    }

    /**
     * Pushes element on Stack
     * @param indexToPush index to push
     */

    public void push(int indexToPush) {
        if (isFull()) {
            System.out.println("overFlow");
            System.exit(1);
        }
        arr[++top] = indexToPush;
    }

    /**
     * Pops element from stack
     * @return popped index
     */

    public int pop() {
        if (isEmpty()) {
            System.out.println("underFlow");
        }
        return arr[top--];
    }

    /**
     * Peeks at the top element
     * @return value of the element at top
     */
    public int peek() {
        if (!isEmpty()) {
            return arr[top];
        } else {
            System.exit(1);
        }
        return -1;
    }

    /**
     * Checks if stack is empty
     * @return true if stack is empty
     */

    public Boolean isEmpty() {
        return top == -1;
    }

    /**
     * Checks if stack is Full
     * @return true if stack is full
     */

    public Boolean isFull() {
        return top == capacity - 1;
    }

}

/**
 * Helper class for storing rectangular coordinates
 */
class RectNode {
    int x, y;

    /**
     *Constructor
     * @param x x coordinate
     * @param y y coordinate
     */
    public RectNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "X: " + this.x + " ,Y: " + this.y;
    }
}

/**
 * Helper class to create linkedList
 */
class LList {
    node head;
    int size = 0;

    /**
     * adds element to linkedlist
     * @param height height of bar to be added
     */
    public void add(int height) {
        node newNode = new node(height);
        if (size == 0) {
            head = newNode;
        } else {
            node temp = head;
            newNode.next = temp;
            head = newNode;
        }
        size++;
    }

    /**
     * Converts linked list to an array
     * @return array containing all elements of linkedlist
     */
    public int[] toArray() {
        int[] toArray = new int[size];
        int i = size - 1;
        while (head != null) {
            toArray[i] = head.height;
            head = head.next;
            i--;
        }
        return toArray;
    }
}

/**
 * Helper class for linkedList
 */
class node {
    node next;
    int height;

    public node(int height) {
        this.height = height;
        this.next = null;
    }

}
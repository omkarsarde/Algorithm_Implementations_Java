import java.util.Scanner;

//The planter problem solutions

public class Planters {

    public static void main(String[] args) throws Exception {

        //taking number of p and r planters from user

        Scanner scanner = new Scanner(System.in);
        String[] sizeOfHeaps = scanner.nextLine().split(" ");
        String[] strOne = scanner.nextLine().split(" ");
        String[] strTwo = scanner.nextLine().split(" ");
        Heap heapOne = new Heap(Integer.parseInt(sizeOfHeaps[0]));
        Heap heapTwo = new Heap(Integer.parseInt(sizeOfHeaps[1]));

        //storing both the arrays in heaps
        for (int i = 0; i < strOne.length; i++) {
            heapOne.insert(Integer.parseInt(strOne[i]));
        }
        for (int i = 0; i < strTwo.length; i++) {
            heapTwo.insert(Integer.parseInt(strTwo[i]));
        }
        //scanner.close();

        // this condition makes the transfer valid when
        // planters in p are not empty and there is
        // at least one planter in r which is greater
        // than the planters in p

        while (heapOne.isEmpty() != true && heapTwo.peek() > heapOne.peek()) {
            {
                // transfer process from planters p to r
                int transfer = heapOne.peek();
                heapOne.delete(0);
                heapTwo.delete(0);
                heapTwo.insert(transfer);
            }
        }

        if (heapOne.isEmpty()) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

    }

}

class Heap {

    int[] heap;
    private int size;

    public Heap(int capacity) {
        heap = new int[capacity];
    }

    /**
     * Insert a new value from array in respective heap
     */
    public void insert(int value) {
        if (isFull()) {
            throw new IndexOutOfBoundsException("Heap is full");
        }

        heap[size] = value;

        heapifyUp(size);
        size++;
    }

    /**
     * Peek function of heap
     * @return heap at 1st position
     */
    public int peek() {
//        if (isEmpty()) {
//            throw new IndexOutOfBoundsException("Heap is empty");
//        }

        return heap[0];
    }

    /**
     * Delete the element at given index from heap
     *
     * @param index element at given index
     * @return
     */
    public int delete(int index) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }

        int parent = getParent(index);
        int deletedValue = heap[index];

        heap[index] = heap[size - 1];

        if (index == 0 || heap[index] < heap[parent]) {
            heapifyDown(index, size - 1);
        } else {
            heapifyUp(index);
        }

        size--;

        return deletedValue;

    }

    /**
     * Function for adjusting the heap
     * after insertion
     *
     * @param index index at which element is
     */
    private void heapifyUp(int index) {
        int newValue = heap[index];
        while (index > 0 && newValue > heap[getParent(index)]) {
            heap[index] = heap[getParent(index)];
            index = getParent(index);
        }

        heap[index] = newValue;
    }

    /**
     * Function for adjusting the heap
     * after deletion
     *
     * @param index         index at which element is
     * @param lastHeapIndex
     */
    private void heapifyDown(int index, int lastHeapIndex) {
        int childToSwap;

        while (index <= lastHeapIndex) {
            int leftChild = getChild(index, true);
            int rightChild = getChild(index, false);
            if (leftChild <= lastHeapIndex) {
                if (rightChild > lastHeapIndex) {
                    childToSwap = leftChild;
                } else {
                    childToSwap = (heap[leftChild] > heap[rightChild] ? leftChild : rightChild);
                }

                if (heap[index] < heap[childToSwap]) {
                    int tmp = heap[index];
                    heap[index] = heap[childToSwap];
                    heap[childToSwap] = tmp;
                } else {
                    break;
                }

                index = childToSwap;
            } else {
                break;
            }
        }
    }

    public boolean isFull() {
        return size == heap.length;
    }

    public int getParent(int index) {
        return (index - 1) / 2;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getChild(int index, boolean left) {
        return 2 * index + (left ? 1 : 2);
    }

}
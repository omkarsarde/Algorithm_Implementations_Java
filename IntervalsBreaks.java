import java.util.Scanner;



/**
 * Main class
 */
public class IntervalsBreaks {
    intervals[] input;
    int[][] grid;

    /**
     * Constructor, sorts the input intervals on basis of earliest finish time
     *
     * @param input all intervals from input in intervals form
     * @param grid  the cost of travelling form one course to another in 2D matrix
     */
    public IntervalsBreaks(intervals[] input, int[][] grid) {

        this.input = input;
        this.grid = grid;
        sort(input, 0, input.length - 1);
    }

    /**
     * Checks the compatibility of current course under consideration with all other courses and returns the count
     *
     * @param k index of current course under consideration
     * @return count of courses current course is compatible with
     */
    public int check_courses(int k) {
        intervals current = input[k];
        int count = 1;
        for (int i = 1; i < input.length; i++) {
            // check if the start of next course is greater than end of current course
            // and if the cost of travelling is less than the difference between start of next and
            // end of current course, if yes next course becomes current course
            if (input[i].start > current.end && grid[current.placeInGrid][input[i].placeInGrid] <=
                    (input[i].start - current.end)) {
                count++;
                current = input[i];
            }
        }
        return count;
    }

    /**
     * Generic merge for merge sort, sorts based on end of intervals
     *
     * @param arr input array
     * @param l   lower limit
     * @param m   middle
     * @param r   right most
     */
    void merge(intervals arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        intervals L[] = new intervals[n1];
        intervals R[] = new intervals[n2];
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
     * Driver for merge sort
     *
     * @param arr input array
     * @param l   lower limit on index
     * @param r   max limit on index
     */
    void sort(intervals arr[], int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    /**
     * Driver function, parses input, stores them in interval array
     * and creates a 2D matrix to store the time required for travelling
     * then calls checkCourses function to decide maximum compatible courses
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfIntervals = scanner.nextInt();
        intervals[] interval_given = new intervals[numberOfIntervals];
        int z = 0;
        //populate array of given intervals
        while (z < interval_given.length) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            interval_given[z] = new intervals(start, end, z);
            z++;
        }
        //populate the 2D grid with the time required for travelling from one course
        //to other
        int[][] cost_grid = new int[numberOfIntervals][numberOfIntervals];
        for (int m = 0; m < numberOfIntervals; m++) {
            for (int n = 0; n < numberOfIntervals; n++) {
                int cost = scanner.nextInt();
                cost_grid[m][n] = cost;
            }
        }
        scanner.close();
        //instantiate the test class
        IntervalsBreaks test = new IntervalsBreaks(interval_given, cost_grid);
        //variables to store the max courses possible and
        //courses compatible with current course
        int maximum = 0;
        int current = 0;
        for (int i = 0; i < interval_given.length; i++) {
            current = test.check_courses(i);
            if (current >= maximum) {
                maximum = current;
            }
        }
        System.out.println(maximum);
    }
}

/**
 * helper class to store the intervals with their start, end
 * and index in the 2D grid
 */
class intervals {
    int start, end, placeInGrid;

    public intervals(int start, int end, int placeInGrid) {
        this.start = start;
        this.end = end;
        this.placeInGrid = placeInGrid;
    }

    public String toString() {
        return "Start: " + start + " End: " + end + " place in grid: " + placeInGrid;
    }
}

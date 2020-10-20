import java.util.Scanner;


/**
 * This program computes longest increasing sub-sequence
 * using recursion.
 */

public class LongestIncreasingSubseqRecursive {

    /*
    Driver function
     */

    public static void main(String[] args) throws Exception {

        LongestIncreasingSubseqRecursive recursive = new LongestIncreasingSubseqRecursive();
        Scanner scanner = new Scanner(System.in);
        int numberOfNumbers = scanner.nextInt();
        int[] numbers = new int[numberOfNumbers];
        int i = 0;
        while (scanner.hasNext()) {
            numbers[i] = scanner.nextInt();
            i++;
        }
        long startTime = System.currentTimeMillis();
        // method call
        long length = recursive.longestIncrSubseq(numbers);
        System.out.println(length);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("for n = " + numbers.length);
        System.out.println(totalTime);
    }

    /**
     * This method computes results of recursive function
     * and display the max length of longest sub-sequence
     * @param arr input array
     * @return length of maximum increasing sequence
     */
    private int longestIncrSubseq(int[] arr) {
        int max = 1;
        for (int i = 0; i < arr.length; i++) {
            int maxSum = longestIncrSubseqRecursive(arr, i);
            if (maxSum > max) {
                max = maxSum;
            }
        }
        return max;
    }

    /**
     * Computes sub-sequence recursively
     * @param arr input array
     * @param n length of input array
     * @return
     */
    private static int longestIncrSubseqRecursive(int arr[], int n) {
        if (n == 0) {
            return 1;
        }
        int max = 1;
        int maxSum;
        for (int i = 0; i < n; i++) {
            if (arr[i] < arr[n]) {
                maxSum = 1 + longestIncrSubseqRecursive(arr, i);
                if (maxSum > max) {
                    max = maxSum;
                }
            }
        }
        return max;
    }

}

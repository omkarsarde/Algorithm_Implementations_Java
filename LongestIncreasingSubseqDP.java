import java.util.Scanner;

/**
 * This program computes longest increasing sub-sequence
 * using dynamic programming in O(n^2) running time.
 */

public class LongestIncreasingSubseqDP {

    /**
     * Driver function
     * @param args
     */
    public static void main(String[] args) {
        LongestIncreasingSubseqDP dp = new LongestIncreasingSubseqDP();
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
        int length = dp.longestIncrSubseq(numbers);
        System.out.println(length);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("for n = " + numbers.length);
        System.out.println(totalTime);

    }

    /**
     * Method to find longest increasing sub-sequence
     *
     * @param arr array of numbers
     * @return
     */
    private int longestIncrSubseq(int arr[]) {
        if (arr.length == 0) {
            return 0;
        }
        int[] maxSum = new int[arr.length];
        maxSum[0] = 1;
        int max = 1;
        for (int j = 1; j < arr.length; j++) {
            maxSum[j] = 1;
            for (int k = 0; k < j; k++) {
                if (arr[k] < arr[j] && maxSum[j] < maxSum[k] + 1) {
                    maxSum[j] = maxSum[k] + 1;
                }
            }
            max = (maxSum[j]) > max ? maxSum[j] : max;
        }
        return max;
    }
}

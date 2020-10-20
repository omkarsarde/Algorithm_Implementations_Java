import java.util.Scanner;

/**
 * This program computes longest convex sub-sequence
 * using dynamic programming in O(n^3) running time.
 */


public class LongestConvexSubseq {
    /**
     * Driver Function
     */
    public static void main(String[] args){
        LongestConvexSubseq convexSubseq = new LongestConvexSubseq();
        Scanner scanner = new Scanner(System.in);
        int numberOfNumbers = scanner.nextInt();
        int[] numbers = new int[numberOfNumbers];
        int i = 0;
        while (scanner.hasNext()) {
            numbers[i] = scanner.nextInt();
            i++;
        }
        // method call
        int length = convexSubseq.longestConvexSubseq(numbers);
        System.out.println(length);
    }


    /**
     * Method to compute longest convex subsequence
     * where (arr[i-1] + arr[i+1])/2 > arr[i]
     *
     * @param arr input array
     * @return max length of sequence
     */
    private int longestConvexSubseq(int[] arr) {
        if (arr.length <= 2) {
            //
            return arr.length;
        }
        int maxLength = 0;
        int[][] maxSum = new int[arr.length][arr.length];
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                maxSum[i][j] = 2;
                for (int k = 0; k < j; k++) {
                    if (arr[j] < (arr[i] + arr[k])/2){
                        // if satisfies convex condition
                        maxSum[i][j] = Math.max(maxSum[i][j], maxSum[j][k] + 1);
                    }
                }
                maxLength = Math.max(maxLength, maxSum[i][j]);
            }
        }

        return maxLength;
    }
}

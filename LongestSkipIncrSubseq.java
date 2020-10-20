import java.util.Scanner;


/**
 * This program computes longest increasing sub-sequence
 * using dynamic programming in O(n^2) running time.
 */

public class LongestSkipIncrSubseq {

    /**
     * Driver function
     *
     * @param args
     */
    public static void main(String[] args){
        LongestSkipIncrSubseq dp = new LongestSkipIncrSubseq();
        Scanner scanner = new Scanner(System.in);
        int numberOfNumbers = scanner.nextInt();
        int[] numbers = new int[numberOfNumbers];
        int i = 0;
        while (scanner.hasNext()) {
            numbers[i] = scanner.nextInt();
            i++;
        }
        scanner.close();
        int length = dp.longestIncrSubseq(numbers);
        System.out.println(length);

    }

    /**
     * Method to find longest increasing sub-sequence
     *
     * @param arr array of numbers
     * @return max length of increasing sub-sequence with given condition
     */
    private int longestIncrSubseq(int arr[]) {
        if (arr.length == 0) {
            return 0;
        }
        int[] maxSum = new int[arr.length];
        maxSum[0] = 1;
        int max = 1;
        int counter;
        for (int j = 1; j < arr.length; j++) {
            maxSum[j] = 1;
            counter=0;
            for (int k = 0; k < j; k++) {
                if (arr[k] < arr[j] && maxSum[j] < maxSum[k] + 1) {
                    if(k==j-2 && j-2>=0 && arr[j-2]<arr[j-1] && arr[j-1]<arr[j]) {
                        counter++;
                    }
                    if (counter == 1 && k-1>=0) {
                        maxSum[j] = maxSum[k-1]+1;
                    }else {
                        maxSum[j] = maxSum[k]+1;
                    }
                }
            }
            if(max < maxSum[j]){
                max = maxSum[j];
            }
        }
        return max;
    }
}
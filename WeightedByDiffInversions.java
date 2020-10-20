

import java.util.Scanner;

/**
 * This program takes a input number n and an array of n numbers
 * and displays the sum of differences of the inversions occurred
 */
public class WeightedByDiffInversions {

    // for storing the sum of inversions
    private static long inversions = 0L;

    /**
     * Driver function
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        int i = 0;
        int[] numbers = new int[input];
        while (scanner.hasNext()) {
            numbers[i] = scanner.nextInt();
            i++;
        }
        scanner.close();

        int[] tempArray = new int[numbers.length];
        int inversionCount = countInversions(numbers, tempArray, 0, numbers.length - 1);
        //    System.out.println(inversionCount);
        //    Prints the sum of the differences of all inversions
        System.out.println(inversions);
    }

    /**
     * This method divides the array in two parts
     * and combines all the results of inversions
     *
     * @param inputArray input Array
     * @param newArray   sorted array
     * @param leftIndex  index of left sub-array
     * @param rightIndex index of right sub-array
     * @return
     */
    private static int countInversions(int[] inputArray, int[] newArray, int leftIndex, int rightIndex) {

        int inversionCount = 0;
        if (leftIndex < rightIndex) {
            int mid = (leftIndex + rightIndex) / 2;
            inversionCount += countInversions(inputArray, newArray, leftIndex, mid);
            inversionCount += countInversions(inputArray, newArray, mid + 1, rightIndex);
            inversionCount += countThroughMiddle(inputArray, newArray, leftIndex, mid + 1, rightIndex);
        }
        return inversionCount;
    }

    /**
     * This method counts the inversions through the center
     *
     * @param array      the input array
     * @param newArray   new sorted array that we will obtain
     * @param leftIndex  index of left sub-array
     * @param rightIndex index of right sub-array
     * @param rightEnd   last index of right sub-array
     * @return inversion count
     */
    private static int countThroughMiddle(int[] array, int[] newArray, int leftIndex, int rightIndex, int rightEnd) {
        int invCount = 0;
        int middle = rightIndex;
        int leftEnd = rightIndex - 1;
        int newIndex = leftIndex;
        //  numbers of elements in array
        int elements = rightEnd - leftIndex + 1;

        while (leftIndex <= leftEnd && rightIndex <= rightEnd) {
            if (array[leftIndex] <= array[rightIndex]) {
                newArray[newIndex++] = array[leftIndex++];
            } else {
                newArray[newIndex++] = array[rightIndex++];
                //  counting the inversions in variable invCount
                invCount += middle - leftIndex;
                for (int i = leftIndex; i <= leftEnd; i++) {
                    /* as the element in right sub-array will be in
                    inversion with all the elements of left sub-array
                    from index i
                     */
                    inversions += (array[i] - array[rightIndex - 1]);
                }
            }
        }
        while (leftIndex <= leftEnd) {
            newArray[newIndex++] = array[leftIndex++];
        }
        while (rightIndex <= rightEnd) {
            newArray[newIndex++] = array[rightIndex++];
        }
        //  adding back elements to the new sorted array
        for (int i = 0; i < elements; i++, rightEnd--) {
            array[rightEnd] = newArray[rightEnd];
        }

        return invCount;
    }
}


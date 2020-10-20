import java.util.Scanner;


/**
 * This program finds optimal parenthesizing and
 * prints the optimal parenthesizing
 */
public class MatrixChainParenthesize {
    /**
     * Driver Function
     * @param args
     * @throws Exception
     */
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] input = new int[n + 1];
        int i = 0;
        while (scanner.hasNext()) {
            input[i] = scanner.nextInt();
            i++;
        }

        MatrixChainParenthesize t = new MatrixChainParenthesize();

        //method calls
        int[][] resolve = t.solve(input);
        t.printTheResolve(resolve, 1, input.length - 1);
    }

    /**
     * This method finds the minimum cost of
     * multiplying the matrices
     * @param input
     * @return
     */
    public int[][] solve(int[] input) {
        int n = input.length;
        int[][] solutionMatrix = new int[n][n];
        int[][] resolve = new int[n][n];
        for (int d = 1; d < n; d++) {
            for (int l = 1; l < (n - d); l++) {
                int r = l + d;
                solutionMatrix[l][r] = Integer.MAX_VALUE;
                for (int k = l; k < r; k++) {
                    int temp = solutionMatrix[l][k] + solutionMatrix[k + 1][r] + input[l - 1] * input[k] * input[r];
                    if (temp < solutionMatrix[l][r]) {
                        solutionMatrix[l][r] = temp;
                        resolve[l][r] = k;
                    }
                }
            }
        }
        System.out.println(solutionMatrix[1][n - 1]);
        return resolve;
    }

    /**
     * This method prints the optimal parenthesizing
     * of matrices
     * @param resolve matrix
     * @param i
     * @param j
     */
    public void printTheResolve(int[][] resolve, int i, int j) {
        if (i == j) {
            System.out.print(" A" + i + " ");
        } else {
            System.out.print("( ");
            printTheResolve(resolve, i, resolve[i][j]);
            System.out.print(" x ");
            printTheResolve(resolve, resolve[i][j] + 1, j);
            System.out.print(" ) ");
        }
    }

}
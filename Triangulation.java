import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;



/**
 * This program finds the minimum possible length of a triangulation
 * of a polygon
 */
class Triangulation {

    /**
     * Driver function
     * @param args
     */
    public static void main(String[] args) {
        Triangulation t = new Triangulation();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        double[] x = new double[n];
        double[] y = new double[n];
        int i = 0;
        while (scanner.hasNext()) {
            x[i] = scanner.nextDouble();
            y[i] = scanner.nextDouble();
            i++;
        }
        scanner.close();
        t.solve(x, y);
    }

    /**
     * This method finds the euclidean distance
     * between two given points
     * @param i vertices
     * @param j vertices
     * @param x array of x coordinates
     * @param y array of y coordinates
     * @return
     */
    public double euclideanDist(int i, int j, double[] x, double[] y) {
        double temp = 0;
        if (i == 0 && j == x.length - 1) {
            return temp;
        } else if (i == j - 1) {
            return temp;
        } else {
            return Math.sqrt(((x[i] - x[j]) * (x[i] - x[j])) + ((y[i] - y[j]) * (y[i] - y[j])));
        }
    }

    /**
     * Method similar to matrix chain multiplication
     * only difference is the cost
     * @param x array of x coordinates
     * @param y array of y coordinates
     */
    public void solve(double[] x, double[] y) {
        int n = x.length;
        double[][] solutionMatrix = new double[n][n];
        double[][] resolve = new double[n][n];
        for (int d = 1; d < n; d++) {
            for (int l = 1; l < (n - d); l++) {
                int r = l + d;
                solutionMatrix[l][r] = Integer.MAX_VALUE;
                for (int k = l; k < r; k++) {
                    //just change the cost in matrixCode;
                    double length = euclideanDist(l - 1, r, x, y);
                    double temp = (solutionMatrix[l][k] + solutionMatrix[k + 1][r] + length);
//                   System.out.println("l:" + l+ " k: "+k+" r: "+r+" length: "+length+ " temp: "+ temp);
                    if (temp < solutionMatrix[l][r]) {
                        solutionMatrix[l][r] = temp;
                        resolve[l][r] = k;
                    }
                }
            }
        }
        //decimal stuff
        double answer = solutionMatrix[1][n-1];
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        System.out.println(df.format(answer));
    }
}
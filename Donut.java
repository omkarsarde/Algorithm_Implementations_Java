import java.util.Scanner;


/**
 * This program calculates minimum distnces from the
 * Xbest and Ybest coordinates and displays their sum
 * of all the distances
 */
public class Donut {
    Coordinates[] input;
    Coordinates mean;

    /**
     * Default constructor
     */
    public Donut() {

    }

    /**
     * This method calculates the sum of the
     * minimum distances from best coordinates
     * @return
     */
    int distancesum() {
        int sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += (Math.abs(mean.x - input[i].x) +
                    Math.abs(mean.y - input[i].y));
        }
        return sum;

    }

    /**
     * This method takes the x and y coordinates
     * and also calculates the mean of all values
     */
    public void inputParser() {
        mean = new Coordinates(0, 0);
        Scanner scanner = new Scanner(System.in);
        int numPoints = scanner.nextInt();
        input = new Coordinates[numPoints];
        int counter = 0;
        while (scanner.hasNext()) {
            int x = scanner.nextInt();
            mean.x += x;
            int y = scanner.nextInt();
            mean.y += y;
            Coordinates newCoordinate = new Coordinates(x, y);
            input[counter++] = newCoordinate;
        }
        mean.x = mean.x / input.length;
        mean.y = mean.y / input.length;
    }

    /**
     * Driver Function
     * @param args
     */
    public static void main(String[] args) {
        Donut test = new Donut();
        test.inputParser();
        int a = test.distancesum();
        System.out.println(a);
    }
}

/**
 * Class for coordinates of the points
 */
class Coordinates {
    int x, y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public String toString() {
        return "X: " + x + " Y: " + y;
    }
}
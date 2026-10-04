import java.util.*;

public class PinAssignmentOptimization {

    static int n = 6;

    // Physical pin coordinates
    static int[][] pin = {
        {0, 0}, {0, 1}, {0, 2},
        {1, 0}, {1, 1}, {1, 2}
    };

    // Connection weights
    static int[][] weight = {
        {0, 5, 2, 4, 1, 3},
        {5, 0, 4, 2, 5, 1},
        {2, 4, 0, 3, 2, 4},
        {4, 2, 3, 0, 5, 2},
        {1, 5, 2, 5, 0, 3},
        {3, 1, 4, 2, 3, 0}
    };

    static int calculateCost(int[] assignment) {
        int cost = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                int p1 = assignment[i];
                int p2 = assignment[j];

                int distance =
                    Math.abs(pin[p1][0] - pin[p2][0]) +
                    Math.abs(pin[p1][1] - pin[p2][1]);

                cost += weight[i][j] * distance;
            }
        }

        return cost;
    }

    static void run(int seed) {

        Random random = new Random(seed);

        int[] current = {0, 1, 2, 3, 4, 5};

        // Generate random initial assignment
        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            int temp = current[i];
            current[i] = current[j];
            current[j] = temp;
        }

        int currentCost = calculateCost(current);

        int[] best = current.clone();
        int bestCost = currentCost;

        // Randomized local search
        for (int iter = 0; iter < 10000; iter++) {

            int a = random.nextInt(n);
            int b = random.nextInt(n);

            if (a == b)
                continue;

            // Swap two assigned pins
            int temp = current[a];
            current[a] = current[b];
            current[b] = temp;

            int newCost = calculateCost(current);

            if (newCost <= currentCost) {
                currentCost = newCost;

                if (newCost < bestCost) {
                    bestCost = newCost;
                    best = current.clone();
                }
            } else {
                // Undo the swap
                temp = current[a];
                current[a] = current[b];
                current[b] = temp;
            }
        }

        System.out.println("Seed: " + seed);
        System.out.println("Best Cost: " + bestCost);

        System.out.print("Best Assignment: ");

        for (int i = 0; i < n; i++) {
            System.out.print("S" + (i + 1) +
                    "->P" + (best[i] + 1) + " ");
        }

        System.out.println("\n");
    }

    public static void main(String[] args) {

        run(10);
        run(20);
        run(30);
        run(40);
        run(50);
    }
}
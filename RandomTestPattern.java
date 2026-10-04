import java.util.*;
public class RandomTestPattern {
    static int circuit(int[] x) {
        int A = x[0];
        int B = x[1];
        int C = x[2];
        int D = x[3];
        return (A & B) | (C & D);
    }
    static boolean detectsFault(int[] x, int input, int stuckValue) {
        int goodOutput = circuit(x);
        int[] faulty = x.clone();
        faulty[input] = stuckValue;
        int faultyOutput = circuit(faulty);
        return goodOutput != faultyOutput;
    }
    static double run(boolean biased, int seed) {
        Random random = new Random(seed);
        boolean[][] detected = new boolean[4][2];
        int numberOfVectors = 20;
        for (int v = 0; v < numberOfVectors; v++) {
            int[] x = new int[4];
            for (int i = 0; i < 4; i++) {
                if (biased)
                    x[i] = random.nextDouble() < 0.8 ? 1 : 0;
                else
                    x[i] = random.nextInt(2);
            }
            for (int i = 0; i < 4; i++) {
                for (int stuck = 0; stuck <= 1; stuck++) {
                    if (detectsFault(x, i, stuck))
                        detected[i][stuck] = true;
                }
            }
        }
        int detectedFaults = 0;
        for (int i = 0; i < 4; i++) {
            for (int stuck = 0; stuck <= 1; stuck++) {
                if (detected[i][stuck])
                    detectedFaults++;
            }
        }
        return (detectedFaults / 8.0) * 100;
    }
    public static void main(String[] args) {
        int[] seeds = {10, 20, 30, 40, 50};
        System.out.println("Purely Random Testing");
        for (int seed : seeds) {
            System.out.println(
                "Seed: " + seed +
                ", Coverage: " + run(false, seed) + "%"
            );
        }
        System.out.println("\nBiased-Random Testing");
        for (int seed : seeds) {
            System.out.println(
                "Seed: " + seed +
                ", Coverage: " + run(true, seed) + "%"
            );
        }
    }
}

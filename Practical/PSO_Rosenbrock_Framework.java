import java.util.Random;

public class PSO_Rosenbrock_Framework {
    static int N = 8;             // Number of particles
    static int dimensions = 2;    // Problem dimensions
    static int T = 100;           // Max iterations
    static double w = 0.75;       // Inertia weight
    static double c1 = 1.5;       // Cognitive coefficient
    static double c2 = 2.0;       // Social coefficient
    static double minBound = -5, maxBound = 5;

    static Random rand = new Random();

    // Rosenbrock function (objective)
    public static double rosenbrock(double[] x) {
        double x1 = x[0], x2 = x[1];
        return 100 * Math.pow((x2 - x1 * x1), 2) + Math.pow((1 - x1), 2);
    }

    public static void main(String[] args) {

        double[][] x = new double[N][dimensions];      // Positions
        double[][] v = new double[N][dimensions];      // Velocities
        double[][] pbest = new double[N][dimensions];  // Personal best positions
        double[] pbestScore = new double[N];           // Personal best scores
        double[] gbest = new double[dimensions];       // Global best position
        double gbestScore = Double.MAX_VALUE;          // Global best score

        // Initialize positions, velocities, and pbest
        for (int i = 0; i < N; i++) {
            for (int d = 0; d < dimensions; d++) {
                x[i][d] = minBound + (maxBound - minBound) * rand.nextDouble();
                v[i][d] = -1 + 2 * rand.nextDouble();
                pbest[i][d] = x[i][d];
            }
            pbestScore[i] = rosenbrock(x[i]);
            if (pbestScore[i] < gbestScore) {
                gbestScore = pbestScore[i];
                gbest = pbest[i].clone();
            }
        }

        System.out.println("========== PSO on Rosenbrock Function ==========");
        System.out.println("Iteration\tBest Fitness\t\tGlobal Best (x1, x2)");
        System.out.println("------------------------------------------------");

        int t = 1;
        while (t <= T) {

            // Update pbest and gbest
            for (int i = 0; i < N; i++) {
                double fitness = rosenbrock(x[i]);
                if (fitness < pbestScore[i]) {
                    pbestScore[i] = fitness;
                    pbest[i] = x[i].clone();
                }
                if (fitness < gbestScore) {
                    gbestScore = fitness;
                    gbest = x[i].clone();
                }
            }

            // Print progress every 10 iterations
            if (t % 10 == 0 || t == 1 || t == T) {
                System.out.printf("%-10d\t%-18.6f(%.4f, %.4f)%n", t, gbestScore, gbest[0], gbest[1]);
            }

            // Update velocity and position
            for (int i = 0; i < N; i++) {
                for (int d = 0; d < dimensions; d++) {
                    double r1 = rand.nextDouble();
                    double r2 = rand.nextDouble();

                    v[i][d] = w * v[i][d]
                            + c1 * r1 * (pbest[i][d] - x[i][d])
                            + c2 * r2 * (gbest[d] - x[i][d]);

                    x[i][d] += v[i][d];

                    // Bound check
                    if (x[i][d] < minBound) x[i][d] = minBound;
                    if (x[i][d] > maxBound) x[i][d] = maxBound;
                }
            }

            t++;
        }

        System.out.println("\n========== Final Optimal Solution ==========");
        System.out.printf("x1 = %.6f, x2 = %.6f%n", gbest[0], gbest[1]);
        System.out.printf("f(x) = %.6f%n", gbestScore);
        System.out.println("============================================");
    }
}

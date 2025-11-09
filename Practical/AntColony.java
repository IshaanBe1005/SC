import java.util.*;

public class AntColony {
    static int N = 4; // number of nodes
    static Random rand = new Random();
    static double[][] cost = { //cost matrix
        {0, 5, 15, 4},
        {5, 0, 4, 8},
        {15, 4, 0, 1},
        {4, 8, 1, 0}
    };
    static double[][] pheromone = new double[N][N];
    static double evaporation = 1.0; //no evaporation
    static double alpha = 1.0;
    static double beta = 2.0;
    static int source = 0;      //C01 is source
    static int destination = 2;  //C02 is destination

    static double calculatePathCost(List<Integer> path) {
        double total = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            total += cost[path.get(i)][path.get(i + 1)];
        }
        return total;
    }

    static void generateInitialPheromone() {
        //Displaying paths of ants
        System.out.println("=====================================");
        System.out.println("Step 1: Fixed Paths for First 3 Ants");
        System.out.println("=====================================");
        System.out.println("Source: C" + source + " | Destination: C" + destination + "\n");

        List<Integer> ant1 = Arrays.asList(0, 1, 2, 3, 0); // A → B → C → D → A
        List<Integer> ant2 = Arrays.asList(0, 2, 1, 3, 0); // A → C → B → D → A
        List<Integer> ant3 = Arrays.asList(0, 1, 3, 2, 0); // A → B → D → C → A

        List<List<Integer>> ants = Arrays.asList(ant1, ant2, ant3);

        for (int k = 0; k < ants.size(); k++) {
            
            List<Integer> path = ants.get(k);
            double pathCost = calculatePathCost(path);
            System.out.printf("Ant %d Path: %s | Cost = %.2f%n", k + 1, path, pathCost);

            for (int i = 0; i < path.size() - 1; i++) {
                int u = path.get(i);
                int v = path.get(i + 1);
                double delta = 1.0 / pathCost;
                pheromone[u][v] += delta;
                pheromone[v][u] += delta;
            }
        }

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                pheromone[i][j] *= evaporation;

        System.out.println("\nPheromone Matrix After First 3 Ants:");
        printMatrix(pheromone);
    }

    static void simulateFourthAnt() {
        System.out.println("\n=====================================");
        System.out.println("Step 2: 4th Ant Using Pheromone Matrix");
        System.out.println("=====================================");
        System.out.println("Source: C" + source + " | Destination: C" + destination + "\n");

        boolean[] visited = new boolean[N]; //marks nodes the ant already visited (prevent revisiting).
        List<Integer> path = new ArrayList<>();
        path.add(source);
        visited[source] = true; //from source to destination, marked the visited nodes

        int current = source;

        while (current != destination) {
            int next = selectNextNode(current, visited, destination);
            path.add(next);
            visited[next] = true;
            current = next;
        }

        path.add(source);

        double totalCost = calculatePathCost(path);
        System.out.println("\n4th Ant Path: " + path);
        System.out.printf("Total Cost: %.2f%n", totalCost);

        double delta = 1.0 / totalCost;
        for (int i = 0; i < path.size() - 1; i++) {
            int u = path.get(i);
            int v = path.get(i + 1);
            pheromone[u][v] += delta;
            pheromone[v][u] += delta;
        }

        System.out.println("\nUpdated Pheromone Matrix After 4th Ant:");
        printMatrix(pheromone);
    }

    static int selectNextNode(int current, boolean[] visited, int destination) {
        double[] prob = new double[N];
        double sum = 0;

        for (int j = 0; j < N; j++) {
            if (!visited[j] && j != current) {
                prob[j] = Math.pow(pheromone[current][j], alpha) * Math.pow(1.0 / cost[current][j], beta);
                sum += prob[j];
            } else {
                prob[j] = 0;
            }
        }

        for (int j = 0; j < N; j++) {
            prob[j] = (sum > 0) ? prob[j] / sum : 0;
        }

        System.out.printf("Probabilities from C%d:%n", current);
        for (int j = 0; j < N; j++) {
            if (!visited[j] && j != current)
                System.out.printf("  To C%d: %.4f%n", j, prob[j]);
        }

        double r = rand.nextDouble();
        double cumulative = 0;
        for (int j = 0; j < N; j++) {
            cumulative += prob[j];
            if (r <= cumulative && !visited[j] && j != current) {
                System.out.printf("  Selected: C%d (r=%.4f)%n", j, r);
                return j;
            }
        }

        for (int j = 0; j < N; j++)
            if (!visited[j] && j != current)
                return j;

        return destination;
    }

    static void printMatrix(double[][] matrix) {
        System.out.println("    C0    C1    C2    C3");
        for (int i = 0; i < N; i++) {
            System.out.printf("C%d ", i);
            for (int j = 0; j < N; j++) {
                System.out.printf("%6.3f ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        generateInitialPheromone();
        simulateFourthAnt();
    }
}
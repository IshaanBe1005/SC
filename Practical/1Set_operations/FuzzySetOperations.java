import java.util.*;

public class FuzzySetOperations {

    // union (max)
    static double[] union(double[] setA, double[] setB) {
        double[] result = new double[setA.length];
        for (int i = 0; i < setA.length; i++)
            result[i] = Math.max(setA[i], setB[i]);
        return result;
    }

    // intersection (min)
    static double[] intersection(double[] setA, double[] setB) {
        double[] result = new double[setA.length];
        for (int i = 0; i < setA.length; i++)
            result[i] = Math.min(setA[i], setB[i]);
        return result;
    }

    // complement (1 - value)
    static double[] complement(double[] setA) {
        double[] result = new double[setA.length];
        for (int i = 0; i < setA.length; i++)
            result[i] = 1 - setA[i];
        return result;
    }

    static void display(String name, double[] set, int[] elements) {
        System.out.println("\n" + name + ":");
        for (int i = 0; i < set.length; i++)
            System.out.println("Element " + elements[i] + " → Membership = " + set[i]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();

        int[] elementsA = new int[n];
        int[] elementsB = new int[n];
        double[] setA = new double[n];
        double[] setB = new double[n];

        System.out.println("\n--- Fuzzy Set A ---");
        for (int i = 0; i < n; i++) {
            System.out.print("Enter element value: ");
            elementsA[i] = sc.nextInt();
            System.out.print("Enter corresponding membership value (0–1): ");
            setA[i] = sc.nextDouble();
        }

        System.out.println("\n--- Fuzzy Set B ---");
        for (int i = 0; i < n; i++) {
            System.out.print("Enter element value: ");
            elementsB[i] = sc.nextInt();
            System.out.print("Enter corresponding membership value (0–1): ");
            setB[i] = sc.nextDouble();
        }

        // Fuzzy operations
        double[] unionSet = union(setA, setB);
        double[] intersectionSet = intersection(setA, setB);
        double[] complementSet = complement(setA);

        // Display results
        display("Fuzzy Set A", setA, elementsA);
        display("Fuzzy Set B", setB, elementsB);
        display("Union (A ∪ B)", unionSet, elementsA);
        display("Intersection (A ∩ B)", intersectionSet, elementsA);
        display("Complement of A (A')", complementSet, elementsA);

        sc.close();
    }
}

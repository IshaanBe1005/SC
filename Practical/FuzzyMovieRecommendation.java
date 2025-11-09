import java.util.*;

public class FuzzyMovieRecommendation {

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

    static void display(String name, double[] set, int[] ratings) {
        System.out.println("\n" + name + ":");
        for (int i = 0; i < set.length; i++)
            System.out.println("Rating " + ratings[i] + " → Membership = " + set[i]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rating levels to evaluate: ");
        int n = sc.nextInt();

        int[] storyRatings = new int[n];
        int[] actingRatings = new int[n];
        double[] storySet = new double[n];
        double[] actingSet = new double[n];

        System.out.println("\n--- Story Ratings (Set A) ---");
        for (int i = 0; i < n; i++) {
            System.out.print("Enter story rating (0–100): ");
            storyRatings[i] = sc.nextInt();
            System.out.print("Enter corresponding membership value (0–1): ");
            storySet[i] = sc.nextDouble();
        }

        System.out.println("\n--- Acting Ratings (Set B) ---");
        for (int i = 0; i < n; i++) {
            System.out.print("Enter acting rating (0–100): ");
            actingRatings[i] = sc.nextInt();
            System.out.print("Enter corresponding membership value (0–1): ");
            actingSet[i] = sc.nextDouble();
        }

        // Fuzzy operations
        double[] overallRecommendation = union(storySet, actingSet);  // this shows strongest features
        double[] weakestAspect = intersection(storySet, actingSet);   // this concludes Common weaknesses
        double[] storyWeakness = complement(storySet);                // Story improvement potential

        // Display results
        display("Story Fuzzy Set", storySet, storyRatings);
        display("Acting Fuzzy Set", actingSet, actingRatings);
        display("Overall Recommendation (Union)", overallRecommendation, storyRatings);
        display("Weakest Aspect (Intersection)", weakestAspect, storyRatings);
        display("Story Weakness (Complement)", storyWeakness, storyRatings);

        sc.close();
    }
}


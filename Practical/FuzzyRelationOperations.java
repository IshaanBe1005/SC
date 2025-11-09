public class FuzzyRelationOperations {
    public static void main(String[] args) {

        // Define two sets
        String[] SetA = {"Y1", "Y2", "Y3"}; // Example: Young people
        String[] SetB = {"Sports", "Study", "Rest"}; // Example: Activities

        // Predefined fuzzy relations (Young → Activities)
        double[][] R1 = {
            {0.8, 0.6, 0.5},
            {0.7, 0.9, 0.4},
            {0.5, 0.7, 0.8}
        };

        double[][] R2 = {
            {0.6, 0.4, 0.7},
            {0.8, 0.5, 0.6},
            {0.7, 0.6, 0.5}
        };

        System.out.println("Relation R1:");
        displayMatrix(R1, SetA, SetB);

        System.out.println("\nRelation R2:");
        displayMatrix(R2, SetA, SetB);

        // Fuzzy Union (max)
        double[][] union = union(R1, R2);
        System.out.println("\nUnion (R1 ∪ R2):");
        displayMatrix(union, SetA, SetB);

        // Fuzzy Intersection (min)
        double[][] intersection = intersection(R1, R2);
        System.out.println("\nIntersection (R1 ∩ R2):");
        displayMatrix(intersection, SetA, SetB);

        // Fuzzy Composition (max-min)
        double[][] composition = composition(R1, R2);
        System.out.println("\nComposition (R1 ○ R2):");
        displayMatrix(composition, SetA, SetB);
    }

    // ---------- Operations ----------
    static double[][] union(double[][] A, double[][] B) {
        double[][] result = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                result[i][j] = Math.max(A[i][j], B[i][j]);
        return result;
    }

    static double[][] intersection(double[][] A, double[][] B) {
        double[][] result = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                result[i][j] = Math.min(A[i][j], B[i][j]);
        return result;
    }

    static double[][] composition(double[][] A, double[][] B) {
        int m = A.length, n = B[0].length, k = A[0].length;
        double[][] result = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                double maxVal = 0;
                for (int x = 0; x < k; x++) {
                    double val = Math.min(A[i][x], B[x][j]);
                    maxVal = Math.max(maxVal, val);
                }
                result[i][j] = maxVal;
            }
        }
        return result;
    }

    // ---------- Display Matrix ----------
    static void displayMatrix(double[][] M, String[] rows, String[] cols) {
        System.out.print("\t");
        for (String c : cols) System.out.print(c + "\t");
        System.out.println("\n----------------------------------");
        for (int i = 0; i < M.length; i++) {
            System.out.print(rows[i] + "\t");
            for (int j = 0; j < M[0].length; j++)
                System.out.printf("%.2f\t", M[i][j]);
            System.out.println();
        }
    }
}

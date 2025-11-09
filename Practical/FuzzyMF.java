import java.util.Scanner;

public class FuzzyMF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose Membership Function Type: triangular / trapezoidal / gaussian / gbell / sigmoid");
        String type = sc.next().toLowerCase();

        double[] x = new double[11];
        for (int i = 0; i <= 10; i++) x[i] = i;

        double[] mf = new double[x.length];

        switch (type) {
            case "triangular":
                System.out.print("Enter 3 points (a b c): ");
                double a = sc.nextDouble();
                double b = sc.nextDouble();
                double c = sc.nextDouble();
                for (int i = 0; i < x.length; i++)
                    mf[i] = triangular(x[i], a, b, c);
                break;

            case "trapezoidal":
                System.out.print("Enter 4 points (a b c d): ");
                a = sc.nextDouble();
                b = sc.nextDouble();
                c = sc.nextDouble();
                double d = sc.nextDouble();
                for (int i = 0; i < x.length; i++)
                    mf[i] = trapezoidal(x[i], a, b, c, d);
                break;

            case "gaussian":
                System.out.print("Enter mean: ");
                double mean = sc.nextDouble();
                System.out.print("Enter sigma: ");
                double sigma = sc.nextDouble();
                for (int i = 0; i < x.length; i++)
                    mf[i] = gaussian(x[i], mean, sigma);
                break;

            case "gbell":
                System.out.print("Enter parameters a b c: ");
                double pa = sc.nextDouble();
                double pb = sc.nextDouble();
                double pc = sc.nextDouble();
                for (int i = 0; i < x.length; i++)
                    mf[i] = gbell(x[i], pa, pb, pc);
                break;

            case "sigmoid":
                System.out.print("Enter parameters x0 slope: ");
                double x0 = sc.nextDouble();
                double slope = sc.nextDouble();
                for (int i = 0; i < x.length; i++)
                    mf[i] = sigmoid(x[i], x0, slope);
                break;

            default:
                System.out.println("Invalid type!");
                sc.close();
                return;
        }

        System.out.println("\nX\tMembership");
        for (int i = 0; i < x.length; i++) {
            System.out.printf("%.2f\t%.3f%n", x[i], mf[i]);
        }

        sc.close();
    }

    // --- Fuzzy functions ---
    static double triangular(double x, double a, double b, double c) {
        if (x <= a || x >= c) return 0;
        else if (x == b) return 1;
        else if (x < b) return (x - a) / (b - a);
        else return (c - x) / (c - b);
    }

    static double trapezoidal(double x, double a, double b, double c, double d) {
        if (x <= a || x >= d) return 0;
        else if (x >= b && x <= c) return 1;
        else if (x > a && x < b) return (x - a) / (b - a);
        else return (d - x) / (d - c);
    }

    static double gaussian(double x, double mean, double sigma) {
        return Math.exp(-0.5 * Math.pow((x - mean) / sigma, 2));
    }

    static double gbell(double x, double a, double b, double c) {
        return 1 / (1 + Math.pow(Math.abs((x - c) / a), 2 * b));
    }

    static double sigmoid(double x, double x0, double slope) {
        return 1 / (1 + Math.exp(-slope * (x - x0)));
    }
}

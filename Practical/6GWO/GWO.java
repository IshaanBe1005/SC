import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GWO {
    static final int DIM = 2;
    static final double LO = -5.0, HI = 5.0;

    static class Wolf {
        double[] x = new double[DIM];
        double fitness;
        Wolf cloneWolf() {
            Wolf w = new Wolf();
            System.arraycopy(x, 0, w.x, 0, DIM);
            w.fitness = fitness;
            return w;
        }
    }

    static double objective(double[] p) {
        double x = p[0], y = p[1];
        return Math.pow(1 - x, 2) + 100 * Math.pow(y - x*x, 2);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Population size: ");
        String s = sc.nextLine().trim();
        int POP = s.isEmpty() ? 20 : Integer.parseInt(s);

        System.out.print("Max iterations: ");
        s = sc.nextLine().trim();
        int MAX_IT = s.isEmpty() ? 500 : Integer.parseInt(s);

        Random rnd = new Random();
        Wolf[] pack = new Wolf[POP];
        for (int i = 0; i < POP; i++) {
            pack[i] = new Wolf();
            for (int d = 0; d < DIM; d++) pack[i].x[d] = LO + (HI-LO)*rnd.nextDouble();
            pack[i].fitness = objective(pack[i].x);
        }

        Wolf alpha = new Wolf(), beta = new Wolf(), delta = new Wolf();
        alpha.fitness = beta.fitness = delta.fitness = Double.POSITIVE_INFINITY;

        // initial alpha/beta/delta
        for (Wolf w : pack) {
            if (w.fitness < alpha.fitness) { delta = beta.cloneWolf(); beta = alpha.cloneWolf(); alpha = w.cloneWolf(); }
            else if (w
            .fitness < beta.fitness) { delta = beta.cloneWolf(); beta = w.cloneWolf(); }
            else if (w.fitness < delta.fitness) { delta = w.cloneWolf(); }
        }

        int step = Math.max(1, MAX_IT/10);
        for (int t = 0; t < MAX_IT; t++) {
            double a = 2.0 * (1.0 - (double)t / MAX_IT);
            for (Wolf w : pack) {
                double[] newPos = new double[DIM];
                for (int d = 0; d < DIM; d++) {
                    double r1 = rnd.nextDouble(), r2 = rnd.nextDouble();
                    double A1 = 2*a*r1 - a, C1 = 2*r2;
                    double Dalpha = Math.abs(C1*alpha.x[d] - w.x[d]);
                    double X1 = alpha.x[d] - A1*Dalpha;

                    r1 = rnd.nextDouble(); r2 = rnd.nextDouble();
                    double A2 = 2*a*r1 - a, C2 = 2*r2;
                    double Dbeta = Math.abs(C2*beta.x[d] - w.x[d]);
                    double X2 = beta.x[d] - A2*Dbeta;

                    r1 = rnd.nextDouble(); r2 = rnd.nextDouble();
                    double A3 = 2*a*r1 - a, C3 = 2*r2;
                    double Ddelta = Math.abs(C3*delta.x[d] - w.x[d]);
                    double X3 = delta.x[d] - A3*Ddelta;

                    newPos[d] = (X1 + X2 + X3)/3.0;
                }

                for (int d = 0; d < DIM; d++) {
                    if (newPos[d] < LO) newPos[d] = LO;
                    if (newPos[d] > HI) newPos[d] = HI;
                    w.x[d] = newPos[d];
                }

                w.fitness = objective(w.x);

                if (w.fitness < alpha.fitness) { delta = beta.cloneWolf(); beta = alpha.cloneWolf(); alpha = w.cloneWolf(); }
                else if (w.fitness < beta.fitness) { delta = beta.cloneWolf(); beta = w.cloneWolf(); }
                else if (w.fitness < delta.fitness) { delta = w.cloneWolf(); }
            }

            if (t % step == 0) {
                System.out.printf("Iter %4d/%d  Alpha = %.10f at %s%n", t, MAX_IT, alpha.fitness, Arrays.toString(alpha.x));
            }
        }

        // find Omega (worst fitness in pack)
        Wolf omega = pack[0];
        for (Wolf w : pack) if (w.fitness > omega.fitness) omega = w;

        System.out.println("\n--- Final Wolves ---");
        System.out.printf("Alpha: f=%.12f x=%s%n", alpha.fitness, Arrays.toString(alpha.x));
        System.out.printf("Beta : f=%.12f x=%s%n", beta.fitness, Arrays.toString(beta.x));
        System.out.printf("Delta: f=%.12f x=%s%n", delta.fitness, Arrays.toString(delta.x));
        System.out.printf("Omega: f=%.12f x=%s%n", omega.fitness, Arrays.toString(omega.x));

        sc.close();
    }
}
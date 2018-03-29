import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final double mean;
    private final double stddev;
    private final double ciLow;
    private final double ciHigh;



    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("invalid input");
        }

        double[] each = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int count = 0;
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (perc.isOpen(row, col)) {
                    continue;
                }
                perc.open(row, col);
                count++;
            }
            each[i] = (double) count / (n * n);
        }

        this.mean = StdStats.mean(each);
        this.stddev = StdStats.stddev(each);
        this.ciLow = this.mean - (CONFIDENCE_95 * this.stddev) / Math.sqrt(trials);
        this.ciHigh = this.mean + (CONFIDENCE_95 * this.stddev) / Math.sqrt(trials);
    }

    public double mean() {
        return this.mean;

    }
    public double stddev() {
        return this.stddev;

    }
    public double confidenceLo() {
        return this.ciLow;

    }
    public double confidenceHi() {
        return this.ciHigh;
    }

    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int trial = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(size, trial);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");

    }
}

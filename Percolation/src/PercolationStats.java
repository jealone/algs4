import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by Arthur on 2018/3/29.
 */
public class PercolationStats {

    private double mean;
    private double stddev;
    private double cilow;
    private double cihigh;
    private double[] each;


    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("invalid input");
        }

        this.each = new double[trials];
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
            this.each[i] = (double) count / (n * n);
        }

        this.mean = StdStats.mean(this.each);
        this.stddev = StdStats.stddev(this.each);
        this.cilow = this.mean - (1.96 * this.stddev) / Math.sqrt(trials);
        this.cihigh = this.mean + (1.96 * this.stddev) / Math.sqrt(trials);
    }

    public double mean() {
        return this.mean;

    }
    public double stddev() {
        return this.stddev;

    }
    public double confidenceLo() {
        return this.cilow;

    }
    public double confidenceHi() {
        return this.cihigh;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(N, T);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");

    }
}

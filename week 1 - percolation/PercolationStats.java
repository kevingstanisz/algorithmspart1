/* *****************************************************************************
 *  Name: Kevin Staniszewski
 *  Date: 6/15/2020
 *  Description: Algorithms Part 1, Programming Assignment 1 - Percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final double[] rawResults;

    private final double mean;

    private final double stdDev;

    private final double numTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        numTrials = trials;
        rawResults = new double[trials];

        int[] gridBoxes = new int[n * n];

        for (int j = 0; j < n * n; j++) {
            gridBoxes[j] = j;
        }

        int column;
        int row;

        for (int trialNumber = 0; trialNumber < trials; trialNumber++) {
            Percolation perc = new Percolation(n);

            StdRandom.shuffle(gridBoxes);

            for (int tileNumber = 0; tileNumber < n * n; tileNumber++) {

                column = (gridBoxes[tileNumber] % n) + 1;
                row = (gridBoxes[tileNumber] / n) + 1;
                perc.open(row, column);

                if (perc.percolates()) {
                    rawResults[trialNumber] = (tileNumber + 1) / (double) (n * n);
                    break;
                }
            }
        }

        mean = mean();
        stdDev = stddev();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(rawResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(rawResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (CONFIDENCE_95 * stdDev) / Math.sqrt(numTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (CONFIDENCE_95 * stdDev) / Math.sqrt(numTrials);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]),
                                                          Integer.parseInt(args[1]));

        StdOut.printf("mean                       = %f\n", percStats.mean);
        StdOut.printf("stddev                     = %f\n", percStats.stdDev);
        StdOut.printf("95%% confidence interval    = [%f, %f]\n", percStats.confidenceLo(),
                      percStats.confidenceHi());
    }
}

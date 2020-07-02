/* *****************************************************************************
 *  Name: Kevin Staniszewski
 *  Date: 6/15/2020
 *  Description: Algorithms Part 1, Programming Assignment 1 - Percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;

    private final WeightedQuickUnionUF weightedQU;
    private final WeightedQuickUnionUF percWeightedQU;

    private final int gridSize;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        gridSize = n;
        grid = new boolean[n + 1][n + 1];

        weightedQU = new WeightedQuickUnionUF(n * n + 1);
        percWeightedQU = new WeightedQuickUnionUF(n * n + 2);
    }

    private int determineCell(int row, int col) {
        return gridSize * (row - 1) + col;
    }

    // // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > gridSize || col > gridSize) {
            throw new IllegalArgumentException();
        }

        if (!grid[row][col]) {
            openSites++;
        }
        else {
            return;
        }

        grid[row][col] = true;

        // connects top row to virtual top
        if (row == 1) {
            weightedQU.union(0, determineCell(row, col));
            percWeightedQU.union(0, determineCell(row, col));
        }

        // connect only bottom row for percolation instance
        if (row == gridSize) {
            percWeightedQU.union(gridSize * gridSize + 1, determineCell(row, col));
        }

        // up
        if (row - 1 != 0) {
            if (grid[row - 1][col]) {
                weightedQU.union(determineCell(row - 1, col), determineCell(row, col));
                percWeightedQU.union(determineCell(row - 1, col), determineCell(row, col));
            }
        }

        // right
        if (col + 1 <= gridSize) {
            if (grid[row][col + 1]) {
                weightedQU.union(determineCell(row, col + 1), determineCell(row, col));
                percWeightedQU.union(determineCell(row, col + 1), determineCell(row, col));
            }
        }

        // down
        if (row + 1 <= gridSize) {
            if (grid[row + 1][col]) {
                weightedQU.union(determineCell(row + 1, col), determineCell(row, col));
                percWeightedQU.union(determineCell(row + 1, col), determineCell(row, col));
            }
        }

        // left
        if (col - 1 != 0) {
            if (grid[row][col - 1]) {
                weightedQU.union(determineCell(row, col - 1), determineCell(row, col));
                percWeightedQU.union(determineCell(row, col - 1), determineCell(row, col));
            }
        }
    }

    //
    // // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > gridSize || col > gridSize) {
            throw new IllegalArgumentException();
        }

        return grid[row][col];
    }

    //
    // // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > gridSize || col > gridSize) {
            throw new IllegalArgumentException();
        }


        if ((weightedQU.find(gridSize * (row - 1) + col) == weightedQU.find(0))
                && grid[row][col]) {
            return true;
        }

        return false;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    //
    // // returns the number of open sites
    // public int numberOfOpenSites()
    //
    // // does the system percolate?
    public boolean percolates() {
        return percWeightedQU.find(gridSize * gridSize + 1) == percWeightedQU.find(0);
    }
    //
    // // test client (optional)
    // public static void main(String[] args)
}

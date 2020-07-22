/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    private int[][] boardCopy;

    private int boardDimension;

    private String test;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        boardCopy = new int[tiles.length][];

        for (int i = 0; i < tiles.length; i++) {
            boardCopy[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }

        boardDimension = tiles.length;
    }

    // string representation of this board
    public String toString() {
        StringBuilder strBoard = new StringBuilder();

        strBoard.append(boardDimension + "\n");

        for (int i = 0; i < boardDimension; i++) {
            strBoard.append(" ");
            for (int j = 0; j < boardDimension; j++) {
                if (boardCopy[i][j] < 10) {
                    strBoard.append(boardCopy[i][j] + "  ");
                }
                else {
                    strBoard.append(boardCopy[i][j] + " ");
                }
            }

            strBoard.append("\n");
        }

        return strBoard.toString();
    }

    // board dimension n
    public int dimension() {
        return boardDimension;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDist = 0;
        int tileNumber;

        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                tileNumber = ((i * boardDimension) + (j + 1));
                if (boardCopy[i][j] != tileNumber) {
                    if (tileNumber != (boardDimension * boardDimension)) {
                        hammingDist++;
                    }
                }
            }
        }

        return hammingDist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanDist = 0;
        int tileNumber;
        int iCoord;
        int jCoord;

        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                tileNumber = ((i * boardDimension) + (j + 1));
                if (boardCopy[i][j] != tileNumber) {
                    if (boardCopy[i][j] != 0) {
                        iCoord = (boardCopy[i][j] - 1) / boardDimension;
                        jCoord = (boardCopy[i][j] - 1) % boardDimension;

                        manhattanDist = manhattanDist + Math.abs(iCoord - i)
                                + Math.abs(jCoord - j);

                        // StdOut.println(manhattanDist + " tilenumber " + boardCopy[i][j]
                        //                        + " supposed to be i " + iCoord + " j " + jCoord
                        //                        + " is at i " + i + " j " + j);
                    }
                }
            }
        }

        return manhattanDist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (hamming() == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;

        return Arrays.deepEquals(this.boardCopy, that.boardCopy);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighborBoards = new Stack<Board>();

        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension; j++) {
                if (boardCopy[i][j] == 0) {
                    if (i > 0) {
                        int[][] blockStack = blockCopy();
                        blockStack[i][j] = boardCopy[i - 1][j];
                        blockStack[i - 1][j] = 0;
                        neighborBoards.push(new Board(blockStack));
                    }

                    if (j > 0) {
                        int[][] blockStack = blockCopy();
                        blockStack[i][j] = boardCopy[i][j - 1];
                        blockStack[i][j - 1] = 0;
                        neighborBoards.push(new Board(blockStack));
                    }

                    if (i < (boardDimension - 1)) {
                        int[][] blockStack = blockCopy();
                        blockStack[i][j] = boardCopy[i + 1][j];
                        blockStack[i + 1][j] = 0;
                        neighborBoards.push(new Board(blockStack));
                    }

                    if (j < (boardDimension - 1)) {
                        int[][] blockStack = blockCopy();
                        blockStack[i][j] = boardCopy[i][j + 1];
                        blockStack[i][j + 1] = 0;
                        neighborBoards.push(new Board(blockStack));
                    }

                    return neighborBoards;
                }
            }
        }

        return neighborBoards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinBlocks = blockCopy();

        for (int i = 0; i < boardDimension; i++) {
            for (int j = 0; j < boardDimension - 1; j++) {
                if (boardCopy[i][j] != 0 && boardCopy[i][j + 1] != 0) {
                    twinBlocks[i][j] = boardCopy[i][j + 1];
                    twinBlocks[i][j + 1] = boardCopy[i][j];

                    return new Board(twinBlocks);
                }
            }
        }

        return null;
    }

    private int[][] blockCopy() {
        int[][] copiedBlocks = new int[this.boardDimension][];

        for (int i = 0; i < this.boardDimension; i++) {
            copiedBlocks[i] = Arrays.copyOf(this.boardCopy[i], this.boardDimension);
        }

        return copiedBlocks;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // create the slider puzzle
            Board initial = new Board(tiles);

            StdOut.println(initial.toString());
            StdOut.println("hamming " + initial.hamming());
            StdOut.println("manhattan " + initial.manhattan());
            StdOut.println("is goal " + initial.isGoal());
            StdOut.println("should be equal " + initial.equals(new Board(tiles)));
            tiles[0][0] = 100;
            StdOut.println("should NOT be equal " + initial.equals(new Board(tiles)));

            for (Board s : initial.neighbors()) {
                StdOut.println("neighbor " + s.toString());
            }

            Board twinBoard = initial.twin();

            StdOut.println("twin board " + twinBoard.toString());
        }
    }
}

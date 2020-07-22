/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Solver {

    private ArrayList<Board> solvedBoard = new ArrayList<>();

    private static class SearchNode {
        private Board board;
        private int numberMoves;
        private SearchNode prevNode;
        private int manPriority;


        private SearchNode(Board board, int numberMoves, SearchNode prevNode) {
            this.board = board;
            this.numberMoves = numberMoves;
            this.prevNode = prevNode;
            this.manPriority = board.manhattan() + numberMoves;
        }
    }

    private static class ManComparator implements Comparator<SearchNode> {
        public int compare(SearchNode node1, SearchNode node2) {
            return Integer.compare(node1.manPriority, node2.manPriority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<SearchNode> searchNodes = new MinPQ<>(new ManComparator());
        MinPQ<SearchNode> searchNodesTwin = new MinPQ<>(new ManComparator());

        SearchNode deqSearchNode = new SearchNode(initial, 0, null);
        SearchNode deqSearchNodeTwin = new SearchNode(initial.twin(), 0, null);

        searchNodes.insert(deqSearchNode);
        searchNodesTwin.insert(deqSearchNodeTwin);

        while (!deqSearchNode.board.isGoal() && !deqSearchNodeTwin.board.isGoal()) {
            deqSearchNode = searchNodes.delMin();

            for (Board s : deqSearchNode.board.neighbors()) {
                if (deqSearchNode.prevNode == null || !s.equals(deqSearchNode.prevNode.board))
                    searchNodes.insert(new SearchNode(s, deqSearchNode.numberMoves + 1,
                                                      deqSearchNode));
            }

            deqSearchNodeTwin = searchNodesTwin.delMin();

            for (Board s : deqSearchNodeTwin.board.neighbors()) {
                if (deqSearchNodeTwin.prevNode == null || !s
                        .equals(deqSearchNodeTwin.prevNode.board))
                    searchNodesTwin.insert(new SearchNode(s, deqSearchNodeTwin.numberMoves + 1,
                                                          deqSearchNodeTwin));
            }
        }

        // only one of the boards can reach goal
        if (deqSearchNode.board.isGoal()) {
            while (deqSearchNode != null) {
                solvedBoard.add(deqSearchNode.board);
                deqSearchNode = deqSearchNode.prevNode;
            }

            Collections.reverse(solvedBoard);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return !solvedBoard.isEmpty();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solvedBoard.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            return solvedBoard;
        }
        else {
            return null;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

    }
}
